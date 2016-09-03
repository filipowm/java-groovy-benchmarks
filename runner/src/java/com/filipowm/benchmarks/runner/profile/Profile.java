package com.filipowm.benchmarks.runner.profile;

import com.google.common.base.Strings;
import org.reflections.Reflections;
import com.filipowm.benchmarks.runner.exception.CaliperRunnerException;
import com.filipowm.benchmarks.runner.exception.VmOptionsNotSupportedException;
import com.filipowm.benchmarks.runner.settings.VmOptions;
import com.filipowm.benchmarks.runner.settings.VmOptionsSupport;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Profile extends VmOptionsSupport {

    String getTargetName();
    void setTargetName(String targetName);

    default ProfileType getProfileType() {
        return ProfileType.NORMAL;
    }
    default Target getTarget() {
        return Target.PACKAGE;
    }
    default Scope getScope() {
        return Scope.BRANCH;
    }

    default VmOptions getVmOptions() {
        throw new VmOptionsNotSupportedException();
    }

    default void setVmOptions(VmOptions vmOptions) {
        throw new VmOptionsNotSupportedException();
    }

    static List<Profile> fromOptions(Map<String, String> options) throws InstantiationException, IllegalAccessException {
        return options.entrySet().stream()
                                .filter(entry -> !Strings.isNullOrEmpty(entry.getKey()))
                                .filter(entry -> !Strings.isNullOrEmpty(entry.getValue()))
                                .map(entry -> ProfileHelper.getProfilesForName(entry.getKey())
                                                           .stream()
                                                           .map(clss -> MappingEntity.get(clss, entry.getValue().split(",")))
                                                           .collect(Collectors.toSet()))
                                .flatMap(Collection::stream)
                                .map(entities -> entities.stream()
                                                         .map(MappingEntity::toProfile)
                                                         .collect(Collectors.toList()))
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList());
    }

    class MappingEntity {
        Class<? extends Profile> clss;
        String targetName;

        Profile toProfile() {
            try {
                Profile profile = clss.newInstance();
                profile.setTargetName(targetName);
                return profile;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        static List<MappingEntity> get(Class<? extends Profile> clss, String[] targetNames) {
            List<MappingEntity> entities = new ArrayList<>();
            for (String name : targetNames) {
                MappingEntity me = new MappingEntity();
                me.clss = clss;
                me.targetName = name;
                entities.add(me);
            }
            return entities;
        }
    }

    class ProfileHelper {
        private static final Reflections reflections = new Reflections("");
        private ProfileHelper() {}

        private static Predicate<Class<? extends Profile>> annotatedPredicate = (clss) -> clss.isAnnotationPresent(ProfileName.class);
        private static Predicate<Class<? extends Profile>> annotated(final String name) {
            return annotatedPredicate
                    .and(clss -> Arrays.asList(clss.getDeclaredAnnotation(ProfileName.class).value())
                            .stream()
                            .map(String::toUpperCase)
                            .anyMatch(value -> value.equals(name)));
        }
        private static Predicate<Class<? extends Profile>> named(final String name) {
            return clss -> clss.getName().toUpperCase().equals(name);
        }

        static Set<Class<? extends Profile>> getSubtypeProfiles() {
            return reflections.getSubTypesOf(Profile.class);
        }

        static Set<Class<? extends Profile>> getProfilesForName(String name) {
            if (name == null) {
                throw new CaliperRunnerException();
            }
            return getSubtypeProfiles().stream()
                    .filter(annotated(name.toUpperCase())
                            .or(named(name.toUpperCase())))
                    .collect(Collectors.toSet());
        }
    }
}
