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
        // I am aware most of this stuff should go in run eventually. This is just for testing
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "COMP 301", meetings2,true, "A better class", "HAL 116", "Dr. Harvey", 3, null);
        LocalDateTime time3 = LocalDateTime.of(2024, 1, 1, 12, 30);
        LocalDateTime[][] meetings3 = {null, {time3, time3}, null, {time3, time3}, null};
        Course course3 = new Course("Visual novel writing", "HUMA 501", meetings3,true, "A transcendental, relativistic, and egoistic class", "HAL 306", "Dr. Lipnichan", 3, null);

        catalog = new CourseList();
        catalog.addCourse(course1);
        catalog.addCourse(course2);
        catalog.addCourse(course3);

        fallSemester = new CourseList();
        fallSemester.addCourse(course1);
        fallSemester.addCourse(course2);
        fallSemester.addCourse(course3);
        springSemester = new CourseList();
        future = new CourseList();
        past = new CourseList();
        search = new Search(catalog);

        MainApp.launchGUI();
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
