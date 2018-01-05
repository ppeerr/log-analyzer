package com.per.project.configuration;

public abstract class AppConfiguration {

    private static float minAvailabilityRatio; //in percents
    private static float maxResponseTime;
    private static boolean debugMode = false;

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        AppConfiguration.debugMode = debugMode;
    }

    public static float getMinAvailabilityRatio() {
        return minAvailabilityRatio;
    }

    public static void setMinAvailabilityRatio(float minAvailabilityRatio) {
        AppConfiguration.minAvailabilityRatio = minAvailabilityRatio;
    }

    public static float getMaxResponseTime() {
        return maxResponseTime;
    }

    public static void setMaxResponseTime(float maxResponseTime) {
        AppConfiguration.maxResponseTime = maxResponseTime;
    }
}
