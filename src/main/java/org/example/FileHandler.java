package org.example;

import com.cedarsoftware.io.JsonReader;
import com.cedarsoftware.io.JsonWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private static String createPath(String nameAndExt){
        Path currentPath = FileSystems.getDefault().getPath("");
        String currentName = currentPath.toAbsolutePath().toString();
        String filePath = currentName + "\\src\\CourseLists\\";
        return filePath + nameAndExt;
    }

    public static void saveList(CourseList courses, String fileName){
        String filePath = createPath(fileName + ".json");
        File outFile = new File(filePath);
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(outFile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        JsonWriter writer = new JsonWriter(outStream);

        for(Course course : courses.getCourses()){
            writer.write(course);
        }
    }

    public static CourseList loadList(String fileName){
        String filePath = createPath(fileName + ".json");
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(filePath);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Scanner scan = new Scanner(inStream);
        scan.useDelimiter(",");
        String workingStr = "";
        /* workingStr will represent some fraction of the input file, starting from a '{'
        *  When an object has been read from the json file, the beginning of workingStr advances
        *   to the start of the next object
        * */
        int openBrackets = 0;
        int currentIndex = 0;

        CourseList loadedList = new CourseList();

        while(scan.hasNext()){
            Course loadedCourse = null;

            workingStr += scan.next();
            if(scan.hasNext()){
                workingStr += ','; //add a comma every time except the last time
            }

            for(; currentIndex < workingStr.length(); currentIndex++){
                if(workingStr.charAt(currentIndex) == '{'){//the first char of an object should always be '{'
                    openBrackets++;
                }
                else if(workingStr.charAt(currentIndex) == '}'){
                    openBrackets--;
                }


                if(openBrackets == 0){//we've reached the end of an object
                    String objectString = workingStr.substring(0,currentIndex+1);
                    loadedCourse = (Course) JsonReader.jsonToJava(objectString);
                    workingStr = workingStr.substring(currentIndex+1);
                    currentIndex = -1; //the for statement will set it back to 0
                }
            }
            loadedList.addCourse(loadedCourse);
        }

        return loadedList;
    }

    public static CourseList loadCatalog(){
        String catalogFilePath = createPath("catalog.xlsx");
        CourseList catalog = new CourseList();

        try {
            File iFile = new File(catalogFilePath);
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

                String name = null;
                Cell nameCell = thisRow.getCell(5);
                if(nameCell != null){
                    name = nameCell.getStringCellValue();
                }

                String code = null;
                Cell deptCell = thisRow.getCell(2);
                Cell numCell = thisRow.getCell(3);
                if(deptCell != null && numCell != null) {
                    code = deptCell.getStringCellValue() + numCell.getStringCellValue();
                }

                Boolean isFall = null;
                Cell fallCell = thisRow.getCell(1);
                if(fallCell != null){
                    isFall = (fallCell.getStringCellValue().equals("10"));
                }

                LocalDateTime[][] meetTimes = null;
                Cell sTimeCell = thisRow.getCell(14);
                Cell eTimeCell = thisRow.getCell(15);
                if(sTimeCell != null && eTimeCell != null) {
                    LocalDateTime sTime = sTimeCell.getLocalDateTimeCellValue();
                    LocalDateTime eTime = eTimeCell.getLocalDateTimeCellValue();
                    LocalDateTime timeRange[] = {sTime, eTime};
                    boolean[] days = new boolean[5];
                    for (int y = 9; y < 14; y++) {
                        Cell dayCell = thisRow.getCell(y);
                        if(dayCell != null) {
                            days[y - 9] = ((thisRow.getCell(y).getCellType() != CellType.BLANK));
                        }else{
                            days[y-9] = false;
                        }
                    }
                    meetTimes = new LocalDateTime[5][];
                    for (int y = 0; y < 5; y++) {
                        if (days[y]) {
                            meetTimes[y] = timeRange;
                        } else {
                            meetTimes[y] = null;
                        }
                    }
                }

                String description = null;

                String location = null;

                String professor = null;
                Cell professorFirstCell = thisRow.getCell(17);
                Cell professorLastCell = thisRow.getCell(16);
                if(professorFirstCell != null && professorLastCell != null) {
                    professor = professorFirstCell.getStringCellValue() + " " + professorLastCell.getStringCellValue();
                }

                Integer credits = null;
                Cell creditsCell = thisRow.getCell(6);
                if(creditsCell != null) {
                    credits = (int) creditsCell.getNumericCellValue();
                }

                ArrayList<String> prereqs = null;

                Course thisCourse = new Course(name, code, meetTimes, isFall, description, location, professor, credits, prereqs);
                catalog.addCourse(thisCourse);

                //TODO Prerequisites!!!
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return catalog;
    }
}