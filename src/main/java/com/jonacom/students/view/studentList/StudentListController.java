package com.jonacom.students.view.studentList;

import com.jonacom.students.StudGroup;
import com.jonacom.students.Student;
import com.jonacom.students.Model;
import com.jonacom.students.utils.DateUtils;
import com.jonacom.students.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentListController {
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> surnameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label groupLabel;
    @FXML
    private Label enrollmentDateLabel;

    private Model model;
    private MainController controller;

    public StudentListController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        showStudentDetails(null);

        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));

    }


    public void setModel(Model model) {
        this.model = model;
        studentTable.setItems(model.getStudents());
    }

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    private void showStudentDetails(Student student) {
        if (student == null) {

            nameLabel.setText("");
            surnameLabel.setText("");
            groupLabel.setText("");
            enrollmentDateLabel.setText("");

        } else {

            nameLabel.setText(student.getName());
            surnameLabel.setText(student.getSurname());

            StudGroup group = student.getGroup();
            if (group == null) groupLabel.setText("none");
            else groupLabel.setText(group.getName());

            enrollmentDateLabel.setText(DateUtils.format(student.getEnrollmentDate()));

        }
    }

    public void showStudentDetails() {
        showStudentDetails(studentTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteStudent() {

        Student student = studentTable.getSelectionModel().getSelectedItem();
        if (student != null) {
            int studentNumber = model.getStudents().indexOf(student);
            if (studentNumber >= 0) {
                model.deleteStudent(studentNumber);
            }
        } else {
            showNoSelectedAlert();
        }
    }

    @FXML
    private void handleNewStudent() {
        controller.showNewStudentDialog();
    }


    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            controller.showStudentEditDialog(selectedStudent);

        } else {
            showNoSelectedAlert();
        }
    }

    private void showNoSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Incorrect student");
        alert.setHeaderText("Warning");
        alert.setContentText("Please select student.");
        alert.showAndWait();
    }


}