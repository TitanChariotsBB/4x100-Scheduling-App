import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private ArrayList<Course> courses;
    private Boolean isFall;

    public void addCourse(Course course) {
        boolean overlapsWith = false;
        if (courses != null) {
            for (Course cours : courses) {
                if (course.overlapsWith(cours)) {
                    overlapsWith = true;
                    System.out.println("Error: Overlaps with course " + cours.getName());
                }
            }
            if (!overlapsWith) {
                courses.add(course);
            }
        }
        else {
            courses = new ArrayList<>();
            courses.add(course);
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
