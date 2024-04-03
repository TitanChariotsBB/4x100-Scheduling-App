package org.example;

import java.time.LocalDateTime;
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
            System.out.println("Above 19 credits!");
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
            sb.append(course.getName() + "\n");
        }
        return sb.toString();
    }

    public String getFormattedSchedule() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tMonday   \tTuesday  \tWednesday\tThursday \tFriday\n");

        String[] rows = {"\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n"};

        for (Course course : courses) {
            String code = course.getCode();
            int timeSliceIdx = 0;
            String timeSlice = "";
            for (int i = 0; i < 5; i++) {
                LocalDateTime[] day = course.getMeetingTimes()[i];
                if (day != null) {
                    timeSliceIdx = day[0].getHour() - 8;
                    timeSlice += "\t" + code + " ";
                } else {
                    timeSlice += "\t         ";
                }
            }
            rows[timeSliceIdx] = timeSlice;
        }
        for (String row : rows) {
            sb.append(row);
        }
        return sb.toString();
    }
}
