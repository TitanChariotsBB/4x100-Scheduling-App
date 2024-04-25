package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
        /*StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.getName() + "\n");
        }
        return sb.toString();
         */
        return "list of " + courses.size() + " courses stored at: " + super.toString();
    }
}