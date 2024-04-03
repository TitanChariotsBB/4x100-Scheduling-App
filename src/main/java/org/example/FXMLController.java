package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FXMLController {
    private Search search;
    private CourseList fallSemester;
    private CourseList springSemester;
    private CourseList completedCourses;
    private CourseList courseWishList;

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
    }

    @FXML
    protected void onSearchButtonClick() {
        String searchQuery = searchBar.getText();
        debugLabel.setText("Searching for: " + searchQuery);
        //System.out.println(searchQuery + "What the heck man"); -- Testing thingy
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
        // THIS COULD ALL BE REPLACED BY search.removeAllFilters(),
        // if such a method were implemented
        String courseName = courseNameTF.getText();
        String courseCode = dptComboBox.getSelectionModel().getSelectedItem() + " " +
                courseNumberTF.getText();
        String professor = professorTF.getText();
        String date = "";
        // TODO: format date

        if (!courseName.isEmpty())
            search.removeFilter(Search.SearchBy.COURSE_NAME);
        if (!courseCode.equals(" "))
            search.removeFilter(Search.SearchBy.COURSE_CODE);
        if (!professor.isEmpty())
            search.removeFilter(Search.SearchBy.PROFESSOR);
        if (!date.isEmpty())
            search.removeFilter(Search.SearchBy.TIME);

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
        String courseCode = dptComboBox.getSelectionModel().getSelectedItem() + " " +
                courseNumberTF.getText(); // This line right here is a problem; it makes
        // courseCode null if the user doesn't select anything from the dropdown menu
        String professor = professorTF.getText();
        //System.out.println(professor); - testing thingy
        // TODO: format date

        if (!courseName.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_NAME, courseName);
        if (!courseCode.equals(" "))
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
        int max = 5;
        while (i < max && i < courses.size()) {
            Course c = courses.get(i);
            String code = c.getCode();
            Label label = new Label(code);
            Button addButton = new Button("Add");
            addButton.setOnMouseClicked(event -> onAddButtonClicked(c));
            topCourses.add(new HBox(20, label, addButton));
            i++;
        }
        searchResults.getChildren().setAll(topCourses);
    }

    public void displaySchedule(CourseList courseList, VBox scheduleVBox) {
        ArrayList<HBox> courses = new ArrayList<>();
        for (Course c : courseList.getCourses()) {
            String code = c.getCode();
            Label label = new Label(code);
            Button removeButton = new Button("Remove");
            removeButton.setOnMouseClicked(event -> {
                try {
                    onRemoveButtonClicked(c, courseList, scheduleVBox);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            HBox courseHBox = new HBox(20, label, removeButton);
            //courseHBox.setBackground(Background.fill(Color.rgb(208,208,208)));
            courses.add(courseHBox);
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
            default:
                break;
        }
    }

    public void onRemoveButtonClicked(Course c, CourseList cl, VBox vb) throws Exception {
        cl.removeCourse(c);
        displaySchedule(cl, vb);
    }


}