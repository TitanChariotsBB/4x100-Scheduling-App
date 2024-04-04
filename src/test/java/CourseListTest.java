import org.example.Course;
import org.example.CourseList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CourseListTest {
    @Test
    void addCourse() throws Exception {
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1, true, "A good class", "STEM 376", "Dr. Bibza", 3, null);

        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2, true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        CourseList list1 = new CourseList();
        list1.addCourse(course1);
        list1.addCourse(course2);
        assertEquals(6, list1.getTotalCredits());

        LocalDateTime time3 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime time4 = LocalDateTime.of(2024,1,1,10,0);
        LocalDateTime time5 = LocalDateTime.of(2024,1,1,12,0);
        LocalDateTime time6 = LocalDateTime.of(2024,1,1,12,30);

        LocalDateTime[][] meetings3 = {{time3, time4}, null, {time3, time4}, null, {time3, time4}};
        LocalDateTime[][] meetings4 = {{time5, time6}, null, {time5, time6}, null, {time5, time6}};
        Course course11 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings3, true, "A better class", "HAL 116", "Dr. Bibza", 3, null);
        Course course12 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings4, true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

    }

    @Test
    void removeCourse() {
    }

    @Test
    void testToString() {
    }
}