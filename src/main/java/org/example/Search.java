package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.text.similarity.*;

public class Search {
    private String currentQuery;
    public enum SearchBy {
        ALL,
        COURSE_CODE,
        COURSE_NAME,
        PROFESSOR,
        TIME,
        DATE,
        TIME_RANGE
    }
    private ArrayList<Course> unfilteredResults;
    private ArrayList<Course> results;
    public ArrayList<Filter> activeFilters;

    public CourseList courseCatalog;

    public Search(CourseList courseCatalog) { // Constructor
        unfilteredResults = new ArrayList<>();
        results = new ArrayList<>();
        activeFilters = new ArrayList<>();
        this.courseCatalog = courseCatalog;
        unfilteredResults.addAll(courseCatalog.getCourses());
        setQuery("");
    }

    public ArrayList<Course> getResults() { // Returns ArrayList of courses matching search
        /*if (results.isEmpty()) {
            populateResults();
        }*/
        return results;
    }

    public void setQuery(String currentQuery) { // Sets search query to the given parameter
        this.currentQuery = fuzzySearch(currentQuery, courseCatalog);
        //this.currentQuery = currentQuery;
        results.clear();
        populateResults();
    }
    public String getCurrentQuery(){
        return currentQuery;
    }

    private void populateResults() {
        // Will use currentQuery and filters array to populate results list

        /*if (currentQuery == null) {
            results.addAll(unfilteredResults);
        }*/
        //if (activeFilters.isEmpty()) {
            /*for (Course unfilteredResult : unfilteredResults) {
                if ((unfilteredResult.getName().contains(currentQuery) ||
                        unfilteredResult.getCode().contains(currentQuery) ||
                        unfilteredResult.getProfessor().contains(currentQuery)) &&
                                !results.contains(unfilteredResult)) {
                    results.add(unfilteredResult);
                }
            }*/
            if (currentQuery.isEmpty()) {
                results.addAll(unfilteredResults);
                for (Filter filter: activeFilters) {
                    filterCourses(filter.sb, filter.filter);
                }
            }
            else {
                for (Course unfilteredResult : unfilteredResults) {
                    if ((unfilteredResult.getName().toLowerCase().contains(currentQuery.toLowerCase()) ||
                            unfilteredResult.getCode().toLowerCase().contains(currentQuery.toLowerCase()) ||
                            unfilteredResult.getProfessor().toLowerCase().contains(currentQuery.toLowerCase())) &&
                            !results.contains(unfilteredResult)) {
                        results.add(unfilteredResult);
                    }
                }
                for (Filter filter: activeFilters) {
                    filterCourses(filter.sb, filter.filter);
                }
            }
        //}
        /*else {
            results.addAll(unfilteredResults);
            for (Filter filter: activeFilters) {
                filterCourses(filter.sb, filter.filter);
            }
        }*/

    }


    public void addFilter(SearchBy sb, String filter) { // Adds filter
        activeFilters.add(new Filter(sb, filter));
        //setQuery(filter);
        filterCourses(sb, filter);
    }

    /*public void addFilter(SearchBy sb, LocalDateTime[][] ld) {
        activeFilters.add(new Filter(sb, ld));
    }*/

    private void filterCourses(SearchBy sb, String filter) { // Eliminates classes that don't match the filter
        if (sb.equals(SearchBy.ALL)) { // Returns results with no filter
            return;
        }
        else if (sb.equals(SearchBy.COURSE_CODE)) { // If filtering by course_code
            if (filter.equals("Any ")) {
                return;
            }
            else if (filter.contains("Any")) {
                //System.out.println(filter);
                String number = filter.split(" ")[1];
                //System.out.println(number);
                results.removeIf(result -> !result.getCode().contains(number));
                return;
            }
            results.removeIf(result -> !result.getCode().contains(filter));

        }
        else if (sb.equals(SearchBy.COURSE_NAME)) { // If filtering by course_name
            results.removeIf(result -> !result.getName().toLowerCase().contains(filter.toLowerCase()));

        }
        else if (sb.equals(SearchBy.PROFESSOR)) { // If filtering by professor name
            results.removeIf(result -> !result.getProfessor().toLowerCase().contains(filter.toLowerCase()));
        }
        else if (sb.equals(SearchBy.DATE)) { // If filtering by date
            if (filter.equals("Any")) { // Return all courses
                return;
            }
            else if (filter.equals("MWF")) { // Return only the courses that meet MWF
                results.removeIf(result ->
                        result.getMeetingTimes() == null ||
                        result.getMeetingTimes()[0] == null ||
                        result.getMeetingTimes()[1] != null ||
                        result.getMeetingTimes()[2] == null ||
                        result.getMeetingTimes()[3] != null ||
                        result.getMeetingTimes()[4] == null);
            }
            else if (filter.equals("TR")) { // Return only the courses that meet TR
                results.removeIf(result ->
                        result.getMeetingTimes() == null ||
                        result.getMeetingTimes()[0] != null ||
                        result.getMeetingTimes()[1] == null ||
                        result.getMeetingTimes()[2] != null ||
                        result.getMeetingTimes()[3] == null ||
                        result.getMeetingTimes()[4] != null);
            }
            else if (filter.equals("Other")) { // Return any classes that meet on days other than MWF or TR
                results.removeIf(result -> result.getMeetingTimes() == null ||
                        !result.getMeetingDateString().equals("Other"));
            }
        }
        else if (sb.equals(SearchBy.TIME)) { // If filtering by time
            if (filter.equals("Any")) {
                return;
            }
            results.removeIf(result -> result.getMeetingTimes() == null ||
                    !result.getMeetingTimeStringAlex().equals(filter));

            /*if (filter.charAt(filter.length() - 2) == 'A') { // If AM
                String temp = filter.substring(0, filter.length() - 6); // Store just the number
                int myTime = Integer.parseInt(temp); // Convert the number from String to int
                results.removeIf(result -> Integer.parseInt(result.getMeetingTimeString()) != myTime);

//                for (int i = 0; i < results.size(); i++) { // Runs through all classes
//                    for (int j = 0; j < 5; j++) { // Runs through each weekday
//                        if (results.get(i).getMeetingTimes()[j]!=null) { // Check if day is null
//                            int finalJ = j;
//                        }
//                    }
//                }
            }
            else if (filter.charAt(filter.length() - 2) == 'P') { // If PM
                String temp = filter.substring(0, filter.length() - 6); // Store just the number
                int myTime = Integer.parseInt(temp) + 12; // Convert the number from String to int + 12 for PM
                results.removeIf(result -> Integer.parseInt(result.getMeetingTimeString()) != myTime);
//                for (int i = 0; i < results.size(); i++) { // Runs through all classes
//                    for (int j = 0; j < 5; j++) { // Runs through each weekday
//                        if (results.get(i).getMeetingTimes()[j]!=null) { // Check if day is null
//                            int finalJ = j;
//                            results.removeIf(result -> result.getMeetingTimes()[finalJ][0].getHour() != myTime);
//                        }
//                    }
//                }
            }*/
        }
        else if (sb.equals(SearchBy.TIME_RANGE)) {
            if (filter.equals("Any,Any")) {
                return;
            }

            String[] startEndTime = filter.split(",");

                if (startEndTime[0].equals("null") || startEndTime[0].equals("Any")) {
                    String[] hourMinuteAMPM = startEndTime[1].split("[: ]");
                    int hour = Integer.parseInt(hourMinuteAMPM[0]);
                    int minute = Integer.parseInt(hourMinuteAMPM[1]);
                    String AMPM = hourMinuteAMPM[2];

                    if (AMPM.equals("PM") && hour != 12) {
                        hour+=12;
                    }

                    hour*=100;
                    hour+=minute;

                    int militaryTime = hour;

                    results.removeIf(result -> result.getMeetingTimes() == null ||
                            result.getMeetingTimeRangeStringAlex() > militaryTime);
                }
                else if (startEndTime[1].equals("null") || startEndTime[1].equals("Any")) {
                    String[] hourMinuteAMPM = startEndTime[0].split("[: ]");
                    int hour = Integer.parseInt(hourMinuteAMPM[0]);
                    int minute = Integer.parseInt(hourMinuteAMPM[1]);
                    String AMPM = hourMinuteAMPM[2];

                    if (AMPM.equals("PM") && hour != 12) {
                        hour+=12;
                    }

                    hour*=100;
                    hour+=minute;

                    int militaryTime = hour;

                    results.removeIf(result -> result.getMeetingTimes() == null ||
                            result.getMeetingTimeRangeStringAlex() < militaryTime);
                }
                else {
                    String[] startHourMinuteAMPM = startEndTime[0].split("[: ]");
                    String[] endHourMinuteAMPM = startEndTime[1].split("[: ]");
                    int startHour = Integer.parseInt(startHourMinuteAMPM[0]);
                    int endHour = Integer.parseInt(endHourMinuteAMPM[0]);
                    int startMinute = Integer.parseInt(startHourMinuteAMPM[1]);
                    int endMinute = Integer.parseInt(endHourMinuteAMPM[1]);
                    String startAMPM = startHourMinuteAMPM[2];
                    String endAMPM = endHourMinuteAMPM[2];

                    if (startAMPM.equals("PM") && startHour != 12) {
                        startHour+=12;
                    }

                    if (endAMPM.equals("PM") && endHour != 12) {
                        endHour+=12;
                    }

                    startHour*=100;
                    endHour*=100;
                    startHour+=startMinute;
                    endHour+=startMinute;

                    int startMilitaryTime = startHour;
                    int endMilitaryTime = endHour;

                    results.removeIf(result -> result.getMeetingTimes() == null ||
                            result.getMeetingTimeRangeStringAlex() < startMilitaryTime ||
                            result.getMeetingTimeRangeStringAlex() > endMilitaryTime);
                }




            /*System.out.println("Filter time: " + militaryTime);

            for (Course result : results) {
                System.out.println("Course list time: " + result.getMeetingTimeRangeStringAlex());
            }*/
        }
    }

    /*private void filterCourses(SearchBy sb, LocalDateTime ld) {
        if (sb.equals(SearchBy.DATE)) {

        }
        else if (sb.equals(SearchBy.TIME)) {

        }
    }*/

    public void removeFilter(SearchBy sb) { // Removes filter
        /*if (sb.toString().equals("ALL")) { // Returns results with no filter
            populateResults();
        }*/
        /*if (sb == SearchBy.COURSE_CODE) { // If removing course_code filter
            int target = -1;
            for (int i = 0; i < activeFilters.size(); i++) {
                if (activeFilters.get(i).sb == SearchBy.COURSE_CODE) {
                    target = i;
                }
            }

            for (Course unfilteredResult : unfilteredResults) {
                if (!unfilteredResult.getCode().contains(activeFilters.get(target).filter)) {
                    results.add(unfilteredResult);
                }
            }
        }
        else if (sb == SearchBy.COURSE_NAME) { // If removing course_name filter
            int target = -1;
            for (int i = 0; i < activeFilters.size(); i++) {
                if (activeFilters.get(i).sb == SearchBy.COURSE_NAME) {
                    target = i;
                }
            }

            for (Course unfilteredResult : unfilteredResults) {
                if (!unfilteredResult.getName().contains(activeFilters.get(target).filter)) {
                    results.add(unfilteredResult);
                }
            }
        }
        else if (sb == SearchBy.PROFESSOR) { // If removing professor filter
            int target = -1;
            for (int i = 0; i < activeFilters.size(); i++) {
                if (activeFilters.get(i).sb == SearchBy.PROFESSOR) {
                    target = i;
                }
            }

            for (Course unfilteredResult : unfilteredResults) {
                if (!unfilteredResult.getProfessor().contains(activeFilters.get(target).filter)) {
                    results.add(unfilteredResult);
                }
            }
        }*/
        activeFilters.removeIf(result -> result.sb == sb); // Removes filter from activeFilters list
        results.clear(); // Clears classes
        populateResults(); // Adds back the classes that match the new filters
    }

    public void removeAllFilters() {
        activeFilters.clear();
        results.clear();
        populateResults();
    } // Clears all filters

    public String toString() { // For testing purposes
        StringBuilder sb = new StringBuilder();
        for (Course result : results) {
            sb.append(result.getName() + " ");
        }
        return sb.toString();
    }

    // Performs fuzzy search on specified text
    public String fuzzySearch(String fuzzyQuery, CourseList courseCatalog) {
        if (fuzzyQuery.isEmpty()) {
            return "";
        }

        ArrayList<String> words = new ArrayList<>(List.of(courseCatalog.toString().toLowerCase().split(" ")));
        LevenshteinDistance l = new LevenshteinDistance();
        ArrayList<Integer> distances = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (String word : words) {
            //System.out.println("Loop check");
            //System.out.println("Current word: " + word);

            distances.add(l.apply(word, fuzzyQuery));
            names.add(word);
        }
        Integer maxDist = distances.getFirst();
        String closeMatch = "";
        for (int i = 0; i < distances.size(); i++) {
            //System.out.println("Max distance: " + maxDist);
            //System.out.println("Current distance: " + distances.get(i));

            if (distances.get(i) < maxDist) {
                maxDist = distances.get(i);
                closeMatch = names.get(i);
                //System.out.println("Current match: " + names.get(i));
            }
        }
        //System.out.println("Did you mean: " + closeMatch); // Helper print statment
        return closeMatch;
    }
}
