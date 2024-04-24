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
    public static String getDefaultPath(String nameAndExt){
        Path currentPath = FileSystems.getDefault().getPath("");
        String currentName = currentPath.toAbsolutePath().toString();
        String filePath = currentName + "\\src\\CourseLists\\";
        return filePath + nameAndExt;
    }

    /**
     * @param courses is the list of courses to be saved
     * @param filePath the absolute path to the file where the list is to be saved
     * @param overWrite if true, an existing file with that specified path can be deleted and replaced
     *                  if false, finding an existing file with that name results in returning false
     * @return true if the file was successfully saved
     */
    public static boolean saveList(CourseList courses, String filePath, boolean overWrite){
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

    /**
     * @param filePath the absolute path to the file to be loaded
     * @return the list of courses found in the specified json file
     * @throws FileNotFoundException
     */
    public static CourseList loadList(String filePath) throws FileNotFoundException {
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
                workingStr += ','; //add a comma every time except the last time to preserve indexing
            }

            for(; currentIndex < workingStr.length(); currentIndex++){
                if(workingStr.charAt(currentIndex) == '{'){//the first char of an object should always be '{'
                    openBrackets++;
                }
                else if(workingStr.charAt(currentIndex) == '}'){
                    openBrackets--;
                }


                if(openBrackets == 0){//currentIndex is the end of an object
                    String objectString = workingStr.substring(0,currentIndex+1);
                    loadedCourse = (Course) JsonReader.jsonToJava(objectString);
                    loadedList.addCourse(loadedCourse);

                    workingStr = workingStr.substring(currentIndex+1);
                    currentIndex = -1; //the for statement will set it back to 0
                }
            }

        }

        return loadedList;
    }

    public static CourseList loadCatalog(){
        String catalogFilePath = getDefaultPath("catalog.xlsx");
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
                Cell sectionCell = thisRow.getCell(4);
                if(deptCell != null && numCell != null) {
                    code = deptCell.getStringCellValue() + numCell.getStringCellValue()
                            + sectionCell.getStringCellValue();
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
                    String[] correctDays = {"M", "T", "W", "R", "F"};
                    String[] dayStrs = new String[5];
                    for (int y = 9; y < 14; y++) {
                        Cell dayCell = thisRow.getCell(y);
                        String dayVal;
                        if(dayCell != null) {
                            dayVal = dayCell.getStringCellValue();
                        }
                        else{
                            dayVal = null;
                        }
                        days[y-9] = correctDays[y-9].equals(dayVal);
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

    public static boolean saveFutureList(ArrayList<CourseList> futureLists, String filename, boolean overWrite){
        //algorithm: make a new directory based on filename, and save each courselist inside that directory with the existing

        String filePath = getDefaultPath(filename);
        File directory = new File(filePath);

        if(directory.exists()) {
            if(!overWrite){
                return false;
            }else {
                File[] dirContents = directory.listFiles();
                for(File f : dirContents){
                    f.delete();
                }
                directory.delete();//can only delete an empty directory
            }
        }
        if(!directory.mkdir()){//the directory should exist now
            return false;
        }

        for(int x = 0; x < futureLists.size(); x++){
            String thisPath = filePath + "\\" + filename + "-" + x + ".json";
            if(!saveList(futureLists.get(x),thisPath,overWrite)){//this is fine. It will work once my last pull request is approved
                return false;
            }
        }
        return true;
    }

    public static ArrayList<CourseList> loadFutureList(String filePath) throws FileNotFoundException{
        ArrayList<CourseList> result = new ArrayList<>();

        File directory = new File(filePath);
        if(!directory.exists()){
            throw new FileNotFoundException("We could not find the file: " + filePath);
        }

        File[] courseLists = directory.listFiles();
        for(int x = 0; x < courseLists.length; x++){
            String thisPath = courseLists[x].getPath();
            CourseList thisList = loadList(thisPath);
            result.add(thisList);
        }

        return result;
    }

    /*
     *  Dr. Hutchins told me to focus on a simple proof of concept, then move on to something else,
     *  so this method only works for a single prereq class, and it must be a course code that takes up the first eight chars of the string
     */
        private static ArrayList<String> parsePrereqs (String reqsFromFile){
            if (reqsFromFile.length() < 8){
                return null;
            }

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
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}