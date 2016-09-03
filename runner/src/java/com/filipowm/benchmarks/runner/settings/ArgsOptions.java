package com.filipowm.benchmarks.runner.settings;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.filipowm.benchmarks.runner.profile.Profile;

import java.util.*;
import java.util.stream.Collectors;

public class ArgsOptions implements VmOptionsSupport {

    @ParametersDelegate
    private VmOptions vmOptions = new VmOptions();
    @Parameter(names = {"-e", "--exit"})
    private boolean preventExit;
    @DynamicParameter(names = {"-P", "--profile"}, description = "Profile targets, if empty then profile is applied to any target within project")
    private Map<String, String> targets = new HashMap<>();
    @DynamicParameter(names = {"-C", "--caliper"}, description = "Caliper settings")
    private Map<String, String> caliper = new HashMap<>();

    public List<Profile> profiles() {
        try {
            return Profile.fromOptions(targets);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public VmOptions getVmOptions() {
        return vmOptions;
    }

    @Override
    public void setVmOptions(VmOptions vmOptions) {
        this.vmOptions = vmOptions;
    }

    public String[] getCaliper() {
        List<String> list = caliper.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> entry.getKey() != null)
                .map(entry -> "-" + entry.getKey().trim() + "" + entry.getValue().trim())
                .collect(Collectors.toList());
        return list.toArray(new String[list.size()]);
    }
}
