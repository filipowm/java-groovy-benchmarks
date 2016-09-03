package com.filipowm.benchmarks.groovy

import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.DateBenchmark

import java.sql.Timestamp
import java.text.SimpleDateFormat

@CompileStatic
class GroovyDateBenchmark implements DateBenchmark {


    enum DateFormats {
        YYYYMMDD("YYYY-MM-DD"),
        YYYYMMDDhhmmss("YYYY-MM-DD hh:mm:ss");

        String format;
        DateFormats(String format) {
            this.format = format;
        }
    }

    @Param DateFormats dateFormat;
    private static final Date DATE = new Date();

    private SimpleDateFormat format;

    @BeforeExperiment
    public void init() {
        this.format = new SimpleDateFormat(dateFormat.format);
    }

    @Override
    @Benchmark
    public void current(long repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            new Date();
        }
    }

    @Override
    @Benchmark
    public void currentInMillis(long repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            new Date().time;
        }
    }

    @Override
    @Benchmark
    public void format(long repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            format.format(DATE);
        }
    }

    @Override
    @Benchmark
    public void newSimpleDateFormat(long repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            new SimpleDateFormat(dateFormat.format);
        }
    }

    @Override
    @Benchmark
    public void currentToTimestamp(long repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            new Timestamp(new Date().time);
        }
    }
}
