package com.filipowm.benchmarks.java;

import com.google.caliper.AfterExperiment;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.filipowm.benchmarks.FileBenchmark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaFileBenchmark implements FileBenchmark {

    public static final String FILENAME = "fileBenchmark.txt";
    public static final String FILENAME_2 = "fileBenchmark2.txt";

    File testFile;

    @BeforeExperiment
    public void setup() {
        try {
            testFile = new File(FILENAME_2);
            testFile.createNewFile();
            Files.write(Paths.get(FILENAME_2), "Test line1\nTest line 2".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterExperiment
    public void cleanup() {
        new File(FILENAME).delete();
        testFile.delete();
    }

    @Benchmark
    public void newFile(long reps) {
        for (int i = 0; i < reps; i++){
            try {
                new File(FILENAME).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Benchmark
    public void deleteFile(long reps) {
        for (int i = 0; i < reps; i++){
            testFile.delete();
        }
    }

    @Benchmark
    public void readFile(long reps) {
        for (long i = 0; i < reps; i++) {
            try {
                Files.readAllBytes(Paths.get(FILENAME_2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Benchmark
    public void writeFile(long reps) {
        for (int i = 0; i < reps; i++){
            try {
                Files.write(Paths.get(FILENAME_2), "Test line1\nTest line 2".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
