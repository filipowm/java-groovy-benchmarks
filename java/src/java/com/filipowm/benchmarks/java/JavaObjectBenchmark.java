package com.filipowm.benchmarks.java;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.common.base.Function;
import com.filipowm.benchmarks.ObjectBenchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class JavaObjectBenchmark implements ObjectBenchmark {
    @Param({"test", "aaabbbccc15aaabbbccc"})
    private String test1value;
    private String test2value = "aaabbbccc15aaabbbccc";
    private class TestClass {
        String test;

        public TestClass(String test) {
            this.test = test;
        }

        public TestClass() {
        }

        public void setTest(String test) {
            this.test = test;
        }
    }
    @BeforeExperiment
    public void init() {
        this.test1 = new TestClass(test1value);
        this.test2 = new TestClass(test2value);
    }

    private TestClass test1;
    private TestClass test2;
    public TestClass test3;

    @Override
    @Benchmark
    public void newPojoWithDefaultConstructor(long repeatCount) {
        for (long i = 1; i < repeatCount; i ++) {
            test3 = new TestClass();
        }
    }

    @Override
    @Benchmark
    public void newPojoWithCustomConstructor(long repeatCount) {
        for (long i = 1; i < repeatCount; i ++) {
            test3 = new TestClass(test1value);
        }
    }

    @Override
    @Benchmark
    public void newPojoWithDefaultConstructorAndSetters(long repeatCount) {
        for (long i = 1; i < repeatCount; i ++) {
            test3 = new TestClass();
            test3.setTest(test1value);
        }
    }

    @Override
    @Benchmark
    public void equals(long repeatCount) {
        for (long i = 1; i < repeatCount; i ++) {
            test1.equals(test2);
        }
    }

    @Override
    @Benchmark
    public void hashCode(long repeatCount) {
        for (long i = 1; i < repeatCount; i ++) {
            test1.hashCode();
        }
    }
}
