package org.example;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class FXMLController {
    private Search search;
    private CourseList fallSemester;
    private CourseList springSemester;
    private CourseList completedCourses;
    private CourseList courseWishList;
    private boolean pastSelected; //if past is true, past is selected. If past is false, wishList is selected
    private String currentTab = "";

    @FXML
    private TabPane tabPane;
    @FXML
    private VBox searchResults;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> dptComboBox;
    @FXML
    private ComboBox<String> mtgDaysComboBox;
//    @FXML
//    private ComboBox<String> startTimeComboBox;
    @FXML
    private TextField courseNumberTF;
    @FXML
    private TextField courseNameTF;
    @FXML
    private TextField professorTF;

    @FXML
    private Pane fallSemesterPane;
    @FXML
    private Pane springSemesterPane;
    @FXML
    private VBox completedCoursesVBox;
    @FXML
    private VBox courseWishListVBox;
    @FXML
    private Label totalCreditsFall;
    @FXML
    private Label totalCreditsSpring;
    @FXML
    private ComboBox<String> rangeStartComboBox;
    @FXML
    private ComboBox<String> rangeEndComboBox;

    @FXML
    public void initialize() {
        // Get references to Search and CourseList objects
        search = Main.search;
        fallSemester = Main.fallSemester;
        springSemester = Main.springSemester;
        completedCourses = Main.past;
        courseWishList = Main.future;
        currentTab = tabPane.getSelectionModel().getSelectedItem().getText();

        // Fill combo box options
        ControllerHelper ch = new ControllerHelper();
        dptComboBox.getItems().setAll(ch.dptOptions);
        mtgDaysComboBox.getItems().setAll(ch.dayOptions);
        //startTimeComboBox.getItems().setAll(ch.timeOptions);
        rangeStartComboBox.getItems().setAll(ch.timeOptions);
        rangeEndComboBox.getItems().setAll(ch.timeOptions);

//        fallSemesterPane.setBackground(Background.fill(Paint.valueOf("BBBBBB")));
//        springSemesterPane.setBackground(Background.fill(Paint.valueOf("BBBBBB")));

        // Display schedules
        displayCalendarSchedule(fallSemester, fallSemesterPane);
        displayCalendarSchedule(springSemester, springSemesterPane);
        displaySchedule(completedCourses, completedCoursesVBox);
        displaySchedule(courseWishList, courseWishListVBox);

        pastSelected = true;

        updateTotalCredits();
    }

    @FXML
    protected void onSearchButtonClick() {
        String searchQuery = searchBar.getText();
        search.setQuery(searchQuery);
        displaySearchResults(filterBySemester(search.getResults(), currentTab));
        LogHelper.logUserAction(new UserAction(null,null, UserAction.actionType.SEARCH));
    }

    @FXML
    protected void onClearFiltersButtonClicked() {
        search.removeAllFilters();

        courseNumberTF.setText("");
        courseNameTF.setText("");
        professorTF.setText("");
        dptComboBox.getSelectionModel().clearSelection();
        mtgDaysComboBox.getSelectionModel().clearSelection();
        //startTimeComboBox.getSelectionModel().clearSelection();
        rangeStartComboBox.getSelectionModel().clearSelection();
        rangeEndComboBox.getSelectionModel().clearSelection();

        LogHelper.logUserAction(new UserAction(null,null, UserAction.actionType.CLEAR_FILTERS));
    }

    @FXML
    protected void onApplyFiltersButtonClicked() {
        String courseName = courseNameTF.getText();

        String dpt = dptComboBox.getSelectionModel().getSelectedItem();
        String courseCode;

        if (dpt == null) {
            courseCode = "";
        }
        else if (dpt.equals("Any")) {
            courseCode = dpt + " " + courseNumberTF.getText();
        }
        else {
            courseCode = dpt + courseNumberTF.getText();
        }

        String professor = professorTF.getText();
        String mtgDays = mtgDaysComboBox.getSelectionModel().getSelectedItem();
        //String startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        String rangeStart = rangeStartComboBox.getSelectionModel().getSelectedItem();
        String rangeEnd = rangeEndComboBox.getSelectionModel().getSelectedItem();

        search.removeAllFilters(); // Clear filters for each application of new filters.
        // Only whatever is currently filled out should be filtered.

        if (!courseName.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_NAME, courseName);
        if (!courseCode.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_CODE, courseCode);
        if (!professor.isEmpty())
            search.addFilter(Search.SearchBy.PROFESSOR, professor);
        if (mtgDays != null)
            search.addFilter(Search.SearchBy.DATE, mtgDays);
//        if (startTime != null)
//            search.addFilter(Search.SearchBy.TIME, startTime);

        if (rangeStart != null || rangeEnd != null) {
            //System.out.println("Range Start:" + rangeStart);
            if  (rangeEnd == null) {
                //System.out.println("Range end:" + rangeEnd);
            }
            //System.out.println(rangeStart + "," + rangeEnd);

            search.addFilter(Search.SearchBy.TIME_RANGE, rangeStart + "," + rangeEnd);
        }


        /*for (Filter filter : search.activeFilters) { // Testing
            System.out.println(filter.sb);
            System.out.println(filter.filter);
        }*/

        LogHelper.logUserAction(new UserAction(null,null, UserAction.actionType.ADD_FILTER));
    }

    public void displaySearchResults(ArrayList<Course> courses) {
        if (courses.isEmpty()) {
            searchResults.getChildren().clear();
            Label noMatch = new Label("No matching courses found.");
            noMatch.setPadding(new Insets(5));
            searchResults.getChildren().add(noMatch);
            return;
        }

        ArrayList<HBox> topCourses = new ArrayList<>();
        int i = 0;
        int max = 60;
        while (i < max && i < courses.size()) {
            Course c = courses.get(i);
            topCourses.add(makeSearchResultHBox(c));
            i++;
        }
        searchResults.getChildren().setAll(topCourses);
    }

    public void displaySchedule(CourseList courseList, VBox scheduleVBox) {
        ArrayList<HBox> courses = new ArrayList<>();
        for (Course c : courseList.getCourses()) {
            courses.add(makeScheduleViewHBox(c, courseList, scheduleVBox));
        }
        scheduleVBox.getChildren().setAll(courses);
    }

    public void displayCalendarSchedule(CourseList courseList, Pane schedulePane) {
        schedulePane.getChildren().clear();

        double y_idx;
        double height;
        HBox currentHBox;

        for (Course c : courseList.getCourses()) {
            // If meetingTimes is null, just add a course without formatting size and position
            if (c.getMeetingTimes() == null) {
                currentHBox = makeCalendarScheduleViewHBox(c, courseList, schedulePane);
                schedulePane.getChildren().add(currentHBox);
                return;
            }

            int x_idx = 0;
            for (LocalDateTime[] meetingTime : c.getMeetingTimes()) {
                if (meetingTime == null) { x_idx += 100; continue; }

                y_idx = dateTimeToLayoutPos(meetingTime[0]);
                height = dateTimeToLayoutPos(meetingTime[1]) - y_idx;
                currentHBox = makeCalendarScheduleViewHBox(c, courseList, schedulePane);
                currentHBox.setPrefSize(100, height);
                currentHBox.setMinHeight(height);
                currentHBox.setLayoutX(x_idx);
                currentHBox.setLayoutY(y_idx);
                schedulePane.getChildren().add(currentHBox);

                x_idx += 100;
            }
        }
    }

    private double dateTimeToLayoutPos(LocalDateTime time) {
        return (time.getHour() - 8.0) * 40.0 + (time.getMinute() / 30.0) * 20.0;
    }

    private HBox makeCalendarScheduleViewHBox(Course c, CourseList courseList, Pane schedulePane) {
        String code = c.getCode();
        String name = c.getName();
        Label codeLabel = new Label(code);
        Label nameLabel = new Label(name);
        VBox courseInfo = new VBox(codeLabel, nameLabel);
        Button removeButton = new Button("x");
        Tooltip rmtt = new Tooltip("Remove class from schedule");
        removeButton.setTooltip(rmtt);
        removeButton.setOnMouseClicked(event -> {
            try {
                onRemoveButtonClicked(c, courseList, schedulePane);
            } catch (Exception e) {
                LogHelper.logError("Clicking the remove button threw an exception");
                throw new RuntimeException(e);
            }
        });
        HBox box = new HBox(2, courseInfo, removeButton);
        box.setBackground(Background.fill(Paint.valueOf("A0E0FFC0")));
        box.setPadding(new Insets(0, 0, 0, 2));
        return box;
    }

    @FXML
    public void onAddButtonClicked(Course c) {
        switch (currentTab) {
            case "Fall Semester":
                addCourseToSemesterSchedule(c, fallSemester, fallSemesterPane);
                break;
            case "Spring Semester":
                addCourseToSemesterSchedule(c, springSemester, springSemesterPane);
                break;
            case "College Career":
                if (pastSelected) {
                    completedCourses.addCourse(c);
                    displaySchedule(completedCourses, completedCoursesVBox);
                    LogHelper.logUserAction(new UserAction(completedCourses,c, UserAction.actionType.ADD_COURSE));
                } else {
                    courseWishList.addCourse(c);
                    displaySchedule(courseWishList, courseWishListVBox);
                    LogHelper.logUserAction(new UserAction(courseWishList,c, UserAction.actionType.ADD_COURSE));
                }
            default:
                break;
        }
        updateTotalCredits();
    }

    private void addCourseToSemesterSchedule(Course toAdd, CourseList semester, Pane semesterPane) {
        Course existingCourse = semester.addCourse(toAdd);

        if (toAdd.unmetPrereq() != null) { //checks if there are unmet prereqs
            launchPrereqsDialog(toAdd, toAdd.unmetPrereq(), semester);
        }
        if (semester.getTotalCredits() > 21) {
            LogHelper.logMessage("attempted to add more than 21 credits. Course not added.");
            launchCreditWarning();
            try { semester.removeCourse(toAdd); } catch (Exception e) {}
        }
        if (existingCourse != null) {//addCourse returns the existing course if there is a conflict
            launchConflictDialog(toAdd, existingCourse, semester);
        }
        if (semester.getTotalCredits() > 18) {
            launchCreditDialog(toAdd, semester);
        } else {
            LogHelper.logUserAction(new UserAction(semester,toAdd, UserAction.actionType.ADD_COURSE));
        }
        displayCalendarSchedule(semester, semesterPane);
    }

    private void launchCreditDialog(Course toAdd, CourseList semester) {
        Alert conflictAlert = new Alert(Alert.AlertType.CONFIRMATION);
        conflictAlert.setTitle("Credit warning");
        conflictAlert.setContentText("If you add this class, your tuition may increase.");

        Optional<ButtonType> result = conflictAlert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.CANCEL)) {
            try {
                semester.removeCourse(toAdd);
            } catch (Exception e) {}
        }
        else{
            LogHelper.logUserAction(new UserAction(semester,toAdd, UserAction.actionType.ADD_COURSE));
        }
    }

    private void launchCreditWarning() {
        Alert creditAlert = new Alert(Alert.AlertType.WARNING);
        creditAlert.setTitle("Credit limit reached!");
        creditAlert.setContentText("Your schedule cannot exceed 21 credits.");

        creditAlert.show();
    }

    private void launchConflictDialog(Course conflictingCourse, Course existingCourse, CourseList semester) {
        Alert conflictAlert = new Alert(Alert.AlertType.CONFIRMATION);
        conflictAlert.setTitle("Conflicting course!");
        conflictAlert.setContentText("Do you want to replace " + existingCourse.getCode() +
                " with " + conflictingCourse.getCode() + "?");

        Optional<ButtonType> result = conflictAlert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            try {
                semester.removeCourse(existingCourse);
            } catch (Exception e) {
                return;
            }
            semester.addCourse(conflictingCourse);
            LogHelper.logUserAction(new UserAction(semester,existingCourse, UserAction.actionType.REMOVE_IN_CONFLICT));
            LogHelper.logUserAction(new UserAction(semester,conflictingCourse, UserAction.actionType.ADD_IN_CONFLICT));
        }
        else{
            LogHelper.logMessage("Conflicting course chosen. User kept original course.");
        }
    }

    //tells a user there are unmet prereqs and prompts them to add the missing classes
    private void launchPrereqsDialog(Course toAdd, ArrayList<Course> missing, CourseList semester) {
        Alert conflictAlert = new Alert(Alert.AlertType.CONFIRMATION);
        conflictAlert.setTitle("Unmet Prerequisites!");
        conflictAlert.setContentText("Do you want to add " + missing.size() + " unmet prerequisites to past courses?");

        Optional<ButtonType> result = conflictAlert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            for(Course c : missing) {
                Main.past.addCourse(c);
            }
            semester.addCourse(toAdd);
            displaySchedule(completedCourses, completedCoursesVBox);

            LogHelper.logUserAction(new UserAction(Main.past, missing.get(0), UserAction.actionType.ADD_COURSE)); // Will only add the first missing course to the log
            LogHelper.logUserAction(new UserAction(semester, toAdd, UserAction.actionType.ADD_COURSE));
        }

    }

    @FXML
    public void onRemoveButtonClicked(Course c, CourseList cl, Pane p) throws Exception {
        cl.removeCourse(c);
        displayCalendarSchedule(cl, p);
        updateTotalCredits();
        LogHelper.logUserAction(new UserAction(cl,c, UserAction.actionType.REMOVE_COURSE));
    }

    @FXML
    public void onRemoveButtonClicked(Course c, CourseList cl, VBox vb) throws Exception {
        cl.removeCourse(c);
        displaySchedule(cl, vb);
        updateTotalCredits();
        LogHelper.logUserAction(new UserAction(cl,c, UserAction.actionType.REMOVE_COURSE));
    }

    public void updateTotalCredits() {
        totalCreditsFall.setText("Total Credits: " + fallSemester.getTotalCredits());
        totalCreditsSpring.setText("Total Credits: " + springSemester.getTotalCredits());
    }

    @FXML
    public void openStatusSheet() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.gcc.edu/Utility/Offices/Registrar/Program-Guides"));
    }

    @FXML
    public void openCourseCatalog() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.gcc.edu/Home/Academics/Majors-Departments/College-Catalog"));
    }

    public HBox makeSearchResultHBox(Course c) {
        String code = c.getCode();
        String name = c.getName();
        if (name.length() > 25) {
            name = name.substring(0,25);
            name += "...";
        }
        String meetingTime;
        if (c.getMeetingTimes() != null) {
            meetingTime = c.getMeetingTimeString();
        } else {
            meetingTime = "";
        }
        Label codeLabel = new Label(code + ": " + name);
        Label mtgLabel = new Label(meetingTime);
        VBox courseInfo = new VBox(codeLabel, mtgLabel);
        Button addButton = new Button("Add");
        addButton.setOnMouseClicked(event -> onAddButtonClicked(c));
        HBox h = new HBox(10, courseInfo, addButton);
        h.setPadding(new Insets(5, 0, 5, 3));
        return h;
    }

    public HBox makeScheduleViewHBox(Course c, CourseList courseList, VBox scheduleVBox) {
        String code = c.getCode();
        String name = c.getName();
        String meetingTime;
        if (c.getMeetingTimes() != null) {
            meetingTime = c.getMeetingTimeString();
        } else {
            meetingTime = "";
        }
        Label codeLabel = new Label(code + ": " + name);
        Label time = new Label(meetingTime);
        VBox courseInfo = new VBox(codeLabel, time);
        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(60);
        removeButton.setOnMouseClicked(event -> {
            try {
                onRemoveButtonClicked(c, courseList, scheduleVBox);
            } catch (Exception e) {
                LogHelper.logError("Remove Button caused an error: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        HBox courseHBox = new HBox(10, courseInfo, removeButton);
        //courseHBox.setBackground(Background.fill(Color.rgb(208,208,208)));
        courseHBox.setPadding(new Insets(10, 0, 10, 5));
        return courseHBox;
    }

    @FXML
    public void onCompletedCoursesClick(){
        pastSelected = true;
        completedCoursesVBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, null)));
        courseWishListVBox.setBorder(null);
    }

    @FXML
    public void onCourseWishlistClick(){
        pastSelected = false;
        courseWishListVBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, null)));
        completedCoursesVBox.setBorder(null);
    }

    @FXML
    public void onUndoButtonClicked() {
        LogHelper.undo();
        //update the display
        switch(currentTab){
            case "Fall Semester":
                displayCalendarSchedule(fallSemester, fallSemesterPane);
                break;
            case "Spring Semester":
                displayCalendarSchedule(springSemester, springSemesterPane);
                break;
            case "College Career":
                displaySchedule(courseWishList, courseWishListVBox);
                displaySchedule(completedCourses, completedCoursesVBox);
                break;
        }
        updateTotalCredits();
    }

    public void onTabSwitch() {
        if (currentTab.isEmpty()) return;
        if (currentTab.equals(tabPane.getSelectionModel().getSelectedItem().getText())) return;
        currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        clearSearchResults();
    }

    public void clearSearchResults() {
        ArrayList<HBox> empty = new ArrayList<>();
        searchResults.getChildren().setAll(empty);
        onClearFiltersButtonClicked();
    }

    public ArrayList<Course> filterBySemester(ArrayList<Course> searchResults, String semester) {
        ArrayList<Course> semesterResults = new ArrayList<>();
        for (Course c : searchResults) {
            if (c.getIsFall() == (semester.equals("Fall Semester"))) {
                semesterResults.add(c);
            }
        }
        return semesterResults;
    }

    @FXML
    protected void onKeyPressed(KeyEvent ke) {
//        if (ke.getCode() == KeyCode.ENTER)
//            onSearchButtonClick();
    }

}