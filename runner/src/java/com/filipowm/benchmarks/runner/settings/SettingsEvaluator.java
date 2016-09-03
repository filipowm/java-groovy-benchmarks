package com.filipowm.benchmarks.runner.settings;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import com.filipowm.benchmarks.runner.utils.AnnotationUtils;
import com.filipowm.benchmarks.runner.utils.BenchmarkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsEvaluator {

    private static final String XMX_CONF = "-Xmx{0}m";
    private static final String XMS_CONF = "-Xms{0}m";
    private static final String COMPILER_COUNT_CONF = "-XX:CICompilerCount={0}";
    private static final String SERVER_CONF = "-server";
    private static final String TIERED_COMPILATION_CONF = "-XX:-TieredCompilation";
    private static final int XMX_MIN = 128;
    private static final int XMS_MIN = 16;

    private final String xmx;
    private final String xms;
    private final String compilerCount;
    private final String tieredCompilation;
    private final String server;

    public SettingsEvaluator(VmOptions vmOptions) {
        this.xms = getXms(vmOptions.getXms());
        this.xmx = getXmx(vmOptions.getXmx());
        this.compilerCount = getConf(COMPILER_COUNT_CONF, vmOptions.getCompilerCount());
        this.tieredCompilation = vmOptions.isTieredCompilation() ? TIERED_COMPILATION_CONF : "";
        this.server = vmOptions.isServer() ? SERVER_CONF : "";
    }

    public Class evaluate(String className) throws NotFoundException, CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(className);
        if (isLoaded(ctClass) || !BenchmarkUtils.isBenchmarkable(ctClass)) {
            return null;
        }
        ClassFile classFile = ctClass.getClassFile();
        ConstPool cp = classFile.getConstPool();

        ArrayMemberValue amv = new ArrayMemberValue(cp);
        amv.setValue(asArray(cp, xmx, xms, compilerCount, tieredCompilation, server));

        Annotation annotation = new Annotation(com.google.caliper.api.VmOptions.class.getName(), cp);
        annotation.addMemberValue(AnnotationUtils.DEFAULT_VALUE, amv);

        AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        attr.addAnnotation(annotation);
        classFile.addAttribute(attr);

        return ctClass.toClass();
    }

    private boolean isLoaded(CtClass clss)  {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Method m = null;
        try {
            m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            m.setAccessible(true);
            return m.invoke(cl, clss.getName()) != null;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getXmx(Integer xmx) {
        if (xmx == null || xmx < XMX_MIN) {
            xmx = VmOptions.XMX_DEFAULT;
        }
        return getConf(XMX_CONF, xmx);
    }

    private String getXms(Integer xms) {
        if (xms == null || xms < XMS_MIN) {
            xms = VmOptions.XMS_DEFAULT;
        }
        return getConf(XMS_CONF, xms);
    }

    private String getConf(String conf, Integer value) {
        if (value == null) {
            return "";
        }
        return conf.replace("{0}", String.valueOf(value));
    }

    private StringMemberValue[] asArray(ConstPool cp, String... values) {
        if (values == null) {
            return new StringMemberValue[0];
        }
        List<String> vals = Arrays.asList(values)
                                                .stream()
                                                .filter(value -> value != null)
                                                .filter(value -> !value.trim().isEmpty())
                                                .collect(Collectors.toList());
        StringMemberValue[] smv = new StringMemberValue[vals.size()];
        for (int i = 0; i < vals.size(); i++) {
            String value = values[i];
            smv[i] = new StringMemberValue(value, cp);
        }
        return smv;
    }
}
