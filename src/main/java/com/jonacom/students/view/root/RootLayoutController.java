package com.jonacom.students.view.root;

import com.jonacom.students.Model;
import com.jonacom.students.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController {

    private Model model;
    private MainController controller;

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    private void handleShowStudentsContent() {
        controller.showStudentOverview();
    }

    @FXML
    private void handleShowGroupsContent() {
        controller.showGroupsOverview();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Students App");
        alert.setHeaderText("About");
        alert.setContentText("Students App\nver: 0.2.0\n");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}