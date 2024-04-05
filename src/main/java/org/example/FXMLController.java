package org.example;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FXMLController {
    private Search search;
    private CourseList fallSemester;
    private CourseList springSemester;
    private CourseList completedCourses;
    private CourseList courseWishList;
    private boolean past; //if past is true, past is selected. If past is false, wishList is selected

    @FXML
    private Label debugLabel;
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox searchResults;
    @FXML
    private TextField searchBar;
    @FXML
    private Accordion filterAccordion;
    @FXML
    private ComboBox<String> dptComboBox;
    @FXML
    private ComboBox<String> mtgDaysComboBox;
    @FXML
    private ComboBox<String> startTimeComboBox;
    @FXML
    private TextField courseNumberTF;
    @FXML
    private TextField courseNameTF;
    @FXML
    private TextField professorTF;

    @FXML
    private VBox fallSemesterVBox;
    @FXML
    private VBox springSemesterVBox;
    @FXML
    private VBox completedCoursesVBox;
    @FXML
    private VBox courseWishListVBox;
    @FXML
    private Label totalCreditsFall;
    @FXML
    private Label totalCreditsSpring;
    @FXML
    private Label completedCoursesLabel;
    @FXML
    private Label courseWishlistLabel;

    @FXML
    public void initialize() {
        // Get references to Search and CourseList objects
        search = Main.search;
        fallSemester = Main.fallSemester;
        springSemester = Main.springSemester;
        completedCourses = Main.past;
        courseWishList = Main.future;

        // Fill combo box options
        ControllerHelper ch = new ControllerHelper();
        dptComboBox.getItems().setAll(ch.dptOptions);
        mtgDaysComboBox.getItems().setAll(ch.dayOptions);
        startTimeComboBox.getItems().setAll(ch.timeOptions);

        // Display schedules
        displaySchedule(fallSemester, fallSemesterVBox);
        displaySchedule(springSemester, springSemesterVBox);
        displaySchedule(completedCourses, completedCoursesVBox);
        displaySchedule(courseWishList, courseWishListVBox);

        past = true;

        updateTotalCredits();
    }

    @FXML
    protected void onSearchButtonClick() {
        String searchQuery = searchBar.getText();
        debugLabel.setText("Searching for: " + searchQuery);
        search.setQuery(searchQuery);
        displaySearchResults(search.getResults());
    }

    @FXML
    protected void onKeyPressed(KeyEvent ke) {
//        if (ke.getCode() == KeyCode.ENTER)
//            onSearchButtonClick();
    }

    @FXML
    protected void onClearFiltersButtonClicked() {
        search.removeAllFilters();

        courseNumberTF.setText("");
        courseNameTF.setText("");
        professorTF.setText("");
        dptComboBox.getSelectionModel().clearSelection();
        mtgDaysComboBox.getSelectionModel().clearSelection();
        startTimeComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onApplyFiltersButtonClicked() {
        String courseName = courseNameTF.getText();

        String dpt = dptComboBox.getSelectionModel().getSelectedItem();
        String courseCode;
        if (dpt != null) {
            courseCode = dpt + courseNumberTF.getText();
        } else {
            courseCode = "";
        }

        String professor = professorTF.getText();
        //System.out.println(professor); - testing thingy
        // TODO: format date

        if (!courseName.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_NAME, courseName);
        if (!courseCode.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_CODE, courseCode);
        if (!professor.isEmpty())
            search.addFilter(Search.SearchBy.PROFESSOR, professor);
        // TODO: add date filter

        for (Filter filter : search.activeFilters) {
            System.out.println(filter.sb);
            System.out.println(filter.filter);
        }
    }

    public void displaySearchResults(ArrayList<Course> courses) {
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

    public void onAddButtonClicked(Course c) {
        String selectedTabText = tabPane.getSelectionModel().getSelectedItem().getText();
        switch (selectedTabText) {
            case "Fall Semester":
                fallSemester.addCourse(c);
                displaySchedule(fallSemester, fallSemesterVBox);
                break;
            case "Spring Semester":
                springSemester.addCourse(c);
                displaySchedule(springSemester, springSemesterVBox);
                break;
            case "College Career":
                if(past){
                    completedCourses.addCourse(c);
                    displaySchedule(completedCourses, completedCoursesVBox);
                }
                else{
                    courseWishList.addCourse(c);
                    displaySchedule(courseWishList, courseWishListVBox);
                }
            default:
                break;
        }
        updateTotalCredits();
    }

    public void onRemoveButtonClicked(Course c, CourseList cl, VBox vb) throws Exception {
        cl.removeCourse(c);
        displaySchedule(cl, vb);
        updateTotalCredits();
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
        if (name.length() > 15) {
            name = name.substring(0,15);
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
        h.setPadding(new Insets(5, 0, 5, 0));
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
        removeButton.setOnMouseClicked(event -> {
            try {
                onRemoveButtonClicked(c, courseList, scheduleVBox);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        HBox courseHBox = new HBox(10, courseInfo, removeButton);
        //courseHBox.setBackground(Background.fill(Color.rgb(208,208,208)));
        courseHBox.setPadding(new Insets(10, 0, 10, 0));
        return courseHBox;
    }

    @FXML
    public void onCompletedCoursesClick(){
        past = true;
        Font bigFont = new Font(20);
        Font smallFont = new Font(12);
        completedCoursesLabel.setFont(bigFont);
        courseWishlistLabel.setFont(smallFont);
    }

    @FXML
    public void onCourseWishlistClick(){
        past = false;
        Font bigFont = new Font(20);
        Font smallFont = new Font(12);
        completedCoursesLabel.setFont(smallFont);
        courseWishlistLabel.setFont(bigFont);
    }
}