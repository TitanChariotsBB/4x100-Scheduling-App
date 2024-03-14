import static org.junit.jupiter.api.Assertions.*;

class SemesterScheduleTest {
    public static void main(String[] args) {
        SemesterSchedule ss = new SemesterSchedule();
        Course blah = new Course("Software Engineering", "COMP 350",
                null, "Gradle class", "STEM", "Hutchins",
                3, null);

        ss.addCourse(blah);
        System.out.println(ss);
    }
}