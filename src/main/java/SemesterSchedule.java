import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private ArrayList<Course> courses; //ELliott is here
    private Boolean isFall;

    public SemesterSchedule(){
        //we'll probably want a constructor that loads from a file.
    }

    public void addCourse(Course course) {
        boolean overlapsWith = false;
        for (int i = 0; i < courses.size(); i++) {
            if (course.overlapsWith(courses.get(i))) {
                overlapsWith = true;
            }
        }
        if (!overlapsWith) {
            courses.add(course);
        }
    }

    @Override
    public String toString() { return ""; }
}
