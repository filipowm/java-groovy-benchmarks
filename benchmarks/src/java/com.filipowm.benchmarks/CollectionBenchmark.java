package com.filipowm.benchmarks;

public interface CollectionBenchmark {

    void fill(int repeatCount);
    void filter(int repeatCount);
    void filterAndCount(int repeatCount);
    void limit(int repeatCount);

}
