package org.example.rent.other;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLogger {

    private static final Logger logger = LogManager.getLogger(CustomLogger.class);

    private CustomLogger() {}

    public static Logger getLog() {
        return logger;
    }

}
