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
    private String[] activeFilters;

    public Search(CourseList catalog) {
        activeFilters = new String[]{"", "", "", "", ""};
        currentQuery = "";
        unfilteredResults = catalog.getCourses();
        results = new ArrayList<>();
    }

    public void setCurrentQuery(String query) {
        currentQuery = query;
    }

    public void populateResults() {
        results.clear();
        for (Course c : unfilteredResults) {
            if (c.getCode().contains(currentQuery)) {
                results.add(c);
                continue;
            }
            if (c.getName().contains(currentQuery)) {
                results.add(c);
                continue;
            }
            if (c.getProfessor().contains(currentQuery)) {
                results.add(c);
            }
        }

        String codeFilter = activeFilters[SearchBy.COURSE_CODE.ordinal()];
        String nameFilter = activeFilters[SearchBy.COURSE_NAME.ordinal()];
        String professorFilter = activeFilters[SearchBy.PROFESSOR.ordinal()];

        for (int i = 0; i < results.size(); i++) {
            Course c = results.get(i);
            if (!codeFilter.isEmpty()) {
                if (!c.getCode().contains(codeFilter)) results.remove(c);
            }
            if (!nameFilter.isEmpty()) {
                if (!c.getName().contains(nameFilter)) results.remove(c);
            }
            if (!professorFilter.isEmpty()) {
                if (!c.getProfessor().contains(professorFilter)) results.remove(c);
            }
            // TODO: date/time
        }
    }

    public ArrayList<Course> getResults() {
        return results;
    }

    public void addFilter(SearchBy sb, String filter) {
        activeFilters[sb.ordinal()] = filter;
    }

    public void removeFilter(SearchBy sb) {
        activeFilters[sb.ordinal()] = "";
    }

    public void removeAllFilters() {
        activeFilters = new String[]{"", "", "", "", ""};
    }

    public String[] getActiveFilters() {
        return activeFilters;
    }
}
