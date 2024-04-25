package org.example;

public class UserAction {
    private CourseList affectedList;
    private Course affectedCourse;
    private Search search;
    public enum actionType {ADD, REMOVE, SEARCH, UNDO}
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

        if(aType == actionType.ADD){
            result = "Added " + affectedCourse.getName() + " to " + affectedList;
        }
        else if(aType == actionType.REMOVE){
            result = "Removed " + affectedCourse.getName() + " from " + affectedList;
        }
        else if(aType == actionType.SEARCH){
            result = "Searched for " + search.getCurrentQuery();
        }
        else if(aType == actionType.UNDO){
            result = "UNDO";
        }

        return result;
    }
}
