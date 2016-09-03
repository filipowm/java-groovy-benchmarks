package com.filipowm.benchmarks.runner.settings;

import com.beust.jcommander.Parameter;

public final class VmOptions {
    public static final int XMX_DEFAULT = 2048;
    public static final int XMS_DEFAULT = 128;
    public static final int COMPILER_COUNT_DEFAULT = 2;

    @Parameter(names = "-xms")
    private Integer xms;
    @Parameter(names = "-xmx")
    private Integer xmx;
    @Parameter(names = "-compilers")
    private Integer compilerCount;
    @Parameter(names = "-server")
    private boolean isServer;
    @Parameter(names = "-tiered")
    private boolean tieredCompilation = true;

    public int getXms() {
        return xms != null ? xms : XMS_DEFAULT;
    }

    public void setXms(int xms) {
        this.xms = xms;
    }

    public int getXmx() {
        return xmx != null ? xmx : XMS_DEFAULT;
    }

    public void setXmx(int xmx) {
        this.xmx = xmx;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public Integer getCompilerCount() {
        return compilerCount != null ? compilerCount : COMPILER_COUNT_DEFAULT;
    }

    public void setCompilerCount(Integer compilerCount) {
        this.compilerCount = compilerCount;
    }

    public boolean isTieredCompilation() {
        return tieredCompilation;
    }

    public void setTieredCompilation(boolean tieredCompilation) {
        this.tieredCompilation = tieredCompilation;
    }
}
