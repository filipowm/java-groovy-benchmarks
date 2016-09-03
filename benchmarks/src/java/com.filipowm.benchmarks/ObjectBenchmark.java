package com.filipowm.benchmarks;

import com.google.caliper.Benchmark;

public interface ObjectBenchmark {

    void newPojoWithDefaultConstructor(long repeatCount);
    void newPojoWithCustomConstructor(long repeatCount);
    void newPojoWithDefaultConstructorAndSetters(long repeatCount);
    void equals(long repeatCount);
    void hashCode(long repeatCount);

}
