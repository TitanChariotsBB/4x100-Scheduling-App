package org.example;

import java.util.ArrayList;

public class Search {
    private String currentQuery;
    enum SearchBy {
        ALL,
        COURSE_CODE,
        COURSE_NAME,
        PROFESSOR,
        TIME
    }
    private ArrayList<Course> unfilteredResults;
    private ArrayList<Course> results;
    private String[] activeFilters;

    public void setCurrentQuery(String query) {
        currentQuery = query;
    }

    public void populateResults() {

    }

    public ArrayList<Course> getResults() {
        return null;
    }

    public void addFilter(SearchBy sb, String filter) {}

    public void removeFilter(SearchBy sb) {}
}
