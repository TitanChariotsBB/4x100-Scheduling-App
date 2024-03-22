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

    public Search(CourseList courseCatalog) {
        unfilteredResults = new ArrayList<>();
        results = new ArrayList<>();

        unfilteredResults.addAll(courseCatalog.getCourses());
    }

    public ArrayList<Course> getResults(String currentQuery) {
        return results;
    }

    public void addFilter(SearchBy sb, String filter) {
        results.clear(); // Clears results in preparation for adding classes matching filter

        if (sb.toString().equals("ALL")) { // Returns unfilteredResults
            return;
        }
        else if (sb.toString().equals("COURSE_CODE")) { // If filtering by course_code
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getCode().equals(filter)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
        else if (sb.toString().equals("COURSE_NAME")) { // If filtering by course_name
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getName().equals(filter)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
        else if (sb.toString().equals("PROFESSOR")) {
            for (int i = 0; i < unfilteredResults.size(); i++) {
                if (unfilteredResults.get(i).getProfessor().equals(filter)) {
                    results.add(unfilteredResults.get(i));
                }
            }
        }
    }

    public void removeFilter(SearchBy sb) {}
}
