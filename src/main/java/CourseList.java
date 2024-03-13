import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;
    private int totalCredits;

    public void addCourse(Course course) {
        totalCredits = totalCredits + course.getCredits();
    }

    public void removeCourse(Course course) {
        totalCredits = totalCredits - course.getCredits();
    }

    @Override
    public String toString() { return ""; }
}
