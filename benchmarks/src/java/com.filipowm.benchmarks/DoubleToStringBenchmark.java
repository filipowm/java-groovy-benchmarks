package com.filipowm.benchmarks;

public interface DoubleToStringBenchmark {

    int withPrimitive(long repeatCount);
    int withWrapper(long repeatCount);

}
