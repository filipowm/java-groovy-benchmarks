package com.filipowm.benchmarks.java;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.RecursionBenchmark;

public class JavaRecursionBenchmark implements RecursionBenchmark {

    @Param({"10", "100", "1000", "10000"})
    int value;

    @Override
    @Benchmark
    public void recursive(int reps) {
        for (int i = 0; i < reps; i++) {
            factorial(value);
        }
    }

    @Override
    @Benchmark
    public void tailRecursive(long reps) {
        for (int i = 0; i < reps; i++) {
            factorial(value, 1);
        }
    }

int factorial(int n) {
    try {
        if (n < 1) {
            return 1;
        }
        return n * factorial(n-1);
    } catch (StackOverflowError soe) {
        System.out.println("SOE recursive: " + (value - n));
        return 1;
    }
}
int factorial (int n, int sum) {
    try {
        if (n < 2) {
            return sum;
        }
        return factorial(n - 1, n * sum);
    } catch (StackOverflowError soe) {
        System.out.println("SOE tailRecursive: " + (value - n));
        return 1;
    }
}
}
