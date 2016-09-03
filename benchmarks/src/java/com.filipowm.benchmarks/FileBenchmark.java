package com.filipowm.benchmarks;

import com.google.caliper.Benchmark;

public interface FileBenchmark {

    void newFile(long repeatCount);
    void deleteFile(long repeatCount);
    void readFile(long repeatCount);
    void writeFile(long repeatCount);

}
