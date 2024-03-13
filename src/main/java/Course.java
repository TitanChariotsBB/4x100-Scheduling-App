import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    private String name;
    private String code;
    private LocalDateTime[][] meetingTimes;
    private String description;
    private String location;
    private String professor;
    private int credits;
    private ArrayList<String> prerequisites;

    public Course(String name, String code, LocalDateTime[][] meetingTimes,
                  String description, String location, String professor,
                  int credits, ArrayList<String> prerequisites) {
        this.name = name;
        this.code = code;
        this.meetingTimes = meetingTimes; 
        this.description = description;
        this.location = location;
        this.professor = professor;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    // Hey hey this is Alex Zeilstra changing up the Course claaassss

    public boolean overlapsWith(Course toCompare) {
        return false;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime[][] getMeetingTimes() {
        return meetingTimes;
    }
}
