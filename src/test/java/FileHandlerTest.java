import org.example.CourseList;
import org.example.FileHandler;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
    @Test
    void testLoad(){
        CourseList catalog = FileHandler.loadCatalog();
        FileHandler.saveList(catalog, "test-save");
        CourseList loaded = FileHandler.loadList("test-save");
    }

    @Test
    void testSave(){
        CourseList catalog = FileHandler.loadCatalog();
        FileHandler.saveList(catalog, "test-save");
    }
    @Test
    void loadCatalog() {
        CourseList myCourses = FileHandler.loadCatalog();
        System.out.println(myCourses);
    }
}