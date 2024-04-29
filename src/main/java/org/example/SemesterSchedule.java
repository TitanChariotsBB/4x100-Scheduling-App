package org.example;

public class SemesterSchedule extends CourseList {
    private final Boolean isFall;

    public SemesterSchedule(Boolean isFall){
        super();
        this.isFall = isFall;
        // we'll probably want a constructor that loads from a file.
    }

    public SemesterSchedule(CourseList listToConvert, Boolean isFall){
        super();
        this.isFall = isFall;

        for(Course course : listToConvert.getCourses()){
            this.addCourse(course);
        }
    }

    @Override
    public Course addCourse(Course course) throws IllegalArgumentException {
        for (Course existingCourse : super.getCourses()) {
            if (course.overlapsWith(existingCourse)) {
                return existingCourse;
            }
        }
        super.addCourse(course);//adds course to the arrayList and increments totalCredits
        return null;
    }

    @Override
    public String toString(){
        if(isFall){
            return "fallSchedule";
        }
        return "springSchedule";
    }
}