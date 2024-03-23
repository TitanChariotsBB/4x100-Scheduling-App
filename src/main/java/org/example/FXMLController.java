package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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
    private TextArea fallSemesterSchedule;
    @FXML
    private TextArea springSemesterSchedule;

    @FXML
    public void initialize() {
        search = Main.search;
        fallSemester = Main.fallSemester;
        ControllerHelper ch = new ControllerHelper();
        dptComboBox.getItems().setAll(ch.dptOptions);
        mtgDaysComboBox.getItems().setAll(ch.dayOptions);
        startTimeComboBox.getItems().setAll(ch.timeOptions);
        fallSemesterSchedule.setText(fallSemester.getFormattedSchedule());
    }

    @FXML
    protected void onSearchButtonClick() {
        // String selectedTabText = tabPane.getSelectionModel().getSelectedItem().getText();
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
        // TODO: display searh results
        searchResults.getChildren().add(new Label(courses.get(0).getCode()));
    }

    public void setSearch(Search search) {
        this.search = search;
        if (search == null)
            System.out.println("ERROR! the Search object passed into FXMLController is null!!!\n\n");
    }
}