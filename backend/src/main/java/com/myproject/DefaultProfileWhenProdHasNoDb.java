package com.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * If prod profile is active but no database URL is set (e.g. Fly has
 * SPRING_PROFILES_ACTIVE=prod but no Postgres secrets), switch to default
 * profile so the app starts with H2 instead of crashing.
 */
public class DefaultProfileWhenProdHasNoDb implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                      SpringApplication application) {
        String profile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (!"prod".equals(profile)) {
            return;
        }
        String url = System.getenv("SPRING_DATASOURCE_URL");
        if (url == null || url.isBlank()) {
            environment.setActiveProfiles("default");
        }
    }
}
