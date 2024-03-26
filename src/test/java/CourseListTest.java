import org.example.Course;
import org.example.CourseList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.lang3.Range;

class CourseListTest {
    @Test
    void addCourse() throws Exception {
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);

        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        CourseList list1 = new CourseList();
        list1.addCourse(course1);
        list1.addCourse(course2);
        assertEquals(6,list1.getTotalCredits());

        assertTrue((course1.timesAsRange()[0] == null)||(course1.timesAsRange()[0].isOverlappedBy(course1.timesAsRange()[0])));
        assertTrue((course1.timesAsRange()[1] == null)||(course1.timesAsRange()[0].isOverlappedBy(course1.timesAsRange()[1])));
        assertTrue((course1.timesAsRange()[2] == null)||(course1.timesAsRange()[0].isOverlappedBy(course1.timesAsRange()[2])));
        assertTrue((course1.timesAsRange()[3] == null)||(course1.timesAsRange()[0].isOverlappedBy(course1.timesAsRange()[3])));
        assertTrue((course1.timesAsRange()[4] == null)||(course1.timesAsRange()[0].isOverlappedBy(course1.timesAsRange()[4])));
    }

    @Test
    void removeCourse() {
    }

    @Test
    void testToString() {
    }
}