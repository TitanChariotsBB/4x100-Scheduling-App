import org.junit.jupiter.api.Test;

class loadCatalogTest {

    @Test
    void loadCatalog() {
        CourseList myCourses = FileHandler.loadCatalog();
        System.out.println(myCourses);
    }
}