package com.filipowm.benchmarks.groovy

import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.RandomString
import com.filipowm.benchmarks.StringBenchmark


@CompileStatic
class GroovyStringBenchmark implements StringBenchmark {


    @Param(["1", "10", "100"]) private int length;

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
public void equalsIgnoreCase(long repeatCount) {
    for (long i = 0; i < repeatCount; i++) {
        string1.equalsIgnoreCase(string2);
    }
}

@Override
@Benchmark
void createBigString(long repeatCount) {
    for (long i = 0; i < repeatCount; i++) {
        String s = string1 + " " + string2 + " " + string1 + " " + string2;
    }
}

@Benchmark
void createBigStringGroovy(long repeatCount) {
    for (long i = 0; i < repeatCount; i++) {
        "${string1} ${string2} ${string1} ${string2}"
    }
}
}
