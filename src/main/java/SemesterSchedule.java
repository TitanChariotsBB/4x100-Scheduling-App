import java.awt.*;
import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private ArrayList<Course> courses;
    private Boolean isFall;

    public SemesterSchedule(){
        // we'll probably want a constructor that loads from a file.
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tMonday \tTuesday \tWednesday \tThursday \tFriday\n");

        String[] rows = new String[24];

        for (Course course : courses) {
            String code = course.getCode();
            for (int i = 0; i < 5; i++) {
                Point p = course.getMeetingTimes().get(i);
                String space = "\t";
                switch (i) {
                    case 1:
                        space = "      \t";
                        break;
                    case 2:
                        space = "            \t";
                        break;
                    case 3:
                        space = "                  \t";
                        break;
                    case 4:
                        space = "                        \t";
                        break;
                    default:
                        space = "";
                }
                if (p != null) {
                    int idx = p.x * 2 - 16;
                    rows[idx] = space + code + "\n";
                }
            }
        }
        return "";
    }
}
