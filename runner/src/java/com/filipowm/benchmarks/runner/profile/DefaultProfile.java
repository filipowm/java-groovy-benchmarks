package com.filipowm.benchmarks.runner.profile;

import com.filipowm.benchmarks.runner.settings.VmOptions;

@ProfileName("default")
public class DefaultProfile implements Profile {

    private String targetName;
    private VmOptions vmOptions;

    @Override
    public String getTargetName() {
        return targetName;
    }

    @Override
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public VmOptions getVmOptions() {
        return vmOptions;
    }

    @Override
    public void setVmOptions(VmOptions vmOptions) {
        this.vmOptions = vmOptions;
    }
}
