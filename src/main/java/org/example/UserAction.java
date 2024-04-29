package org.example;

public class UserAction {
    private CourseList affectedList;
    private Course affectedCourse;
    private Search search;
    public enum actionType {ADD_COURSE, REMOVE_COURSE, SEARCH, ADD_FILTER, CLEAR_FILTERS, UNDO}
    private actionType aType;

    public UserAction(CourseList affectedList, Course affectedCourse, Search search, actionType aType){
        this.affectedList = affectedList;
        this.affectedCourse = affectedCourse;
        this.search = search;
        this.aType = aType;
    }

    public CourseList getAffectedList() {
        return affectedList;
    }

    public Course getAffectedCourse() {
        return affectedCourse;
    }

    public Search getSearch() {
        return search;
    }

    public actionType getaType() {
        return aType;
    }

    @Override
    public String toString(){
        String result = null;

        switch(aType) {
            case actionType.ADD_COURSE:
                result = "Added " + affectedCourse.getName() + " to " + affectedList;
                break;
            case actionType.REMOVE_COURSE:
                result = "Removed " + affectedCourse.getName() + " from " + affectedList;
                break;
            case actionType.SEARCH:
                result = "Searched for " + search.getCurrentQuery();
                break;
            case actionType.UNDO:
                result = "UNDO";
                break;
            case actionType.CLEAR_FILTERS:
                result = "Cleared Filters";
                break;
            case actionType.ADD_FILTER:
                result = "Added Filter. New filters list: " + search.activeFilters.toString();
                break;

        }

        return result;
    }
}