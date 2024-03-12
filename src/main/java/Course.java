import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    private String name;
    private String code;
    private ArrayList<LocalDateTime[]> meetingTimes;
    private boolean isFall;
    private String description;
    private String location;
    private String professor;
    private int credits;
    private ArrayList<String> prerequisites;

    public Course(String name, String code, ArrayList<LocalDateTime[]> meetingTimes, boolean isFall,
                  String description, String location, String professor,
                  int credits, ArrayList<String> prerequisites) {
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
        return false;
    }
}
