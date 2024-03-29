package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void getResults() {
        CourseList catalog;
        Search search;
        CourseList fallSemester;
        CourseList springSemester;
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);
        fallSemester = new CourseList();
        fallSemester.addCourse(course1);
        fallSemester.addCourse(course2);
        springSemester = new CourseList();
        search = new Search(fallSemester);

        search.setCurrentQuery("Underwater");
        search.populateResults();
        ArrayList<Course> expectedResults = new ArrayList<>();
        expectedResults.add(course1);
        assertEquals(expectedResults, search.getResults());
    }
}