package org.example;

import java.awt.*;
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

    public boolean overlapsWith(Course toCompare) {
        for (int i = 0; i < 5; i++) {
            if (meetingTimes[i] == null || toCompare.meetingTimes[i] == null) continue;

            String startTimeA = "" + meetingTimes[i][0].getHour() +
                    meetingTimes[i][0].getMinute();
            String startTimeB = "" + toCompare.meetingTimes[i][0].getHour() +
                    toCompare.meetingTimes[i][0].getMinute();

            if (startTimeA.equals(startTimeB)) return true;
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
        String time = "";
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

        time = " " + hour + ":" + minute;
        if (minute == 0) time += "0";

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

        return day;
    }

    public String getMeetingTimeStringAlex() {
        String time = "";
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
