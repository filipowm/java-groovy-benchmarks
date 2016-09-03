package com.filipowm.benchmarks.groovy

import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import groovy.transform.TailRecursive
import com.filipowm.benchmarks.RecursionBenchmark

@CompileStatic
class GroovyRecursionBenchmark implements RecursionBenchmark {

    @Param(["10", "100", "1000", "10000"])
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
    void tailRecursive(long reps) {
        for (long i = 0; i < reps; i++) {
            factorial(value, 1);
        }
    }

    int factorial(int n) {
        try {
            if (n < 1) {
                return 1;
            }
            return n * factorial(n - 1);
        } catch (StackOverflowError soe) {
            println 'SOE recursive: ' + (value - n);
//            println "stacktrace size: ${soe.stackTrace.length}"
            return 0
        }
    }

    @TailRecursive
    int factorial(int n, int sum) {
        try {
            if (n < 2) {
                return sum;
            }
            return factorial(n - 1, n * sum);
        } catch (StackOverflowError soe) {
            println 'SOE tail recursive: ' + (value - n);
//            println "stacktrace size: ${soe.stackTrace.length}"
            return sum
        }
    }
}
