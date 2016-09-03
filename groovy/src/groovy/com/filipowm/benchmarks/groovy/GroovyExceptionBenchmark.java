package com.filipowm.benchmarks.groovy;

import com.google.caliper.Benchmark;
import com.filipowm.benchmarks.ExceptionBenchmark;

public class GroovyExceptionBenchmark  implements ExceptionBenchmark {

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
