import org.apache.logging.log4j.Logger;
import org.example.LogHelper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoggerTest {
    @Test
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

        log.error("Hello this is an error message. It should be in the file and console");
        log.debug("Hello, this should show up in the file, but not the console");
    }
}
