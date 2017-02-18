package com.jonacom.students.view.groupList;

import com.jonacom.students.StudGroup;
import com.jonacom.students.Model;
import com.jonacom.students.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GroupListController {
    @FXML
    private TableView<StudGroup> groupTable;
    @FXML
    private TableColumn<StudGroup, String> nameColumn;
    @FXML
    private TableColumn<StudGroup, String> facultyColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label facultyLabel;

    private Model model;
    private MainController controller;

    public GroupListController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        facultyColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getFaculty()));

        showGroupDetails(null);

        groupTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showGroupDetails(newValue));

    }


    public void setModel(Model model) {
        this.model = model;
        groupTable.setItems(model.getGroups());
    }

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    private void showGroupDetails(StudGroup group) {
        if (group == null) {
            nameLabel.setText("");
            facultyLabel.setText("");
        } else {
            nameLabel.setText(group.getName());
            facultyLabel.setText(group.getFaculty());
        }
    }

    public void showGroupDetails() {
        showGroupDetails(groupTable.getSelectionModel().getSelectedItem());
    }


    @FXML
    private void handleDeleteGroup() {

        StudGroup group = groupTable.getSelectionModel().getSelectedItem();
        if (group != null) {
            int number = model.getGroups().indexOf(group);
            if (number >= 0) {
                model.deleteGroup(number);
            }
        } else {
            showNoSelectedAlert();
        }
    }

    @FXML
    private void handleNewGroup() {
        controller.showNewGroupDialog();
    }


    @FXML
    private void handleEditGroup() {
        StudGroup selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            controller.showGroupEditDialog(selectedGroup);
        } else {
            showNoSelectedAlert();
        }
    }

    private void showNoSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getPrimaryStage());
        alert.setTitle("Incorrect group");
        alert.setHeaderText("Warning");
        alert.setContentText("Please select group.");
        alert.showAndWait();
    }


}