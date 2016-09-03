package com.filipowm.benchmarks.java;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.SortBenchmark;

import java.util.Arrays;
import java.util.Random;

public class JavaSortBenchmark implements SortBenchmark {
    @Param({"10", "100", "1000", "10000"})
    private int length;

    @Param
    private Distribution distribution;

    private int[] values;
    private int[] copy;
    private HeapSort heapSort = new HeapSort();
    private QuickSort quickSort = new QuickSort();

    @BeforeExperiment
    void setUp() throws Exception {
        values = distribution.create(length);
        copy = new int[length];
    }

    @Override
    @Benchmark
    public void sort(int reps) {
        for (int i = 0; i < reps; i++) {
            System.arraycopy(values, 0, copy, 0, values.length);
            Arrays.sort(copy);
        }
    }

    @Override
    @Benchmark
    public void quickSort(int reps) {
        for (int i = 0; i < reps; i++) {
            System.arraycopy(values, 0, copy, 0, values.length);
            quickSort.sort(copy);
        }
    }

    @Override
    @Benchmark
    public void heapSort(int reps) {
        for (int i = 0; i < reps; i++) {
            System.arraycopy(values, 0, copy, 0, values.length);
            heapSort.sort(copy);
        }
    }

public enum Distribution {
    INCREASING {
        @Override
        int[] create(int length) {
            int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = i;
            }
            return result;
        }
    },
    DECREASING {
        @Override
        int[] create(int length) {
            int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = length - i;
            }
            return result;
        }
    },
    RANDOM {
        @Override
        int[] create(int length) {
            Random random = new Random();
            int[] result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = random.nextInt();
            }
            return result;
        }
    };

    abstract int[] create(int length);
}

    class QuickSort {
        private int[] numbers;
        private int number;

        public void sort(int[] values) {
            if (values == null || values.length == 0) {
                return;
            }
            this.numbers = values;
            number = values.length;
            quicksort(0, number - 1);
        }

        private void quicksort(int low, int high) {
            int i = low;
            int j = high;
            int pivot = numbers[low + (high - low) / 2];

            while (i <= j) {
                while (numbers[i] < pivot) {
                    i++;
                }
                while (numbers[j] > pivot) {
                    j--;
                }

                if (i <= j) {
                    exchange(i, j);
                    i++;
                    j--;
                }
            }

            if (low < j)
                quicksort(low, j);
            if (i < high)
                quicksort(i, high);
        }

        private void exchange(int i, int j) {
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
    }

    class HeapSort {

        private int[] a;
        private int n;
        private int left;
        private int right;
        private int largest;


        private void buildheap(int[] a) {
            n = a.length - 1;
            for (int i = n / 2; i >= 0; i--) {
                maxheap(a, i);
            }
        }

        private void maxheap(int[] a, int i) {
            left = 2 * i;
            right = 2 * i + 1;
            if (left <= n && a[left] > a[i]) {
                largest = left;
            } else {
                largest = i;
            }

            if (right <= n && a[right] > a[largest]) {
                largest = right;
            }
            if (largest != i) {
                exchange(i, largest);
                maxheap(a, largest);
            }
        }

        private void exchange(int i, int j) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }

        public void sort(int[] a0) {
            a = a0;
            buildheap(a);

            for (int i = n; i > 0; i--) {
                exchange(0, i);
                n = n - 1;
                maxheap(a, 0);
            }
        }
    }
}
