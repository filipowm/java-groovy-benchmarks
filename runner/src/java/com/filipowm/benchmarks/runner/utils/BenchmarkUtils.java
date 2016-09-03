package com.filipowm.benchmarks.runner.utils;

import com.google.caliper.Benchmark;
import com.google.caliper.api.Macrobenchmark;
import javassist.CtClass;
import javassist.CtMethod;
import com.filipowm.benchmarks.DiscardBenchmark;

import java.lang.annotation.Annotation;

public class BenchmarkUtils {

    private BenchmarkUtils() {}

    private static final Class<? extends Annotation>[] BENCHARK_ANNOTATIONS = new Class[] {Benchmark.class, Macrobenchmark.class};
    
    public static boolean isBenchmarkable(CtClass clss) {
        if (clss.hasAnnotation(DiscardBenchmark.class)) {
            return false;
        }
        for (CtMethod method : clss.getDeclaredMethods()) {
            if (isBenchmarkable(method)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBenchmarkable(CtMethod method) {
        for (Class clss : BENCHARK_ANNOTATIONS) {
            if (method.hasAnnotation(clss)) {
                return true;
            }
        }
        return false;
    } 
}
