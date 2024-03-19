import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SemesterSchedule extends CourseList {
    private ArrayList<Course> courses;
    private Boolean isFall;

    public SemesterSchedule(){
        courses = new ArrayList<>();
        // we'll probably want a constructor that loads from a file.
    }

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
        sb.append("\tMonday   \tTuesday  \tWednesday\tThursday \tFriday\n");

        String[] rows = {"\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n"};

        for (Course course : courses) {
            String code = course.getCode();
            int timeSliceIdx = 0;
            String timeSlice = "";
            for (int i = 0; i < 5; i++) {
                LocalDateTime[] day = course.getMeetingTimes()[i];
                if (day != null) {
                    timeSliceIdx = day[0].getHour() - 8;
                    timeSlice += "\t" + code + " ";
                } else {
                    timeSlice += "\t         ";
                }
            }
            rows[timeSliceIdx] = timeSlice;
        }
        for (String row : rows) {
            sb.append(row);
        }
        return sb.toString();
    }
}
