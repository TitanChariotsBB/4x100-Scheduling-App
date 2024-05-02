package org.example;

import java.io.FileNotFoundException;

public class Main {
    public static Search search;
    public static CourseList fallSemester;
    public static CourseList springSemester;
    public static CourseList past;
    public static CourseList future;
    public static Major major;

    public static void run() {
        LogHelper.initLogger();
        major = new Major("Entrepreneurship");

        CourseList catalog = FileHandler.loadCatalog();
        LogHelper.logMessage("Catalog loading complete");


        search = new Search(catalog);
        LogHelper.logMessage("Search initialization complete");

        try {
            autoLoad();
            LogHelper.logMessage("Previously autosaved courseLists were successfully loaded");
        }catch(FileNotFoundException fnfe){
            LogHelper.logError("One or more autosaved courseLists could not be found");
            springSemester = new CourseList();
            fallSemester = new CourseList();
            past = new CourseList();
            future = new CourseList();
        }
        MainApp.launchGUI();
        autoSave();
        LogHelper.logMessage("autoSave complete. Program terminating.");
    }

    public static void main(String[] args) {
        run();
    }

    public static void autoSave(){
        String saveFolder = FileHandler.getDefaultPath(""); //ends with \

        FileHandler.saveList(springSemester,saveFolder+"default-spring.json",true);
        FileHandler.saveList(fallSemester,saveFolder+"default-fall.json",true);
        FileHandler.saveList(past,saveFolder+"default-past.json",true);
        FileHandler.saveList(future,saveFolder+"default-future.json",true);
    }

    public static void autoLoad() throws FileNotFoundException{
        String saveFolder = FileHandler.getDefaultPath(""); //ends with \

        CourseList springList = FileHandler.loadList(saveFolder + "default-spring.json");
        springSemester = new SemesterSchedule(springList, false);
        CourseList fallList = FileHandler.loadList(saveFolder + "default-fall.json");
        fallSemester = new SemesterSchedule(fallList, true);
        past = FileHandler.loadList(saveFolder + "default-past.json");
        future = FileHandler.loadList(saveFolder + "default-future.json");
    }
}