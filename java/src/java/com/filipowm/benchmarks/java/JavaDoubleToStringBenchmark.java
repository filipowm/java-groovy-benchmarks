package com.filipowm.benchmarks.java;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.DiscardBenchmark;
import com.filipowm.benchmarks.DoubleToStringBenchmark;


@DiscardBenchmark
public class JavaDoubleToStringBenchmark implements DoubleToStringBenchmark {

    @Param Method method;
    @Param Value value;

    public enum Method {
        TO_STRING {
            @Override String convert(double d) {
                return ((Double) d).toString();
            }
            @Override String convert(Double d) {
                return d.toString();
            }
        }
        ;

        abstract String convert(double d);
        abstract String convert(Double d);
    }

    enum Value {
        Pi(Math.PI),
        NegativeZero(-0.0),
        NegativeInfinity(Double.NEGATIVE_INFINITY),
        NaN(Double.NaN);

        final double value;

        Value(double value) {
            this.value = value;
        }
    }

    @Override
    @Benchmark
    public int withPrimitive(long repeatCount) {
        double d = value.value;
        int dummy = 0;
        for (int i = 0; i < repeatCount; i++) {
            dummy += method.convert(d).length();
        }
        return dummy;
    }

    @Override
    @Benchmark
    public int withWrapper(long repeatCount) {
        Double d = value.value;
        int dummy = 0;
        for (int i = 0; i < repeatCount; i++) {
            dummy += method.convert(d).length();
        }
        return dummy;
    }
}
