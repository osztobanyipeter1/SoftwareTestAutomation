package com.testautomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final Properties ROOT = new Properties();
    private static final Properties ENV = new Properties();
    private static String activeEnv;
    // Load properties at class initialization
    static {
        try {
            try (InputStream is = resource("/config.properties")) {
                if (is != null) ROOT.load(is);
            }
            activeEnv = System.getProperty("env", ROOT.getProperty("env", "dev")).trim();
            String envPath = "/env/" + activeEnv + ".properties";
            try (InputStream is = resource(envPath)) {
                if (is != null) ENV.load(is);
                else throw new IllegalStateException("Missing properties for env: " + envPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private static InputStream resource(String path) {
        return Config.class.getResourceAsStream(path);
    }

    public static String env() { return activeEnv; }
    public static String baseUri() { return mustGet(ENV, "baseUri"); }
    public static int connectTimeoutMs() { return Integer.parseInt(ENV.getProperty("connectTimeoutMs", "5000")); }
    public static int readTimeoutMs() { return Integer.parseInt(ENV.getProperty("readTimeoutMs", "10000")); }

    private static String mustGet(Properties p, String key) {
        String v = p.getProperty(key);
        return Objects.requireNonNull(v, () -> "Missing config key: " + key);
    }
    public static String get(String key) {
        // First, check env-specific properties
        String v = ENV.getProperty(key);
        if (v != null) return v;
        // Then, root
        v = ROOT.getProperty(key);
        if (v != null) return v;
        // Finally, system property
        return System.getProperty(key);
    }

}
