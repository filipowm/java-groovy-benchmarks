package com.filipowm.benchmarks.java;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.filipowm.benchmarks.MessageDigestBenchmark;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JavaMessageDigestBenchmark implements MessageDigestBenchmark{
    @Param({"MD5", "SHA-1", "SHA-256", "SHA-512"})
    String algorithm;

    @Override
    @Benchmark
    public void create(int repeatCount) throws DigestException, NoSuchAlgorithmException {
        for (int i = 0; i < repeatCount; i++) {
            MessageDigest.getInstance(algorithm);
        }
    }
}
