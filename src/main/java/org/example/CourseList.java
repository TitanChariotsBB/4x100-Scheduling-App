package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class CourseList {
    private ArrayList<Course> courses;
    private int totalCredits;

    public CourseList(){
        courses = new ArrayList<Course>();
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public int getTotalCredits(){return totalCredits;}

    public void addCourse(Course course) throws IllegalArgumentException {
        if(totalCredits+course.getCredits() > 19) {
            throw new IllegalArgumentException("org.example.Too many credits");
        }
        courses.add(course);
        totalCredits += course.getCredits();
    }

    public void removeCourse(Course course) throws Exception {
        if (!courses.remove(course)) {
            throw new Exception("org.example.Course not found!");
        }
        totalCredits -= course.getCredits();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.getName());
        }
        return sb.toString();
    }
}
