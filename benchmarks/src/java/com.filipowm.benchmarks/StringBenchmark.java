package com.filipowm.benchmarks;

import com.google.caliper.Benchmark;

public interface StringBenchmark {

    void concat(long repeatCount);
    void concatWithStringBuilder(long repeatCount);
    void concatWithStringBuffer(long repeatCount);
    void contains(long repeatCount);
    void equalsIgnoreCase(long repeatCount);
    void createBigString(long repeatCount);

}
