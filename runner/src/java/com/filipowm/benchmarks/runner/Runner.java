package com.filipowm.benchmarks.runner;

import com.filipowm.benchmarks.runner.profile.Profile;
import com.filipowm.benchmarks.runner.settings.VmOptionsSupport;

import java.io.IOException;

public interface Runner extends VmOptionsSupport {

    boolean supports(Profile profile);
    void setCaliperOptions(String[] options);
    void run(Profile profile) throws IOException;

    default void finalizeRunner() {
        System.gc();
        System.runFinalization();
    }

}
