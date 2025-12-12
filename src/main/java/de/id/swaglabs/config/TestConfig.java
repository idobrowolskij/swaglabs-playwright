package de.id.swaglabs.config;

import de.id.swaglabs.util.PropertyReader;

public class TestConfig {
    public static String baseUrl() {
        return PropertyReader.get("base.url");
    }

    public static String standardUser() {
        return PropertyReader.get("user.standard");
    }

    public static String password() {
        return PropertyReader.get("user.password");
    }
}
