package org.example;

public class Main {
    private static CourseList catalog;
    public static Search search;

    public static void run() {
        catalog = FileHandler.loadCatalog();
        // run
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
        search = new Search();
        MainApp.launchGUI();
    }

}
