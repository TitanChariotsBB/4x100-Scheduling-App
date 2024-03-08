import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private ArrayList<Course> courses;
    private Boolean isFall;

    public SemesterSchedule(){
        //we'll probably want a constructor that loads from a file.
    }

    public void addCourse(Course course) {
        // checks for conflict
    }

    @Override
    public String toString() { return ""; }
}
