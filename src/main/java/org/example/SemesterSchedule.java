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
        if(super.getTotalCredits() + course.getCredits() > 19) {
            throw new IllegalArgumentException("Attempted to add too many credits");
        }
        super.addCourse(course);//adds course to the arrayList and increments totalCredits
        return null;
    }

}