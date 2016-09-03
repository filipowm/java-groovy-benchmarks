package com.filipowm.benchmarks.groovy

import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.MapBenchmark

@CompileStatic
class GroovyMapBenchmark implements MapBenchmark {

    @Param(["10","100", "1000", "10000"])
    private int size;

    private enum MapImpl {
        HashMap {
            @Override
            Map<Integer, Integer> create() {
                return [:] as HashMap
            }
        },
        TreeMap {
            @Override
            Map<Integer, Integer> create() {
                return [:] as TreeMap
            }
        };

        abstract Map<Integer, Integer> create();
    }
    @Param
    private MapImpl implementation;

    private Map<Integer, Integer> map;
    private Map<Integer, Integer> map2;

    @BeforeExperiment
    void setup() {
        map = implementation.create();
        map2 = implementation.create();
        for (int i = 0; i < size; i++) {
            if (i % 5 == 0) {
                map2.put(i, null);
            }
            map2.put(i, i * 2);
        }
    }

    @Override
    @Benchmark
    public void fill(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            map.clear();
            for (int j = 0; j < size; j++) {
                map[j] = j
            }
        }
    }

    @Override
    @Benchmark
    public void get(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            int index = random.nextInt(map2.size());
            map2[index];
        }
    }

    static Random random = new Random();

    @Override
    @Benchmark
    public void iterate(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            map2.each { key, value -> return true }
        }
    }
}
