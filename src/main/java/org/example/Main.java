package org.example;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class Main {
    private static CourseList catalog;
    public static Search search;
    public static CourseList fallSemester;
    public static CourseList springSemester;
    public static CourseList past;
    public static CourseList future;

    public static void run() {
        catalog = FileHandler.loadCatalog();
        System.out.println(catalog);
        search = new Search(catalog);

        try {
            autoLoad();
        }catch(FileNotFoundException fnfe){
            springSemester = new CourseList();
            fallSemester = new CourseList();
            past = new CourseList();
            future = new CourseList();
        }
        MainApp.launchGUI();
        autoSave();
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

        springSemester = FileHandler.loadList(saveFolder + "default-spring.json");
        fallSemester = FileHandler.loadList(saveFolder + "default-fall.json");
        past = FileHandler.loadList(saveFolder + "default-past.json");
        future = FileHandler.loadList(saveFolder + "default-future.json");
    }
}
