import org.example.Course;
import org.example.CourseList;
import org.example.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class FileHandlerTest {

    /*@Test
    void testLoad(){
        CourseList catalog = FileHandler.loadCatalog();
        String filePath = FileHandler.getDefaultPath("test-save.json");
        FileHandler.saveList(catalog, filePath, true);
        try{
            CourseList loaded = FileHandler.loadList(filePath);
            System.out.println(loaded);
        }
        catch(Exception e){e.printStackTrace();}
    }
@Test
    void testLoadExists() throws Exception{
        try {
            FileHandler.loadList("CrazyFileThatTotallyExists");
            assert(false); //an exception should be thrown. If we get here, we failed
        }catch(FileNotFoundException fnfe){
            assert(true);
        }
    }

    @Test
    void testSave(){
        CourseList catalog = FileHandler.loadCatalog();
        String filePath = FileHandler.getDefaultPath("test-save.json");
        FileHandler.saveList(catalog, filePath, true);
    }

    @Test
    void testSaveOverwrite(){
        CourseList catalog = FileHandler.loadCatalog();
        boolean couldSave = FileHandler.saveList(catalog, "test-save", false);
        assert(!couldSave);
        couldSave = FileHandler.saveList(catalog, "test-save", true);
        assert(couldSave);
    }

    @Test
    void loadCatalog() {
        CourseList myCourses = FileHandler.loadCatalog();
        System.out.println(myCourses);
    }

    @Test
    void testWishlistSave(){
        ArrayList<String> myPrereqs = new ArrayList<String>();
        myPrereqs.add("COMP101");
        Course c1 = new Course("Computer Programming 7","Comp505",null,true,"A cool computer class for cool computer people","STEM056","Dr. Hutchins",6,myPrereqs);
        Course c2 = new Course("Schmomputer Schmogramming","SCMP101",null,true,"Description Schmescription",null,"Schmocter Schmutchins", 2,myPrereqs);

        CourseList cl1 = new CourseList();
        cl1.addCourse(c1);
        cl1.addCourse(c2);
        CourseList cl2 = FileHandler.loadCatalog();

        ArrayList<CourseList> cll = new ArrayList<>();
        cll.add(cl1);
        cll.add(cl2);

        boolean saved = FileHandler.saveFutureList(cll, "test-wishlist",true);
        assert(saved);
    }

    @Test
    void testWishlistLoad(){
        String filePath = FileHandler.getDefaultPath("test-wishlist");
        try {
            ArrayList<CourseList> wishList = FileHandler.loadFutureList(filePath);
            System.out.println(wishList);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/
}