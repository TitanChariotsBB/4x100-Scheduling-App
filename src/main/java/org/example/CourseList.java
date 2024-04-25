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

        UserAction addition = new UserAction(this,course,Main.search,UserAction.actionType.ADD);
        LogHelper.logUserAction(addition);
    }

    public void removeCourse(Course course) throws Exception {
        if (!courses.remove(course)) {
            throw new Exception("org.example.Course not found!");
        }
        totalCredits -= course.getCredits();

        UserAction removal = new UserAction(this,course,Main.search,UserAction.actionType.REMOVE);
        LogHelper.logUserAction(removal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.getName() + "\n");
        }
        return sb.toString();
    }

}