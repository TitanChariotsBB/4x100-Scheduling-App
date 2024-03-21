package org.example;

public class Main {
    private static CourseList catalog;

    public static void run() {
        catalog = FileHandler.loadCatalog();
        // run
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
        MainApp.main(args);
    }

}
