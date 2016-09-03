package com.filipowm.benchmarks;

public interface DateBenchmark {

    void current(long repeatCount);
    void currentInMillis(long repeatCount);
    void format(long repeatCount);
    void newSimpleDateFormat(long repeatCount);
    void currentToTimestamp(long repeatCount);

}
