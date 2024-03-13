import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            sb.append(courses.get(i));
        }
        return sb.toString();
    }
}
