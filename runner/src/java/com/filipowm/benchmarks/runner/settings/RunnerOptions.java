package com.filipowm.benchmarks.runner.settings;

import com.google.caliper.options.CaliperOptions;
import com.filipowm.benchmarks.runner.profile.Profile;

import java.util.Set;

public interface RunnerOptions extends VmOptionsSupport {

    boolean preventExit();
    Set<Profile> profiles();
    CaliperOptions caliper();

}
