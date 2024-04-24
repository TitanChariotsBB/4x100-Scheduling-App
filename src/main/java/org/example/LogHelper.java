package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LogHelper {
    public static Logger initLogger(){
        System.setProperty("log4j.configurationFile","src\\main\\resources\\org\\example\\log4jProperties.xml");

        org.apache.logging.log4j.Logger result1 = LogManager.getLogger("fileLog");
        Logger result = (Logger) result1;

        return result;
    }
}
