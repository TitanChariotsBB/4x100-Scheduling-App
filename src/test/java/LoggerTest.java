import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoggerTest {
   /* @Test
    public void configFileTest(){
        Logger log = LogHelper.initLogger();

        String logConfigPath = System.getProperty("log4j.configurationFile");
        System.out.println("Logger config filepath: " + logConfigPath);
        File logConfigFile = new File(logConfigPath);
        Scanner scan = null;
        try {
            scan = new Scanner(logConfigFile);
            System.out.println("Your config filePath is good!");
        }catch(FileNotFoundException fnfe){
            System.out.println("Your config filePath is bad!");
        }

        System.out.println(scan.next());
        System.out.println(scan.next());

        System.out.println("root(file) level: " + LogManager.getRootLogger().getLevel());
        System.out.println("error(console) level: " + log.getLevel());

        log.error("Hello this is an error message. It should be in the file and console");
        log.debug("Hello, this should show up in the file, but not the console");
        log.info("Hello this is an info message");
    }*/

    @Test
    public void LogHelperErrorTest(){
        LogHelper.logError("Hello this is an error");
    }

    @Test
    public void LogHelperActionTest(){
        CourseList cl = new CourseList();
        Course c = new Course("Computer Programming 7","Comp505",null,true,"A cool computer class for cool computer people","STEM056","Dr. Hutchins",6,null);

        UserAction ua = new UserAction(cl,c,UserAction.actionType.ADD_COURSE);
        LogHelper.logUserAction(ua);
    }
}
