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
        for (int i = 0; i < course.getPrerequisites().size(); i++) {
            if(!pastCourses().contains(course.getPrerequisites().get(i))) {
                System.out.println("Hey, you need the prerequisites to take this!!");
            }
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

    public ArrayList<String> pastCourses() {
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < Main.past.getCourses().size(); i++) {
            out.add(Main.past.getCourses().get(i).getCode());
        }
        return out;
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