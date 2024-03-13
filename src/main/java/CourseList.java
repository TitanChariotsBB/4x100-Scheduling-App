import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;
    private int totalCredits;

    public void addCourse(Course course) {
        if(totalCredits+course.getCredits() > 19){
            throw new RuntimeException("You have attempted to add more than 19 credits worth of classes");
        }
        
        totalCredits = totalCredits + course.getCredits();
    }

    public void removeCourse(Course course) {
        totalCredits = totalCredits - course.getCredits();
    }

    @Override
    public String toString() { return ""; }
}
