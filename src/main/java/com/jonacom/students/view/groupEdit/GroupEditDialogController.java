package com.jonacom.students.view.groupEdit;


import com.jonacom.students.StudGroup;
import com.jonacom.students.Model;
import com.jonacom.students.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class GroupEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField facultyField;

    private Model model;
    private MainController controller;

    private StudGroup group;

    @FXML
    private void initialize() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    public void setGroup(StudGroup group) {
        this.group = group;

        if (group == null) {
            nameField.clear();
            facultyField.clear();
        } else {
            nameField.setText(group.getName());
            facultyField.setText(group.getFaculty());
        }
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {

            if (isNewGroupMode()) {
                StudGroup newGroup = model.getGroupFromData(nameField.getText(), facultyField.getText());
                if (newGroup == null) {
                    model.addGroup(nameField.getText(), facultyField.getText());
                    controller.hideEditDialog();
                } else
                    showExistingGroupWarning();
            } else {
                model.setGroupData(model.getGroupNumber(group), nameField.getText(), facultyField.getText());
                controller.refreshGroupOverviewDetails();
                controller.hideEditDialog();
            }

        }
    }

    @FXML
    private void handleCancel() {
        controller.hideEditDialog();
    }

    private boolean isNewGroupMode() {
        return group == null;
    }

    private boolean isInputValid() {

        if (! isNewGroupMode()) {
            if (! model.isGroupExists(group)) {
                showInvalidGroupError();
                controller.hideEditDialog();
                return false;
            }
        }

        boolean isValid = true;
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "Write name!\n";
            isValid = false;
        }
        if (facultyField.getText() == null || facultyField.getText().isEmpty()) {
            errorMessage += "Write faculty name!\n";
            isValid = false;
        }

        if (isValid)
            return true;
        else {
            showInvalidFieldsWarning(errorMessage);
            return false;
        }
    }

    private void showInvalidGroupError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Invalid group");
        alert.setHeaderText("Error");
        alert.setContentText("App error, sorry.");
        alert.showAndWait();
    }

    private void showInvalidFieldsWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Invalid fields");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInvalidFieldsWarning() {
        showInvalidFieldsWarning("");
    }

    private void showExistingGroupWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Invalid group");
        alert.setHeaderText("Warning");
        alert.setContentText("Group with this properties already exists!");
        alert.showAndWait();
    }

}