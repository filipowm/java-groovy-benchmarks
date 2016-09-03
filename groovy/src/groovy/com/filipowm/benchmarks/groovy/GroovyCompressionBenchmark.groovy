package com.filipowm.benchmarks.groovy

import com.google.caliper.Benchmark
import com.google.caliper.Param
import com.google.caliper.model.ArbitraryMeasurement
import groovy.transform.CompileStatic
import com.filipowm.benchmarks.CompressionBenchmark

import java.util.zip.Deflater

@CompileStatic
class GroovyCompressionBenchmark implements CompressionBenchmark {
    @Param([
        "this string will compress badly",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"])
    private String toCompress;

    @Param(["bestCompression", "bestSpeed", "noCompression", "huffmanOnly"])
    private String compressionLevel;

    public static final Map<String, Integer> compressionLevelMap = [:];
    static {
        compressionLevelMap["bestCompression"] = Deflater.BEST_COMPRESSION;
        compressionLevelMap["bestSpeed"] = Deflater.BEST_SPEED;
        compressionLevelMap["noCompression"] = Deflater.NO_COMPRESSION;
        compressionLevelMap["huffmanOnly"] = Deflater.HUFFMAN_ONLY;
    }

    @Override
    @Benchmark
    public void zip(long repeatCount) {
        for (int i = 0; i < repeatCount; i ++) {

            compress(toCompress.bytes);
        }
    }

    @ArbitraryMeasurement(units = ":1", description = "ratio of uncompressed to compressed")
    public double compressionSize() {
        byte[] initialBytes = toCompress.bytes;
        byte[] finalBytes = compress(initialBytes);
        return (double) initialBytes.length / (double) finalBytes.length;
    }

    private byte[] compress(byte[] bytes) {
        Deflater compressor = new Deflater();
        compressor.level = compressionLevelMap[compressionLevel];
        compressor.input = bytes;
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
