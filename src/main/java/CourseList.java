import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;

    public void addCourse(Course course) {

    }

    public void removeCourse(Course course) throws Exception {
        if (!courses.remove(course)) {
            throw new Exception("Course not found!");
        }
    }

    @Override
    public String toString() { return ""; }
}
