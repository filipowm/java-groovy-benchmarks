package com.filipowm.benchmarks;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

public interface MessageDigestBenchmark {

    void create(int repeatCount) throws DigestException, NoSuchAlgorithmException;

}
