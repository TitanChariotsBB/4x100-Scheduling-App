package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Stack;

public class LogHelper {
    protected static Logger logger = initLogger();
    private static Stack<UserAction> actionStack = new Stack<>();

    public static Logger initLogger(){
        System.setProperty("log4j.configurationFile","src\\main\\resources\\org\\example\\log4jProperties.xml");
        org.apache.logging.log4j.Logger result1 = LogManager.getLogger("fileLogger");
        Logger result = (Logger) result1;

        return result;
    }

    public static void logError(String errorMessage){
        logger.error(errorMessage);
    }

    public static void logUserAction(UserAction ua){
        actionStack.push(ua);
        logger.info(ua);
    }
}
