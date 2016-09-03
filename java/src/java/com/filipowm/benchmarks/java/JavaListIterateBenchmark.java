package com.filipowm.benchmarks.java;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JavaListIterateBenchmark {


    private enum Element {
        INSTANCE,
    }
    private enum ListImpl {
        Array {
            @Override
            List<Element> create() {
                return new ArrayList<>();
            }
        },
        Linked {
            @Override List<Element> create() {
                return new LinkedList<>();
            }
        };

        abstract List<Element> create();
    }

    @Param({"10","100", "1000", "10000"})
    private int size;

    @Param({"Array", "Linked"})
    private ListImpl implementation;

    private List<Element> list;

    @BeforeExperiment
    void setUp() throws Exception {
        list = implementation.create();
        for (int i = 0; i < size; i++) {
            list.add(Element.INSTANCE);
        }
    }

    @Benchmark
    void normal(int reps) {
        for (int i = 0; i < reps; i++) {
            for (int j = 0; j < size; j++) {
                list.get(j);
            }
        }
    }

    @Benchmark
    void foreach(int reps) {
        for (int i = 0; i < reps; i++) {
            for (Element element : list) {
                continue;
            }
        }
    }

    @Benchmark
    void iterator(int reps) {
        for (int i = 0; i < reps; i++) {
            Iterator<Element> iterator = list.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
        }
    }
}
