import org.example.Course;
import org.example.CourseList;
import org.example.Search;
import org.example.SemesterSchedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


public class SearchTest {

    @Test
    public void testToString() {
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);

        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        LocalDateTime time3 = LocalDateTime.of(2024, 1, 1, 12, 30);
        LocalDateTime[][] meetings3 = {null, {time3, time3}, null, {time3, time3}, null};
        Course course3 = new Course("Visual novel writing", "WRIT 501", meetings3,true, "A transcendental, relativistic, and egoistic class", "HAL 306", "Dr. Lipnichan", 3, null);

        CourseList cs = new CourseList();

        cs.addCourse(course1);
        cs.addCourse(course2);
        cs.addCourse(course3);

        Search s = new Search(cs);
        assertEquals(3, s.getResults().size());
        s.addFilter(Search.SearchBy.PROFESSOR, "Lipnichan");
        assertEquals(1, s.getResults().size());
    }
}
