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
    private ArrayList<String> activeFilters;

    public Search(CourseList courseCatalog) {
        unfilteredResults = new ArrayList<>();
        results = new ArrayList<>();

        unfilteredResults.addAll(courseCatalog.getCourses());
    }

    public ArrayList<Course> getResults() {
        populateResults();
        return results;
    }

    public void setQuery(String currentQuery) {
        this.currentQuery = currentQuery;
        populateResults();
    }

    public void populateResults() {
        // Will use currentQuery and filters array to populate results list
        for (int i = 0; i < unfilteredResults.size(); i++) {
            if (unfilteredResults.get(i).getName().contains(currentQuery) ||
            unfilteredResults.get(i).getCode().contains(currentQuery) ||
            unfilteredResults.get(i).getProfessor().contains(currentQuery)) {
                results.add(unfilteredResults.get(i));
            }
        }
    }


    public void addFilter(SearchBy sb, String filter) {
        activeFilters.add("Category: " + sb.toString() + " filter: " + filter);

        if (sb.toString().equals("ALL")) { // Returns results with no filter
            return;
        }
        else if (sb.toString().equals("COURSE_CODE")) { // If filtering by course_code
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).getCode().equals(filter)) {
                    results.remove(results.get(i)); // Remove courses that don't match
                }
            }
        }
        else if (sb.toString().equals("COURSE_NAME")) { // If filtering by course_name
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).getName().equals(filter)) {
                    results.remove(results.get(i));
                }
            }
        }
        else if (sb.toString().equals("PROFESSOR")) {
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (!results.get(i).getProfessor().equals(filter)) {
                    results.add(results.get(i));
                }
            }
        }
    }

    public void removeFilter(SearchBy sb) {
        activeFilters.remove(activeFilters.contains(sb));
    }
}
