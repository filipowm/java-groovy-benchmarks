package com.filipowm.benchmarks;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Looper {

    public static <T> T loop(Long reps, Supplier<T> func) {
        T obj = null;
        for (long i = 0; i < reps; i++) {
            obj = func.get();
        }
        return obj;
    }

    public static <T> void loop(Long reps, T acceptable, Consumer<T> func) {
        for (long i = 0; i < reps; i++) {
            func.accept(acceptable);
        }
    }

    public static void loop(Long reps,  Consumer<Integer> func) {
        for (long i = 0; i < reps; i++) {
            func.accept(Math.toIntExact(i));
        }
    }

    public static <T> T loop(Long reps,  Function<Integer, T> func) {
        T obj = null;
        for (long i = 0; i < reps; i++) {
            obj = func.apply(Math.toIntExact(i));
        }
        return obj;
    }

    public static void loop(Long reps, Looper func) {
        for (long i = 0; i < reps; i++) {
            func.loop();
        }
    }


    void loop();

    default<T> void loop(T obj) {}


}
