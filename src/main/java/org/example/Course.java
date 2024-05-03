package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    private String name;
    private String code;
    private LocalDateTime[][] meetingTimes;
    private Boolean isFall;
    private String description;
    private String location;
    private String professor;
    private Integer credits;
    private ArrayList<String> prerequisites;

    public Course(String code) { //create a course with only a code
        this.name = "";
        this.code = code;
        isFall = null;
        meetingTimes = null;
        description = "";
        location = "";
        professor = "";
        credits = 0;
        prerequisites = null;
    }

    public Course(String name, String code, LocalDateTime[][] meetingTimes, Boolean isFall,
                  String description, String location, String professor,
                  Integer credits, ArrayList<String> prerequisites) {
        this.name = name;
        this.code = code;
        this.isFall = isFall;
        this.meetingTimes = meetingTimes; 
        this.description = description;
        this.location = location;
        this.professor = professor;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    public void setPrerequisites(ArrayList<String> in) {
        prerequisites = in;
    }

    public boolean overlapsWith(Course toCompare) {
        double aStart;
        double aEnd;
        double bStart;
        double bEnd;

        for (int i = 0; i < 5; i++) {
            if (meetingTimes[i] == null || toCompare.meetingTimes[i] == null) continue;

            aStart = meetingTimes[i][0].getHour() + (meetingTimes[i][0].getMinute() / 60.0);
            aEnd = meetingTimes[i][1].getHour() + (meetingTimes[i][1].getMinute() / 60.0);
            bStart = toCompare.meetingTimes[i][0].getHour() +
                    (toCompare.meetingTimes[i][0].getMinute() / 60.0);
            bEnd = toCompare.meetingTimes[i][1].getHour() +
                    (toCompare.meetingTimes[i][1].getMinute() / 60.0);

            // If the classes start at the same time
            if (aStart == bStart) return true;

            // If the first class starts in the middle of the second class
            if (aStart > bStart && aStart < bEnd) return true;

            // If the first class ends in the middle of the second class
            if (aEnd > bStart && aEnd < bEnd) return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime[][] getMeetingTimes() {
        return meetingTimes;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getProfessor() {
        return professor;
    }

    public String getMeetingTimeString() {
        String day = "";
        int hour = 0;
        int minute = 0;

        if (meetingTimes[0] != null) {
            day += "M";
            hour = meetingTimes[0][0].getHour();
            minute = meetingTimes[0][0].getMinute();
        }
        if (meetingTimes[1] != null) {
            day += "T";
            hour = meetingTimes[1][0].getHour();
            minute = meetingTimes[1][0].getMinute();
        }
        if (meetingTimes[2] != null)
            day += "W";
        if (meetingTimes[3] != null)
            day += "R";
        if (meetingTimes[4] != null)
            day += "F";

        String minuteString;
        if (minute < 10) minuteString = "0" + minute;
        else minuteString = "" + minute;

        String time = " " + hour + ":" + minuteString;

        return day + time;
    }

    public String getMeetingDateString() {
        String day = "";

        if (meetingTimes[0] != null) {
            day += "M";
        }
        if (meetingTimes[1] != null) {
            day += "T";
        }
        if (meetingTimes[2] != null)
            day += "W";
        if (meetingTimes[3] != null)
            day += "R";
        if (meetingTimes[4] != null)
            day += "F";

        if (!day.equals("MWF") && !day.equals("TR")) {
            return "Other";
        }

        return day;
    }

    public ArrayList<String> getPrerequisites() {
        //ArrayList<Course> prereqs = new ArrayList<>();
        return prerequisites;
    }

    public ArrayList<Course> unmetPrereq() { //checks if classes have unmet prereqs
        if (prerequisites != null) {
            int unmetCount = prerequisites.size();
            ArrayList<String> newPre = new ArrayList<>();
            newPre.addAll(prerequisites); //gets an arraylist with all prerequisites
            for (String prereq : prerequisites) { //loop through all prereqs
                for (Course c : Main.past.getCourses()) { //loop through past courses
                    if (prereq.equalsIgnoreCase(c.getCode())) { //if past contains prerequisite, lower count
                        unmetCount--;
                        newPre.remove(c.getCode()); //remove the course from prereqs
                        System.out.println("prereq " + c.getCode() + " met");
                        break;
                    }
                }
            }
            if (unmetCount > 0) {
                ArrayList<Course> out = new ArrayList<>();
                for (int i = 0; i < unmetCount; i++) {
                    out.add(new Course(newPre.get(i))); //return the unmet prereqs as an arraylist
                    return out;
                }
            }
        }
        return null;
    }

    public ArrayList<String> pastCourses() { //an arraylist to return all past courses as course codes
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < Main.past.getCourses().size(); i++) {
            out.add(Main.past.getCourses().get(i).getCode());
        }
        return out;
    }

    public String getMeetingTimeStringAlex() {
        String time;
        int hour = 0;
        int minute = 0;

        if (meetingTimes[0] != null) {
            hour = meetingTimes[0][0].getHour();
            minute = meetingTimes[0][0].getMinute();
        }
        if (meetingTimes[1] != null) {
            hour = meetingTimes[1][0].getHour();
            minute = meetingTimes[1][0].getMinute();
        }
        if (hour < 12) {
            time = hour + ":" + minute;
            if (minute == 0) time += "0";
            time += " AM";
        } else if (hour == 12) {
            time = hour + ":" + minute;
            if (minute == 0) time += "0";
            time += " PM";
        } else {
            time = (hour - 12) + ":" + minute;
            if (minute == 0) time += "0";
            time += " PM";
        }

        return time;
    }

    public boolean getIsFall() {
        return isFall;
    }
}
