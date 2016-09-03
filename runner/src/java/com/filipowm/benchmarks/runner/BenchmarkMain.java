package com.filipowm.benchmarks.runner;

import com.beust.jcommander.JCommander;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;
import com.filipowm.benchmarks.runner.profile.Profile;
import com.filipowm.benchmarks.runner.settings.ArgsOptions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BenchmarkMain {

    private Set<Runner> runners;
    private List<Profile> profiles;
    private String[] caliperOptions;

    @Inject
    public void setRunners(Set<Runner> runners) {
        this.runners = runners;
    }

    private static ArgsOptions resolveOptions(String[] args) {
        ArgsOptions opts = new ArgsOptions();
        new JCommander(opts, args);
        return opts;
    }

    public static void main(String[] args) {
        System.setSecurityManager(ExitPreventSecurityManager.instance());

        ArgsOptions opts = resolveOptions(args);
        List<Profile> profiles = opts.profiles();
        System.out.println(opts);
        Injector injector = Guice.createInjector(new RunnerInjector(profiles));
        BenchmarkMain app = injector.getInstance(BenchmarkMain.class);
        System.out.println("App set up");
        app.profiles = profiles;
        app.caliperOptions = opts.getCaliper();
        app.run();
    }

    public void run() {
        for (Profile profile : profiles) {
            Optional<Runner> runner = runners.stream()
                   .filter(run -> run.supports(profile))
                   .findFirst();
            if (runner.isPresent()) {
                try {
                    Runner tRunner = runner.get();
                    tRunner.setCaliperOptions(caliperOptions);
                    tRunner.run(profile);
                } catch (IOException e) {
                    System.err.println("Error running " + profile.getTargetName() + " benchmarks");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private static class RunnerInjector extends AbstractModule {
        List<Profile> profiles;

        RunnerInjector(List<Profile> profiles) {
            this.profiles = profiles;
        }
        @Override
        protected void configure() {
            Multibinder<Runner> binder =
                    Multibinder.newSetBinder(binder(), Runner.class);
            new Reflections("").getSubTypesOf(Runner.class)
                               .forEach(runner -> binder.addBinding().to(runner));
        }
    }
}
