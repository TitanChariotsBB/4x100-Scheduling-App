package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Search {
    private String currentQuery;
    private LocalDateTime[][] meetings;
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
        setQueryString(null);
    }

    public ArrayList<Course> getResults() {
        if (results.isEmpty()) {
            populateResults();
        }
        return results;
    }

    public void setQueryString(String currentQuery) {
        this.currentQuery = currentQuery;
        if (results.isEmpty()) {
            populateResults();
        }
    }

    public void setQueryDateTime(LocalDateTime[][] meetings) {
        this.meetings = meetings;
    }

    private void populateResults() {
        // Will use currentQuery and filters array to populate results list

        /*if (currentQuery == null) {
            results.addAll(unfilteredResults);
        }*/
        if (activeFilters.isEmpty()) {
            /*for (Course unfilteredResult : unfilteredResults) {
                if ((unfilteredResult.getName().contains(currentQuery) ||
                        unfilteredResult.getCode().contains(currentQuery) ||
                        unfilteredResult.getProfessor().contains(currentQuery)) &&
                                !results.contains(unfilteredResult)) {
                    results.add(unfilteredResult);
                }
            }*/
            results.addAll(unfilteredResults);
        }
        else {
            results.addAll(unfilteredResults);
            for (Filter filter: activeFilters) {
                if (filter.sb != SearchBy.TIME) {
                    filterCourses(filter.sb, filter.filter);
                }
                else {
                    filterCourses(filter.sb, filter.meetings);
                }
            }
        }

    }


    public void addFilter(SearchBy sb, String filter) {
        activeFilters.add(new Filter(sb, filter));
        setQueryString(filter);
        filterCourses(sb, filter);
    }

    public void addFilter(SearchBy sb, LocalDateTime[][] meetings) {
        activeFilters.add(new Filter(sb, meetings));
        setQueryDateTime(meetings);
        filterCourses(sb, meetings);
    }

    private void filterCourses(SearchBy sb, String filter) {
        if (sb.equals(SearchBy.ALL)) { // Returns results with no filter
            return;
        }
        else if (sb.equals(SearchBy.COURSE_CODE)) { // If filtering by course_code
            results.removeIf(result -> !result.getCode().contains(filter));

        }
        else if (sb.equals(SearchBy.COURSE_NAME)) { // If filtering by course_name
            results.removeIf(result -> !result.getName().contains(filter));

        }
        else if (sb.equals(SearchBy.PROFESSOR)) { // If filtering by professor name
            results.removeIf(result -> !result.getProfessor().contains(filter));
        }
    }

    private void filterCourses(SearchBy sb, LocalDateTime[][] meetings) {
        results.removeIf(result -> !(result.getMeetingTimes() == meetings));
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Course result : results) {
            sb.append(result.getName() + " ");
        }
        return sb.toString();
    }
}
