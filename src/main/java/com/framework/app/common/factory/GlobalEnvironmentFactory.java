package com.framework.app.common.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GlobalEnvironmentFactory {

    public static final String PRD = "prd";
    public static final String STG = "stg";
    public static final String DEV = "dev";
    public static final String LOCAL = "local";

    @Autowired
    Environment environment;

    public String getActiveProfile() {
        String[] env = environment.getActiveProfiles();

        if(env == null || env.length == 0) {
            return LOCAL;
        } else {
            return env[0];
        }
    }

    public boolean hasProfiles(String... profile) {
        return environment.acceptsProfiles(profile);
    }
}
