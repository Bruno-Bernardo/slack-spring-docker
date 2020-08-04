package com.brunober.slackspringdocker.utils;

public class JiraUtils {

    public static String getValidIssueKey(String key) throws RuntimeException {
        key.toUpperCase();
        if (!key.contains("SIG-")) {
            if (key.matches("\\d+")) {
                key = "SIG-" + key;
            } else {
                throw new RuntimeException("Invalid key: " + key);
            }
        }

        return key;
    }
}
