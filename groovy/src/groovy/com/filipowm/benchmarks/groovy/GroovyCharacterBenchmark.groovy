package com.filipowm.benchmarks.groovy

import com.google.caliper.BeforeExperiment
import com.google.caliper.Benchmark
import com.google.caliper.Param
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.CharacterBenchmark

@CompileStatic
class GroovyCharacterBenchmark implements CharacterBenchmark {


    private static final int CHARS = 65536;

    @Param
    private CharacterSet characterSet;

    private char[] chars;

    @BeforeExperiment
    public void setUp() throws Exception {
        this.chars = characterSet.chars;
    }

    public enum CharacterSet {
        ASCII(128),
        UNICODE(CHARS);
        final char[] chars;

        CharacterSet(int size) {
            this.chars = new char[CHARS];
            for (int i = 0; i < CHARS; ++i) {
                chars[i] = (char) (i % size).intValue();
            }
        }
    }

    // A fake benchmark to give us a baseline.
    @Benchmark
    public void isSpace(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                boolean a = ((char) ch) == ' ';
            }
        }
    }

    @Benchmark
    public void digit(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.digit(chars[ch], 10);
            }
        }
    }

    @Benchmark
    public void isDigit(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isDigit(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isLetter(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isLetter(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isLetterOrDigit(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isLetterOrDigit(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isLowerCase(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isLowerCase(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isSpaceChar(long reps) {
        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isSpaceChar(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isUpperCase(long reps) {

        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isUpperCase(chars[ch]);
            }
        }
    }

    @Benchmark
    public void isWhitespace(long reps) {

        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.isWhitespace(chars[ch]);
            }
        }
    }

    @Benchmark
    public void toLowerCase(long reps) {

        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.toLowerCase(chars[ch]);
            }
        }
    }

    @Benchmark
    public void toUpperCase(long reps) {

        for (int i = 0; i < reps; ++i) {
            for (int ch = 0; ch < CHARS; ++ch) {
                Character.toUpperCase(chars[ch]);
            }
        }
    }
}
