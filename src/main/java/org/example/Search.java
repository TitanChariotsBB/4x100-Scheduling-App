package org.example;

import java.util.ArrayList;

public class Search {
    private String currentQuery;
    public enum SearchBy {
        ALL,
        COURSE_CODE,
        COURSE_NAME,
        PROFESSOR,
        TIME
    }
    private ArrayList<Course> unfilteredResults;
    private ArrayList<Course> results;
    private ArrayList<String> activeFilters;

    public Search(CourseList courseCatalog) {
        unfilteredResults = new ArrayList<>();
        results = new ArrayList<>();
        activeFilters = new ArrayList<>();
        unfilteredResults.addAll(courseCatalog.getCourses());
    }

    public ArrayList<Course> getResults() {
        if (results.isEmpty()) {
            populateResults();
        }
        return results;
    }

    public void setQuery(String currentQuery) {
        this.currentQuery = currentQuery;
        if (results.isEmpty()) {
            populateResults();
        }
    }

    private void populateResults() {
        // Will use currentQuery and filters array to populate results list

        if (currentQuery == null) {
            results.addAll(unfilteredResults);
        }
        else {
            for (Course unfilteredResult : unfilteredResults) {
                if ((unfilteredResult.getName().contains(currentQuery) ||
                        unfilteredResult.getCode().contains(currentQuery) ||
                        unfilteredResult.getProfessor().contains(currentQuery)) &&
                                !results.contains(unfilteredResult)) {
                    results.add(unfilteredResult);
                }
            }
        }

    }


    public void addFilter(SearchBy sb, String filter) {
        activeFilters.add("Category: " + sb.toString() + " filter: " + filter);

        if (sb.toString().equals("ALL")) { // Returns results with no filter
            return;
        }
        else if (sb.toString().equals("COURSE_CODE")) { // If filtering by course_code
            results.removeIf(result -> !result.getCode().equals(filter));

        }
        else if (sb.toString().equals("COURSE_NAME")) { // If filtering by course_name
            results.removeIf(result -> !result.getName().equals(filter));

        }
        else if (sb.toString().equals("PROFESSOR")) { // If filtering by professor name
            results.removeIf(result -> !result.getProfessor().equals(filter));
        }
    }

    public void removeFilter(SearchBy sb) {
        activeFilters.remove(activeFilters.contains(sb.toString()));
        /*if (sb.toString().equals("ALL")) { // Returns results with no filter
            populateResults();
        }*/
        if (sb.toString().equals("COURSE_CODE")) { // If filtering by course_code
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getCode().contains(currentQuery)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
        else if (sb.toString().equals("COURSE_NAME")) { // If filtering by course_name
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getName().contains(currentQuery)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
        else if (sb.toString().equals("PROFESSOR")) {
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getProfessor().contains(currentQuery)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append(results.get(i).getName());
        }
        return sb.toString();
    }
}
