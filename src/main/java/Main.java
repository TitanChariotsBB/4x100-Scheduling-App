import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    private static CourseList catalog;
    private static final String iFilePath = "C:\\Users\\STRIEBELNJ21\\Documents\\Software Engineering\\UpdatedCourseData\\catalog.xlsx";

    public static void run() {
        // run
    }

    public static void loadCatalog() {
        try {
            File iFile = new File(iFilePath);
            FileInputStream iStream = new FileInputStream(iFile); //could throw fileNotFoundException

            Workbook catalogFile = new XSSFWorkbook(iStream); //could throw IOException
            Sheet catalogSheet = catalogFile.getSheetAt(0); //pulls the first worksheet from the given xlsx file

            //Is it bad practice to write this whole method in the try block??
                //There's no point in having the rest of this code execute if one of the first four statements
                //throws an exception, so I think it's fine.

            /*
            In Dr. Hutchins' excel file format
            column 1: semester code; fall=10, spring=30
            column 2: department
            column 3: course #
            column 4: section letter
            column 5: course name
            column 6: credit hours
            columns 9-13: booleans for each day
            column 14: start time
            column 15: end time
            column 16: professor last name
            column 17: professor first name
             */

            int numRows = catalogSheet.getLastRowNum(); // returns 0-indexed last row

            for(int x = 1; x <= numRows; x++){
                Row thisRow = catalogSheet.getRow(x);
                String name = thisRow.getCell(5).getStringCellValue();
                String code = thisRow.getCell(2).getStringCellValue() + thisRow.getCell(3).getStringCellValue();
                boolean isFall = thisRow.getCell(1).getNumericCellValue() == 10.0;
                LocalDateTime sTime = thisRow.getCell(14).getLocalDateTimeCellValue();
                LocalDateTime eTime = thisRow.getCell(14).getLocalDateTimeCellValue();;
                LocalDateTime timeRange[] = {sTime, eTime};
                boolean[] days = new boolean[5];
                for(int y = 9; y < 14; y++){
                    days[y] = (thisRow.getCell(y).getCellType() != CellType.BLANK);
                }
                ArrayList<LocalDateTime[]> meetTimes = new ArrayList<>();
                for(int y = 0; y < 5; y++){
                    if(days[y]){
                        meetTimes.add(timeRange);
                    }
                    else{
                        meetTimes.add(null);
                    }
                }
                String description = null;
                String location = null;
                String professor = thisRow.getCell(17).getStringCellValue() + thisRow.getCell(16).getStringCellValue();
                int credits = (int)thisRow.getCell(6).getNumericCellValue();
                ArrayList<String> prereqs = null;

                Course thisCourse = new Course(name, code, meetTimes, isFall, description, location, professor, credits, prereqs);
                catalog.addCourse(thisCourse);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
