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

    // Hey hey this is Alex Zeilstra changing up the org.example.Course claaassss

    public boolean overlapsWith(Course toCompare) {
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

    public int getCredits(){return credits;}
}
