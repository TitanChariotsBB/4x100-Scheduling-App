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
        Course course2 = new Course("Foundations of balloon fabrication", "COMP 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        LocalDateTime time3 = LocalDateTime.of(2024, 1, 1, 12, 30);
        LocalDateTime[][] meetings3 = {null, {time3, time3}, null, {time3, time3}, null};
        Course course3 = new Course("Visual novel writing", "HUMA 501", meetings3,true, "A transcendental, relativistic, and egoistic class", "HAL 306", "Dr. Lipnichan", 3, null);

        //System.out.println("HELP ME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n \n\n\n\n\n\n");

        CourseList cs = new CourseList();

        cs.addCourse(course1);
        cs.addCourse(course2);
        cs.addCourse(course3);

        Search s = new Search(cs);
        assertEquals(3, s.getResults().size());
        s.addFilter(Search.SearchBy.PROFESSOR, "Lipnichan");
        /*for (int i = 0; i < s.getResults().size(); i++) {
            System.out.println("Yo: " + s.getResults().get(i).getProfessor());
        }*/
        //System.out.println(s.activeFilters.getFirst().filter);
        assertEquals(1, s.getResults().size());
        s.removeFilter(Search.SearchBy.PROFESSOR);
        //System.out.println(s.activeFilters.size());
        assertEquals(3, s.getResults().size());
        /*for (int i = 0; i < s.getResults().size(); i++) {
            System.out.println("Yo: " + s.getResults().get(i).getProfessor());
        }*/
        //System.out.println(s.toString());

        s.addFilter(Search.SearchBy.PROFESSOR, "Bibza");
        /*for (int i = 0; i < s.getResults().size(); i++) {
            System.out.println("Yo: " + s.getResults().get(i).getProfessor());
        }*/
        //System.out.println(s.activeFilters.getFirst().filter);
        //System.out.println(s);
        assertEquals(2, s.getResults().size());
        s.addFilter(Search.SearchBy.COURSE_CODE, "COMP");
        /*for (int i = 0; i < s.activeFilters.size(); i++) {
            System.out.println("Yo: " + s.activeFilters.get(i).filter);
        }*/
        //System.out.println(s);
        assertEquals(1, s.getResults().size());
        assertEquals(2, s.activeFilters.size());
        s.removeFilter(Search.SearchBy.PROFESSOR);
        assertEquals(1, s.activeFilters.size());
        assertEquals(1, s.getResults().size());
        assertEquals("Foundations of balloon fabrication", s.getResults().get(0).getName());
        assertEquals("Dr. Bibza", s.getResults().get(0).getProfessor());
        //System.out.println(s);
        s.removeFilter(Search.SearchBy.COURSE_CODE);
        assertEquals(0, s.activeFilters.size());
        assertEquals(3, s.getResults().size());
        //System.out.println(s);
        s.addFilter(Search.SearchBy.TIME, "2:00 PM");
        assertEquals(1, s.getResults().size());
        //System.out.println(s);
        s.removeFilter(Search.SearchBy.TIME);
        assertEquals(3, s.getResults().size());
        //System.out.println(s);
        s.addFilter(Search.SearchBy.TIME, "9:00 AM");
        assertEquals(1, s.getResults().size());
        assertEquals("Underwater basket weaving", s.getResults().get(0).getName());
        //System.out.println(s);
        s.removeFilter(Search.SearchBy.TIME);
        assertEquals(3, s.getResults().size());
        //System.out.println(s);
        s.addFilter(Search.SearchBy.DATE, "MWF");
        assertEquals(1, s.getResults().size());
        //System.out.println(s);


        s.fuzzySearch("bisual", cs);
        s.fuzzySearch("visual", cs);
        //System.out.println("Why do they exist, Matt?\n");
        s.fuzzySearch("bovel", cs);
        s.fuzzySearch("foundations", cs);
        //System.out.println("Why do they exist, Matt?\n");
        s.fuzzySearch("zalloon", cs);

        s.addFilter(Search.SearchBy.TIME_RANGE, "10:00 AM,1:00 PM");
        /*for (int i = 0; i < s.activeFilters.size(); i++) {
            System.out.println("Yo: " + s.activeFilters.get(i).filter);
        }*/
        s.removeAllFilters();
        assertEquals(3, s.getResults().size());

        s.addFilter(Search.SearchBy.TIME_RANGE, "10:00 AM,1:00 PM");
        assertEquals(1, s.getResults().size());
        //System.out.println(s);
        s.removeAllFilters();

        assertEquals(3, s.getResults().size());

        s.addFilter(Search.SearchBy.TIME_RANGE, "10:00 AM," + null);
        assertEquals(2, s.getResults().size());
        //System.out.println(s);
        s.removeAllFilters();


        s.addFilter(Search.SearchBy.TIME_RANGE, "12:00 PM,Any");
        assertEquals(2, s.getResults().size());

        s.removeAllFilters();

        s.addFilter(Search.SearchBy.TIME_RANGE, null+",4:00 PM");
        assertEquals(3, s.getResults().size());
    }
}
