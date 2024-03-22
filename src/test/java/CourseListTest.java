import org.example.Course;
import org.example.CourseList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CourseListTest {
    @Test
    void addCourse() throws Exception {
        CourseList list2 = new CourseList();
        LocalDateTime time3 = LocalDateTime.of(2024,1,1,8,0);
        LocalDateTime time4 = LocalDateTime.of(2024,1,1,10,0);
        LocalDateTime[][] meetings3 = {{time3,time4}, null, {time3,time4}, null, {time3,time4}};
        Course course5 = new Course("Overlap class","870",meetings3,true,"","","",3,null);

        //should throw an error
        LocalDateTime time5 = LocalDateTime.of(2024,1,1,9,0);
        LocalDateTime[][] meetings4 = {null,null,{time5,time5},null,null};
        Course course4 = new Course("Epic Class","345",meetings4,true,"","","",3,null);
        list2.addCourse(course5);
        list2.addCourse(course4);



        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime[][] meetings1 = {{time1, time1}, null, {time1, time1}, null, {time1, time1}};
        Course course1 = new Course("Underwater basket weaving", "HUMA 201", meetings1,true, "A good class", "STEM 376", "Dr. Bibza", 3, null);

        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime[][] meetings2 = {null, {time2, time2}, null, {time2, time2}, null};
        Course course2 = new Course("Foundations of balloon fabrication", "HUMA 301", meetings2,true, "A better class", "HAL 116", "Dr. Bibza", 3, null);

        CourseList list1 = new CourseList(); //test credit hours counter
        list1.addCourse(course1);
        list1.addCourse(course2);
        assertEquals(6,list1.getTotalCredits());

        //a class from 8-10 on monday wednesday friday
        Course course3 = new Course("Interesting class number one","124",meetings3,true,"A really intersting class", "HAL", "Jed Hart",3,null);



    }

    @Test
    void removeCourse() {
    }

    @Test
    void testToString() {
    }
}