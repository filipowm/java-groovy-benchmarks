package com.filipowm.benchmarks;

import com.google.caliper.api.VmOptions;


@VmOptions({"-Xmx4096M", "-Xms128M"})
public @interface Setup {
}
