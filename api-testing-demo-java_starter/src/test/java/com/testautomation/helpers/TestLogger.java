package com.testautomation.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);

    public static void logTestStart(String testName) {
        logger.info("========================================");
        logger.info("TEST START: " + testName);
        logger.info("========================================");
    }

    public static void logTestEnd(String testName) {
        logger.info("========================================");
        logger.info("TEST END: " + testName);
        logger.info("========================================");
    }

    public static void logApiCall(String method, String endpoint) {
        logger.debug("API CALL: {} {}", method, endpoint);
    }

    public static void logResponse(int statusCode, String body) {
        logger.debug("RESPONSE: Status={}, Body={}", statusCode, body);
    }

    public static void logAssertion(String assertion) {
        logger.debug("ASSERTION: {}", assertion);
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
