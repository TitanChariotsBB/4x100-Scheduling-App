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
    private CourseList past;
    private CourseList future;

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
    public void initialize() {
        search = Main.search;
        fallSemester = Main.fallSemester;
        ControllerHelper ch = new ControllerHelper();
        dptComboBox.getItems().setAll(ch.dptOptions);
        mtgDaysComboBox.getItems().setAll(ch.dayOptions);
        startTimeComboBox.getItems().setAll(ch.timeOptions);
        displayFallSemester();
    }

    @FXML
    protected void onSearchButtonClick() {
        String searchQuery = searchBar.getText();
        debugLabel.setText("Searching for: " + searchQuery);
        search.setCurrentQuery(searchQuery);
        search.populateResults();
        displaySearchResults(search.getResults());
    }

    @FXML
    protected void onKeyPressed(KeyEvent ke) {
//        if (ke.getCode() == KeyCode.ENTER)
//            onSearchButtonClick();
    }

    @FXML
    protected void onClearFiltersButtonClicked() {
        courseNumberTF.setText("");
        courseNameTF.setText("");
        professorTF.setText("");
        dptComboBox.getSelectionModel().clearSelection();
        mtgDaysComboBox.getSelectionModel().clearSelection();
        startTimeComboBox.getSelectionModel().clearSelection();
        // TODO: call remove filter
    }

    @FXML
    protected void onApplyFiltersButtonClicked() {
        String courseName = courseNameTF.getText();
        String courseCode = dptComboBox.getSelectionModel().getSelectedItem() + " " +
                courseNumberTF.getText();
        String professor = professorTF.getText();
        // TODO: format date

        if (!courseName.isEmpty())
            search.addFilter(Search.SearchBy.COURSE_NAME, courseName);
        if (!courseCode.equals(" "))
            search.addFilter(Search.SearchBy.COURSE_CODE, courseCode);
        if (!professor.isEmpty())
            search.addFilter(Search.SearchBy.PROFESSOR, professor);
        // TODO: add date filter
    }

    public void displaySearchResults(ArrayList<Course> courses) {
        // TODO: test this! (also figure out a way to display more than 5 courses)
        ArrayList<HBox> topCourses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course c = courses.get(i);
            String code = c.getCode();
            Label label = new Label(code);
            Button addButton = new Button("Add");
            addButton.setOnMouseClicked(event -> {onAddButtonClicked(c);});
            topCourses.add(new HBox(label, addButton));
        }
        searchResults.getChildren().setAll(topCourses);
    }

    public void displayFallSemester() {
        ArrayList<HBox> courses = new ArrayList<>();
        for (Course c : fallSemester.getCourses()) {
            String code = c.getCode();
            Label label = new Label(code);
            Button removeButton = new Button("Remove");
            removeButton.setOnMouseClicked(event -> {
                try {
                    onRemoveButtonClicked(c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            courses.add(new HBox(label, removeButton));
        }
        fallSemesterVBox.getChildren().setAll(courses);
    }

    public void onAddButtonClicked(Course c) {
        String selectedTabText = tabPane.getSelectionModel().getSelectedItem().getText();
        switch (selectedTabText) {
            case "Fall Semester":
                fallSemester.addCourse(c);
                displayFallSemester();
                break;
            case "Spring Semester":
                springSemester.addCourse(c);
                break;
            default:
                break;
        }
    }

    public void onRemoveButtonClicked(Course c) throws Exception {
        String selectedTabText = tabPane.getSelectionModel().getSelectedItem().getText();
        switch (selectedTabText) {
            case "Fall Semester":
                fallSemester.removeCourse(c);
                displayFallSemester();
                break;
            case "Spring Semester":
                springSemester.removeCourse(c);
                break;
            default:
                break;
        }
    }


}