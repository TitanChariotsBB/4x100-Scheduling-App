package org.example;

import java.util.ArrayList;
import java.util.Iterator;

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

        Iterator<Course> it = results.iterator();
        while (it.hasNext()) {
            Course c = it.next();
            if (!codeFilter.isEmpty()) {
                if (!c.getCode().contains(codeFilter)) {
                    it.remove();
                    System.out.println("Removing " + c.getCode());
                    continue;
                }
            }
            if (!nameFilter.isEmpty()) {
                if (!c.getName().contains(nameFilter)) {
                    it.remove();
                    continue;
                }
            }
            if (!professorFilter.isEmpty()) {
                if (!c.getProfessor().contains(professorFilter)) {
                    it.remove();
                }
            }
        }
        // TODO: date/time
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
