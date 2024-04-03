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
        // run
    }

    public static void main(String[] args) {
        // I am aware most of this stuff should go in run eventually. This is just for testing
        System.out.println("Hello!");
        CourseList cs = new CourseList();
        search = new Search(cs);

        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);
        fallSemester = new CourseList();
        fallSemester.addCourse(course1);
        fallSemester.addCourse(course2);
        springSemester = new CourseList();
        past = new CourseList();
        future = new CourseList();
        past.addCourse(course1);
        future.addCourse(course2);

        MainApp.launchGUI();
    }

    public static void autoSave(){
        String saveFolder = FileHandler.getDefaultPath(""); //ends with \

        FileHandler.saveList(springSemester,saveFolder+"default-spring.json",true);
        FileHandler.saveList(fallSemester,saveFolder+"default-fall.json",true);
        FileHandler.saveList(past,saveFolder+"default-past.json",true);
        FileHandler.saveList(future,saveFolder+"default-future.json",true);
    }

    public static void autoLoad(){
        String saveFolder = FileHandler.getDefaultPath(""); //ends with \

        try {
            springSemester = FileHandler.loadList(saveFolder + "default-spring.json");
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Error: Spring File Not Found");
        }
        try {
            fallSemester = FileHandler.loadList(saveFolder + "default-fall.json");
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Error: Fall File Not Found");
        }
        try {
            past = FileHandler.loadList(saveFolder + "default-past.json");
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Error: Past File Not Found");
        }
        try {
            future = FileHandler.loadList(saveFolder + "default-future.json");
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Error: Future File Not Found");
        }
    }
}
