package com.filipowm.benchmarks.runner;

import java.security.Permission;

public final class ExitPreventSecurityManager extends SecurityManager {
    private final SecurityManager parent;
    private static final SecurityManager INSTANCE = new ExitPreventSecurityManager();

    private ExitPreventSecurityManager(final SecurityManager parent) {
        this.parent = parent;
    }

    private ExitPreventSecurityManager() {
        this(System.getSecurityManager());
    }

    @Override
    public void checkPermission(final Permission perm) {
        if (parent != null) {
            parent.checkPermission(perm);
        }
    }

    @Override
    public void checkExit(int status) {
        throw new SecurityException("Cannot exit benchmark with status: " + status);
    }

    public static SecurityManager instance() {
        return INSTANCE;
    }
}
