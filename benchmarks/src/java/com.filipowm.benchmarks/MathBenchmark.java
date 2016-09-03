package com.filipowm.benchmarks;

public interface MathBenchmark {

    void bigDecimalCreate(long reps);
    void bigDecimalAdd(long reps);
    void bigDecimalDivide(long reps);
    void bigDecimalPower(long reps);
    void sum(long reps);
    void divide(long reps);
    void increment(long reps);
    void decrement(long reps);
    void parseInteger(long reps);

}


