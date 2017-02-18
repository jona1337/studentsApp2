package com.jonacom.students.view.studentEdit;


import com.jonacom.students.StudGroup;
import com.jonacom.students.Student;
import com.jonacom.students.Model;
import com.jonacom.students.utils.DateUtils;
import com.jonacom.students.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Date;

public class StudentEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField enrollmentDateField;

    private Model model;
    private MainController controller;

    private Student student;

    @FXML
    private void initialize() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    public void setStudent(Student student) {
        this.student = student;

        if (student == null) {
            nameField.clear();
            surnameField.clear();
            groupField.clear();
            enrollmentDateField.clear();
        } else {
            nameField.setText(student.getName());
            surnameField.setText(student.getSurname());
            StudGroup group = student.getGroup();
            if (group != null)
                groupField.setText(student.getGroup().getName());
            else
                groupField.setText("");

            enrollmentDateField.setText(DateUtils.format(student.getEnrollmentDate()));
        }
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {

            StudGroup group = null;
            if (groupField.getText() != null && !groupField.getText().isEmpty())
                group = model.getGroupFromName(groupField.getText());

            Date enrollmentDate = new Date();
            if (enrollmentDateField.getText() != null && !enrollmentDateField.getText().isEmpty())
                enrollmentDate = DateUtils.parse(enrollmentDateField.getText());

            if (isNewStudentMode()) {
                Student newStudent = model.getStudentFromData(nameField.getText(), surnameField.getText(), group, enrollmentDate);
                if (newStudent == null) {
                    model.addStudent(nameField.getText(), surnameField.getText(), group, enrollmentDate);
                    controller.hideEditDialog();
                } else
                    showExistingStudentWarning();
            } else {
                model.setStudentData(model.getStudentNumber(student), nameField.getText(), surnameField.getText(), group, enrollmentDate);
                controller.refreshStudentOverviewDetails();
                controller.hideEditDialog();
            }

        }
    }

    @FXML
    private void handleCancel() {
        controller.hideEditDialog();
    }

    private boolean isNewStudentMode() {
        return student == null;
    }

    private boolean isInputValid() {

        if (! isNewStudentMode()) {
            if (! model.isStudentExists(student)) {
                showInvalidStudentError();
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
        if (surnameField.getText() == null || surnameField.getText().isEmpty()) {
            errorMessage += "Write surname!\n";
            isValid = false;
        }
        if (groupField.getText() != null && !groupField.getText().isEmpty()) {
            StudGroup group = model.getGroupFromName(groupField.getText());
            if (group == null) {
                errorMessage += "Invalid group!\n";
                isValid = false;
            }
        }
        if (enrollmentDateField.getText() != null && !enrollmentDateField.getText().isEmpty()) {
            if (!DateUtils.isValidDate(enrollmentDateField.getText())) {
                errorMessage += "Invalid date!\n";
                isValid = false;
            }
        }

        if (isValid)
            return true;
        else {
            showInvalidFieldsWarning(errorMessage);
            return false;
        }
    }

    private void showInvalidStudentError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Invalid student");
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

    private void showExistingStudentWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Invalid student");
        alert.setHeaderText("Warning");
        alert.setContentText("Student with this properties already exists!");
        alert.showAndWait();
    }

}