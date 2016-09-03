package com.filipowm.benchmarks.groovy

import com.filipowm.benchmarks.CollectionBenchmark
import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic

@CompileStatic
class GroovyCollectionBenchmark  implements CollectionBenchmark {

    private enum ListImpl {
        ArrayList {
            @Override
            Collection<Integer> create() {
                return [] as ArrayList;
            }
        },
        Vector {
            @Override
            Collection<Integer> create() {
                return [] as Vector;
            }
        },
        HashSet {
            @Override Collection<Integer> create() {
                return [] as HashSet;
            }
        };

        abstract Collection<Integer> create();
    }

    @Param(["10", "100", "1000", "10000"])
    private int size;

    @Param
    private ListImpl implementation;

    private Collection<Integer> collection;
    private Collection<Integer> collection2;

    @BeforeExperiment
    void setup() {
        collection = implementation.create();
        collection2 = implementation.create();
        for (int i = 0; i < size; i++) {
            if (i % 5 == 0) {
                collection2.add(null);
                continue;
            }
            collection2.add(i);
        }
    }


    @Override
    @Benchmark
    public void fill(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            collection.clear();
            for (int j = 0; j < size; j++) {
                collection.add(j);
            }
        }
    }

    @Override
    @Benchmark
    public void filter(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            collection2.findAll { it != null }
        }
    }

    @Override
    @Benchmark
    public void filterAndCount(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            collection2.count { it != null }
        }
    }

    @Override
    @Benchmark
    public void limit(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            collection2.take(50);
        }
    }
}
