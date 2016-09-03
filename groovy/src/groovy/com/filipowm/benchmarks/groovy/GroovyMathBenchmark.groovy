package com.filipowm.benchmarks.groovy

import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.MathBenchmark

import java.math.RoundingMode

@CompileStatic
class GroovyMathBenchmark implements MathBenchmark {

    @Param(["10", "100", "1000", "10000"])
    private int value1;
    private int value2;


    private BigDecimal valueb1;
    private BigDecimal valueb2;

    @BeforeExperiment
    void setup() {
        this.value2 = (value1 / 3).intValue();
        this.valueb1 = new BigDecimal(value1);
        this.valueb2 = new BigDecimal(value2);
        this.stringV = String.valueOf(value1);
    }

    private String stringV;

    @Override
    @Benchmark
    public void bigDecimalCreate(long reps) {
        for (long i = 0; i < reps; i++) {
            new BigDecimal(value1);
        }
    }

    @Override
    @Benchmark
    public void bigDecimalAdd(long reps) {
        for (long i = 0; i < reps; i++) {
            valueb1 + valueb2
        }
    }

    @Override
    @Benchmark
    public void bigDecimalDivide(long reps) {
        for (long i = 0; i < reps; i++) {
            valueb1.divide(valueb2, RoundingMode.HALF_UP)
        }
    }

    @Override
    public void bigDecimalPower(long reps) {
        for (long i = 0; i < reps; i++) {
            valueb1.pow(value1);
        }
    }

    @Override
    @Benchmark
    public void sum(long reps) {
        for (long i = 0; i < reps; i++) {
            double s = value1 + value2;
        }
    }

    @Override
    @Benchmark
    public void divide(long reps) {
        for (long i = 0; i < reps; i++) {
            double d = value1 / value2;
        }
    }

    @Override
    @Benchmark
    public void increment(long reps) {
        int j = 0;
        for (long i = 0; i < reps; i++) {
            j++;
        }
    }

    @Override
    @Benchmark
    public void decrement(long reps) {
        int j = 0;
        for (long i = 0; i < reps; i++) {
            j--;
        }
    }

    @Override
    @Benchmark
    public void parseInteger(long reps) {
        for (long i = 0; i < reps; i++) {
            Integer.parseInt(stringV);
        }
    }
}
