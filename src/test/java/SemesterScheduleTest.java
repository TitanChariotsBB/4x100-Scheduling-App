import org.example.Course;
import org.example.SemesterSchedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SemesterScheduleTest {

    @Test
    void addCourse() {
    }

    @Test
    void testToString() {
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);

        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        SemesterSchedule ss = new SemesterSchedule();
        ss.addCourse(course1);
        ss.addCourse(course2);
        System.out.println(ss);
        assertEquals(ss.toString(), ss.toString());
    }
}