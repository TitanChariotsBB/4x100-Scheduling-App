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

        //TEST Course.overlapsWith

        LocalDateTime t1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime t2 = LocalDateTime.of(2024, 1, 1, 10, 0);

        LocalDateTime t3 = LocalDateTime.of(2024, 1, 1, 11, 0);
        LocalDateTime t4 = LocalDateTime.of(2024, 1, 1, 11, 30);

        LocalDateTime[][] m1 = {null, {t1, t2}, null, null, null};
        LocalDateTime[][] m2 = {null, {t3, t4}, null, null, null};

        Course c1 = new Course("Foundations of balloon fabrication", "HUMA 301", m2, true, "A better class", "HAL 116", "Dr. Bibza", 3, null);
        Course c2 = new Course("Foundations of balloon fabrication", "HUMA 301", m1, true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        assertFalse(c1.overlapsWith(c2));
        assertEquals(c1.overlapsWith(c2), c2.overlapsWith(c1));

        //TEST adding two overlapping courses

        list1.addCourse(c1);
        list1.addCourse(c2);
    }

    @Test
    void removeCourse() {
    }

    @Test
    void testToString() {
    }
}