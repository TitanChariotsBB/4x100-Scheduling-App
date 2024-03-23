package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
}