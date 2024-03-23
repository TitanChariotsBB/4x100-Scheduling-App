import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;

    public CourseList(){
        courses = new ArrayList<Course>();
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) throws Exception {
        if (!courses.remove(course)) {
            throw new Exception("Course not found!");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.getName());
        }
        return sb.toString();
    }
}
