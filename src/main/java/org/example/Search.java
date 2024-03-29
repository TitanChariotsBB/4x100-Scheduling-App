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

        for (Course c : results) {
            String codeFilter = activeFilters[SearchBy.COURSE_CODE.ordinal()];
            if (!codeFilter.equals("")) {
                if (c.getCode().contains(codeFilter)) results.remove(c);
            }
            String nameFilter = activeFilters[SearchBy.COURSE_NAME.ordinal()];
            if (!nameFilter.equals("")) {
                if (c.getName().contains(nameFilter)) results.remove(c);
            }
            String professorFilter = activeFilters[SearchBy.PROFESSOR.ordinal()];
            if (!professorFilter.equals("")) {
                if (c.getProfessor().contains(professorFilter)) results.remove(c);
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
}
