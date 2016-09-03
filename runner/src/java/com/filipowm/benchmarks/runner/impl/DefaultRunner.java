package com.filipowm.benchmarks.runner.impl;

import com.google.caliper.runner.CaliperMain;
import com.google.common.reflect.ClassPath;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import com.filipowm.benchmarks.runner.exception.CaliperRunnerException;
import com.filipowm.benchmarks.runner.profile.Profile;
import com.filipowm.benchmarks.runner.profile.Scope;
import com.filipowm.benchmarks.runner.profile.Target;
import com.filipowm.benchmarks.runner.Runner;
import com.filipowm.benchmarks.runner.settings.RunnerOptions;
import com.filipowm.benchmarks.runner.settings.VmOptions;
import com.filipowm.benchmarks.runner.settings.SettingsEvaluator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DefaultRunner implements Runner {

    private RunnerOptions runnerOptions;
    private VmOptions vmOptions;
    private String[] caliperOpts = new String[0];

    public DefaultRunner() {
        initializeDefaultSettings();
    }

    public DefaultRunner(VmOptions vmOptions) {
        if (vmOptions == null) {
            initializeDefaultSettings();
        } else {
            this.vmOptions = vmOptions;
        }
    }

    @Override
    public boolean supports(Profile profile) {
        return true;
    }

    public void setCaliperOptions(String[] opts) {
        this.caliperOpts = opts;
    }

    private void logRun(Profile profile) {
        System.out.println("Searching for benchmarks in: " + profile.getTargetName());
        System.out.println("Target: " + profile.getTarget());
        System.out.println("Scope: " + profile.getScope());
        System.out.println("Mode: " + profile.getProfileType());
    }

    @Override
    public void run(Profile profile) throws IOException {
        logRun(profile);
        Run run = new Run(profile);

        Set<ClassPath.ClassInfo> classes = run.getClasses();
        System.out.println("Found " + classes.size() + " classes with benchmarks");

        classes.forEach(run::execute);
    }

    @Override
    public VmOptions getVmOptions() {
        return vmOptions;
    }

    @Override
    public void setVmOptions(VmOptions vmOptions) {
        this.vmOptions = vmOptions;
    }

    private void initializeDefaultSettings() {
        vmOptions = new VmOptions();
        vmOptions.setServer(false);
        vmOptions.setTieredCompilation(true);
        vmOptions.setXms(VmOptions.XMS_DEFAULT);
        vmOptions.setXmx(VmOptions.XMX_DEFAULT);
    }

    private class Run {
        final SettingsEvaluator evaluator;
        final Profile profile;
        private ClassPath classPath;
        Run(Profile profile) {
            if (profile == null) {
                throw new NullPointerException("Profile cannot be null");
            }
            this.profile = profile;
            if (profile.getVmOptions() == null) {
                evaluator = new SettingsEvaluator(vmOptions);
            } else {
                evaluator = new SettingsEvaluator(profile.getVmOptions());
            }
            try {
                this.classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void execute(ClassPath.ClassInfo info) {
            try {
                Class clss = evaluator.evaluate(info.getName());
                if (clss != null) {
                    System.out.println("Loading benchmark: " + info.getName());
                    CaliperMain.main(clss, caliperOpts);
                } else {
                    System.out.println("Class " + info.getName() + " does not contain any benchmark");
                }
            } catch (SecurityException | CannotCompileException | NotFoundException e) {
                System.err.println("Exception during running benchmark " + info.getName() + " occured");
                System.err.println(e.getMessage());
            } finally {
                finalizeRunner();
            }
        }

        public Set<ClassPath.ClassInfo> getClasses() throws IOException {
            Set<ClassPath.ClassInfo> classes = new HashSet<>();
            ClassPath.ClassInfo clss = getClassInfo();
            if (profile.getTarget() == Target.CLASS || clss != null) {
                classes.add(clss);
            } else if (profile.getTarget() == Target.PACKAGE) {
                classes.addAll(getClassesForPackage());
            } else {
                throw new CaliperRunnerException();
            }
            return classes;
        }

        private Set<ClassPath.ClassInfo> getClassesForPackage() {
            if (profile.getScope() == Scope.BRANCH) {
                return classPath.getTopLevelClasses(profile.getTargetName());
            } else {
                return classPath.getTopLevelClassesRecursive(profile.getTargetName());
            }
        }

        private ClassPath.ClassInfo getClassInfo() {
            for (ClassPath.ClassInfo info : classPath.getAllClasses()) {
                if (info.getName().equals(profile.getTargetName()) || info.getSimpleName().equals(profile.getTargetName())) {
                    return info;
                }
            }
            return null;
        }
    }
}
