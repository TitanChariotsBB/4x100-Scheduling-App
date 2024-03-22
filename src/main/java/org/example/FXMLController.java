package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class FXMLController {
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
    public void initialize() {
        ControllerHelper ch = new ControllerHelper();
        dptComboBox.getItems().setAll(ch.dptOptions);
        mtgDaysComboBox.getItems().setAll(ch.dayOptions);
        startTimeComboBox.getItems().setAll(ch.timeOptions);
    }

    @FXML
    protected void onSearchButtonClick() {
        // String selectedTabText = tabPane.getSelectionModel().getSelectedItem().getText();
        String searchQuery = searchBar.getText();
        debugLabel.setText("Searching for: " + searchQuery);
    }

    @FXML
    protected void onKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER)
            onSearchButtonClick();
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

        // TODO: call addFilter

    }
}