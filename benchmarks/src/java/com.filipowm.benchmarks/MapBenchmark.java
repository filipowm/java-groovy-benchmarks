package com.filipowm.benchmarks;

public interface MapBenchmark {

    void fill(int repeatCount);
    void get(int repeatCount);
    void iterate(int repeatCount);

}
