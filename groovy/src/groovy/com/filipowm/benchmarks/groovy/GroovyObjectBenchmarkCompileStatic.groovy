package com.filipowm.benchmarks.groovy

@CompileStatic
public class GroovyObjectBenchmarkCompileStatic  implements ObjectBenchmark {
    @Param(['test', 'aaabbbccc15aaabbbccc'])
    private String test1value;
    private String test2value = 'aaabbbccc15aaabbbccc';
    private class TestClass {
        String test

        TestClass() {
            String with
        }
        TestClass(String test) {
            this.test = test
        }
    }

    @BeforeExperiment
    public void init() {
        this.test1 = new TestClass(test1value)
        this.test2 = new TestClass(test2value)
    }

    private TestClass test1
    private TestClass test2

    @Override
    @Benchmark
    public void newPojoWithDefaultConstructor(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            new TestClass()
        }
    }

    @Override
    @Benchmark
    public void newPojoWithCustomConstructor(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            new TestClass(test1value)
        }
    }

    @Override
    @Benchmark
    public void newPojoWithDefaultConstructorAndSetters(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            new TestClass().test = repeatCount
        }
    }

    @Override
    @Benchmark
    public void equals(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            test1.equals(test2)
        }
    }

    @Benchmark
    public void equalsDoubleEqual(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            test1 == test2
        }
    }

    @Override
    @Benchmark
    public void hashCode(long repeatCount) {
        for (long i = 0; i < repeatCount; i++) {
            test1.hashCode()
        }
    }
}
