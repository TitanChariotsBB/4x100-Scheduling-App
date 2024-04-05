package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private Boolean isFall;

    public SemesterSchedule(Boolean isFall){
        super();
        this.isFall = isFall;
        // we'll probably want a constructor that loads from a file.
    }

    public SemesterSchedule(CourseList listToConvert, Boolean isFall){
        super();
        this.isFall = isFall;

        for(Course course : listToConvert.getCourses()){
            this.addCourse(course);
        }
    }

    @Override
    public int addCourse(Course course) {
        int errno = 0;
        for (Course existingCourse : super.getCourses()) {
            if (course.overlapsWith(existingCourse)) {
                errno = 1;
            }
        }
        if(super.getTotalCredits() + course.getCredits() > 19) {
            errno = 2;
        }
        if (course.getPrerequisites() != null) {
            for (int i = 0; i < course.getPrerequisites().size(); i++) {
                if (!pastCourses().contains(course.getPrerequisites().get(i))) {
                    errno = 3;
                }
            }
        }
        super.addCourse(course);//adds course to the arrayList and increments totalCredits
        return errno;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tMonday   \tTuesday  \tWednesday\tThursday \tFriday\n");

        String[] rows = {"\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n"};

        for (Course course : super.getCourses()) {
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