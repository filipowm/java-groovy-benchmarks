package com.filipowm.benchmarks.java;

import com.google.caliper.Benchmark;
import com.filipowm.benchmarks.ExceptionBenchmark;

public class JavaExceptionBenchmark implements ExceptionBenchmark {
    @Override
    @Benchmark
    public void throwRuntimeException(int reps) {
        for (int i = 0; i < reps; i++) {
            try {
                throw new RuntimeException();
            } catch (Exception e) {
            }
        }
    }
}
