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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {
    private static String createPath(String nameAndExt){
        Path currentPath = FileSystems.getDefault().getPath("");
        String currentName = currentPath.toAbsolutePath().toString();
        String filePath = currentName + "\\src\\CourseLists\\";
        return filePath + nameAndExt;
    }

    /**
     * @return true if the file was saved with no problems,
     *      false if the named already exists and it has not been told to overwrite
     */
    public static boolean saveList(CourseList courses, String fileName, boolean overWrite){
        String filePath = createPath(fileName + ".json");
        File outFile = new File(filePath);

        if(outFile.exists() && !overWrite){
            return false;
        }

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
        return true;
    }

    public static CourseList loadList(String fileName) throws FileNotFoundException {
        String filePath = createPath(fileName + ".json");
        FileInputStream inStream = null;
        inStream = new FileInputStream(filePath);

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
                Cell preReqCell = thisRow.getCell(19);
                if(preReqCell != null){
                    prereqs = parsePrereqs(preReqCell.getStringCellValue());
                }

                Course thisCourse = new Course(name, code, meetTimes, isFall, description, location, professor, credits, prereqs);
                catalog.addCourse(thisCourse);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return catalog;
    }

    /*
     *  Dr. Hutchins told me to focus on a simple proof of concept, then move on to something else,
     *  so this method only works for a single prereq class, and it must be a course code that takes up the first eight chars of the string
     */
    private static ArrayList<String> parsePrereqs(String reqsFromFile){
        try {
            ArrayList<String> result = new ArrayList<>();

            boolean validCode = true;
            for(int x = 0; x < 8 && x < reqsFromFile.length() && validCode; x++){
                char thisChar = reqsFromFile.charAt(x);
                if(x < 4 && !((thisChar >= 'a' && thisChar <= 'z') || (thisChar >= 'A' && thisChar <= 'Z'))){
                    validCode = false; //if any of the first four characters is not a letter, it's no good
                }
                if(x == 4 && reqsFromFile.charAt(x) != ' '){
                    validCode = false;
                }
                if(x > 4 && !(thisChar >= '0' && thisChar <= '9')){
                    validCode = false;
                }
            }
            if(validCode) {
                result.add(reqsFromFile.substring(0, 4) + reqsFromFile.substring(5, 8));
                return result;
            }
            else{return null;}
            /*
            ArrayList<String> pieces = new ArrayList<>();
            Scanner scan = new Scanner(reqsFromFile);
            while(scan.hasNext()){
                pieces.add(scan.next());
            }

            Map<String, String> abbrs = new HashMap<String, String>();
            abbrs.put("AS","ASTR");
            abbrs.put("PH","PHYS");
            abbrs.put("CHM","CHEM");
            abbrs.put("CM","COMM");
            abbrs.put("CP","COMP");
            abbrs.put("MT","Math");
            abbrs.put("EN","ENGR");
            abbrs.put("ENG","ENGR");
            abbrs.put("CH","CHEM");
            abbrs.put("ME","MECE");
            abbrs.put("FN","FNCE");
            abbrs.put("IN","INBS");
            abbrs.put("M","MATH");
            abbrs.put("MA","MATH");

            //replace abbreviations with actual department codes
            for(int x = 0; x < pieces.size(); x++){
                String thisPiece = pieces.get(x);
                if(abbrs.containsKey(thisPiece)){
                    pieces.set(x,abbrs.get(thisPiece));//replace the abbreviation with the full code
                    continue;
                }
            }

            //add course codes to result as appropriate
            for(int x = 0; x < pieces.size(); x++) {
                String thisPiece = pieces.get(x);
                if(thisPiece.length() >= 3){
                    boolean isNumCode = true;
                    for(int i = 0; i < 3; i++){
                        if(!((int)thisPiece.charAt(i) >= 48 && (int)thisPiece.charAt(i) <= 57)){//one of the first 3 chars is non-numeric
                            isNumCode = false;
                        }
                    }
                    if(isNumCode && x > 0) {
                        result.add(pieces.get(x - 1) + thisPiece.substring(0, 3));
                    }
                }


                for(int y = 0; y < pieces.get(x).length(); y++){

                }
            }
            */
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     *unusual department codes:
     * ART is only 3 letters
     * AS is short for ASTR
     * PH is short for PHYS
     * CHM is short for CHEM
     * CM is short for COMM
     * CP is short for COMP
     * MT is short for MATH
     * ENG is short for ENGR
     * EN is short for ENGR
     * CH is short for CHEM
     * ME is short for MECE
     * FN is short for FNCE
     * IN is short for INBS
     * MA is short for MATH
     * M is short for MATH
     */

}