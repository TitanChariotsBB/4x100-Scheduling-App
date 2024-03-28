import org.example.CourseList;
import org.example.FileHandler;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
    @Test
    void testLoad(){
        CourseList catalog = FileHandler.loadCatalog();
        FileHandler.saveList(catalog, "test-save", true);
        try{CourseList loaded = FileHandler.loadList("test-save");}
        catch(Exception e){e.printStackTrace();}
    }

    @Test
    void testSave(){
        CourseList catalog = FileHandler.loadCatalog();
        FileHandler.saveList(catalog, "test-save",true);
    }
    @Test
    void loadCatalog() {
        CourseList myCourses = FileHandler.loadCatalog();
        System.out.println(myCourses);
    }
}