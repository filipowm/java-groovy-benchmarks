package com.filipowm.benchmarks;

import com.google.caliper.Benchmark;

public interface SortBenchmark {

    void sort(int repeatCount);
    void quickSort(int repeatCount);
    void heapSort(int repeatCount);

}
