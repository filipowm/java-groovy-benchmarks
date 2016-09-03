package com.filipowm.benchmarks.java;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.MapBenchmark;

import java.util.*;

public class JavaMapBenchmark implements MapBenchmark {
    
    @Param({"10","100", "1000", "10000"})
    private int size;

    private enum MapImpl {
        HashMap {
            @Override
            Map<Integer, Integer> create() {
                return new HashMap<>();
            }
        },
        TreeMap {
            @Override
            Map<Integer, Integer> create() {
                
                return new TreeMap<>();
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
                map.put(j, j);
            }
        }
    }

    @Override
    @Benchmark
    public void get(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            int index = random.nextInt(map2.size());
            map2.get(index);
        }
    }

    static Random random = new Random();

    @Override
    @Benchmark
    public void iterate(int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            for (Map.Entry<Integer, Integer> entry : map2.entrySet()) {
                continue;
            }
        }
    }
}
