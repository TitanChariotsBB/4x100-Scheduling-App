package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.time.LocalDateTime;
import java.util.Stack;

public class LogHelper {
    protected static Logger logger;
    private static Stack<UserAction> actionStack = new Stack<>();

    public static void initLogger(){
        System.setProperty("log4j.configurationFile","src\\main\\resources\\org\\example\\log4jProperties.xml");
        org.apache.logging.log4j.Logger result1 = LogManager.getLogger("fileLogger");
        logger = (Logger) result1;

        logger.info("Logger successfully initialized");
    }

    public static void logError(String errorMessage){
        logger.error(errorMessage);
    }

    public static void logUserAction(UserAction ua){
        logger.info(ua);
    }

    public static void logProgressMessage(String s){
        logger.info(s);
    }

    public static void undoPreviousAction() {
        // TODO
    }
}
