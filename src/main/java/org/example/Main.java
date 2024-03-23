package org.example;

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
        search = new Search();

        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);
        fallSemester = new CourseList();
        fallSemester.addCourse(course1);
        fallSemester.addCourse(course2);

        MainApp.launchGUI();
    }

}
