package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.UserAction.actionType;

import java.time.LocalDateTime;
import java.util.Stack;

public class LogHelper {
    protected static Logger logger;
    private static Stack<UserAction> actionStack = new Stack<>();
    private static Stack<UserAction> undoStack = new Stack<>();

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
        actionStack.push(ua);
    }

    public static void logProgressMessage(String s){
        logger.info(s);
    }

    public static void undo(){
        UserAction action;
        do {
            action = actionStack.pop();
            CourseList list = action.getAffectedList();
            Course c = action.getAffectedCourse();
            switch (action.getaType()) {
                case actionType.ADD_COURSE:
                    try {
                        list.removeCourse(c);
                    } catch (Exception e) {
                        logError("Could not undo adding course: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case actionType.REMOVE_COURSE:
                    list.addCourse(c);
                    break;
            }
        }while(action.getaType() != actionType.ADD_COURSE && action.getaType() != actionType.REMOVE_COURSE);
    }
}
