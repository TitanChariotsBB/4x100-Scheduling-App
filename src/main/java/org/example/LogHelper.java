package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.UserAction.actionType;

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
        if(ua.getaType() != actionType.UNDO) {
            actionStack.push(ua);
        }
    }

    public static void logMessage(String s){
        logger.info(s);
    }

    public static void undo(){
        if(actionStack.empty()){
            LogHelper.logMessage("Attempted to undo from an empty action stack");
            return;
        }

        UserAction action;
        actionType type;
        do {
            action = actionStack.pop();
            CourseList list = action.getAffectedList();
            Course c = action.getAffectedCourse();
            type = action.getaType();
            switch (type) {
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
                case actionType.ADD_IN_CONFLICT:
                    try {
                        list.removeCourse(c);
                    } catch (Exception e) {
                        logError("Could not undo adding course: " + e.getMessage());
                        e.printStackTrace();
                    }
                    UserAction associatedRemove = actionStack.pop();
                    if(associatedRemove.getaType() != actionType.REMOVE_IN_CONFLICT){logError("conflict add and remove actions were not connected as expected.");}
                    CourseList rList = associatedRemove.getAffectedList();
                    Course rCourse = associatedRemove.getAffectedCourse();
                    rList.addCourse(rCourse);
            }
        }while(type != actionType.ADD_COURSE && type != actionType.REMOVE_COURSE && type != actionType.ADD_IN_CONFLICT);
        LogHelper.logUserAction(new UserAction(null, null, UserAction.actionType.UNDO));
    }
}
