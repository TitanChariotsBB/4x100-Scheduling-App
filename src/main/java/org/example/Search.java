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
    public ArrayList<Filter> activeFilters;

    public Search(CourseList courseCatalog) {
        unfilteredResults = new ArrayList<>();
        results = new ArrayList<>();
        activeFilters = new ArrayList<>();
        unfilteredResults.addAll(courseCatalog.getCourses());
        setQuery("");
    }

    public ArrayList<Course> getResults() {
        /*if (results.isEmpty()) {
            populateResults();
        }*/
        return results;
    }

    public void setQuery(String currentQuery) {
        this.currentQuery = currentQuery;
        results.clear();
        populateResults();
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


    public void addFilter(SearchBy sb, String filter) {
        activeFilters.add(new Filter(sb, filter));
        //setQuery(filter);
        filterCourses(sb, filter);
    }

    private void filterCourses(SearchBy sb, String filter) {
        if (sb.equals(SearchBy.ALL)) { // Returns results with no filter
            return;
        }
        else if (sb.equals(SearchBy.COURSE_CODE)) { // If filtering by course_code
            results.removeIf(result -> !result.getCode().contains(filter));

        }
        else if (sb.equals(SearchBy.COURSE_NAME)) { // If filtering by course_name
            results.removeIf(result -> !result.getName().toLowerCase().contains(filter.toLowerCase()));

        }
        else if (sb.equals(SearchBy.PROFESSOR)) { // If filtering by professor name
            results.removeIf(result -> !result.getProfessor().toLowerCase().contains(filter.toLowerCase()));
        }
    }

    public void removeFilter(SearchBy sb) {
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
        activeFilters.removeIf(result -> result.sb == sb);
        results.clear();
        populateResults();
    }

    public void removeAllFilters() {
        activeFilters.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course result : results) {
            sb.append(result.getName() + " ");
        }
        return sb.toString();
    }
}
