package com.filipowm.benchmarks.java;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.RandomString;
import com.filipowm.benchmarks.StringBenchmark;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaStringBenchmark implements StringBenchmark {


    @Param({"1", "10", "100"}) private int length;

    private static final String TEST = "test";

    private String string1;
    private String string2;

    @BeforeExperiment
    void setup() {
        RandomString randomString = new RandomString(length);
        string1 = randomString.nextString();
        string2 = randomString.nextString();
    }

    @Override
    @Benchmark
    public void concat(long repeatCount) {
        String solution = "";
        for (long i = 0; i < repeatCount; i++) {
            for (long j = 0; j < length; j++) {
                solution += TEST;
            }
        }
    }

    @Override
    @Benchmark
    public void concatWithStringBuilder(long repeatCount) {
        StringBuilder solution = new StringBuilder();
        for (long i = 0; i < repeatCount; i++) {
            for (long j = 0; j < length; j++) {
                solution.append(TEST);
            }
        }
    }

    @Override
    @Benchmark
    public void concatWithStringBuffer(long repeatCount) {
        StringBuffer solution = new StringBuffer();
        for (long i = 0; i < repeatCount; i++) {
            for (long j = 0; j < length; j++) {
                solution.append(TEST);
            }
        }
    }

    @Override
    @Benchmark
    public void contains(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            string1.contains(string2);
        }
    }

    @Override
    @Benchmark
    public void createBigString(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            String s = string1 + " " + string2 + " " + string1 + " " + string2;
        }
    }

    @Override
    @Benchmark
    public void equalsIgnoreCase(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            string1.equalsIgnoreCase(string2);
        }
    }
}
