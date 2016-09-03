package com.filipowm.benchmarks.java;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.model.ArbitraryMeasurement;
import com.filipowm.benchmarks.CompressionBenchmark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;

public class JavaCompressionBenchmark implements CompressionBenchmark {
    @Param({
            "this string will compress badly",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"})
    private String toCompress;

    @Param({"bestCompression", "bestSpeed", "noCompression", "huffmanOnly"})
    private String compressionLevel;

    public static final Map<String, Integer> compressionLevelMap = new HashMap<>();
    static {
        compressionLevelMap.put("bestCompression", Deflater.BEST_COMPRESSION);
        compressionLevelMap.put("bestSpeed", Deflater.BEST_SPEED);
        compressionLevelMap.put("noCompression", Deflater.NO_COMPRESSION);
        compressionLevelMap.put("huffmanOnly", Deflater.HUFFMAN_ONLY);
    }

    @Override
    @Benchmark
    public void zip(long repeatCount) {
        for (int i = 0; i < repeatCount; i ++) {
            compress(toCompress.getBytes());
        }
    }

    @ArbitraryMeasurement(units = ":1", description = "ratio of uncompressed to compressed")
    public double compressionSize() {
        byte[] initialBytes = toCompress.getBytes();
        byte[] finalBytes = compress(initialBytes);
        return (double) initialBytes.length / (double) finalBytes.length;
    }

private byte[] compress(byte[] bytes) {
    Deflater compressor = new Deflater();
    compressor.setLevel(compressionLevelMap.get(compressionLevel));
    compressor.setInput(bytes);
    compressor.finish();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    while (!compressor.finished()) {
        int count = compressor.deflate(buf);
        bos.write(buf, 0, count);
    }
    try {
        bos.close();
    } catch (IOException e) {
    }
    return bos.toByteArray();
}
}

