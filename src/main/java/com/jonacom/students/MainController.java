package com.jonacom.students;

import com.jonacom.students.view.groupEdit.GroupEditDialogController;
import com.jonacom.students.view.groupList.GroupListController;
import com.jonacom.students.view.root.RootLayoutController;
import com.jonacom.students.view.studentEdit.StudentEditDialogController;
import com.jonacom.students.view.studentList.StudentListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {

    private static final String APP_TITTLE = "Students App";
    private static final String EDIT_STUDENT_DIALOG_TITTLE = "Edit student";
    private static final String EDIT_GROUP_DIALOG_TITTLE = "Edit group";
    private static final String NEW_STUDENT_DIALOG_TITTLE = "New student";
    private static final String NEW_GROUP_DIALOG_TITTLE = "New group";

    private Model model;

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Parent studentOverview;
    private Parent groupOverview;

    private Stage dialogStage;
    private BorderPane dialogRoot;
    private Parent studentEditDialog;
    private Parent groupEditDialog;

    private StudentListController studentListController;
    private GroupListController groupListController;

    private StudentEditDialogController studentEditDialogController;
    private GroupEditDialogController groupEditDialogController;


    public Stage getPrimaryStage() {
        return primaryStage;
    }



    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        primaryStage.setTitle(APP_TITTLE);

        initModel();

        initRoot();
        initStudentOverview();
        initGroupOverview();

        initDialog();
        initStudentEditDialog();
        initGroupEditDialog();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        showStudentOverview();
        primaryStage.show();

    }

    private void initModel() {
        model = new Model();
    }

    private void initRoot() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/root/RootLayout.fxml"));
        BorderPane rootLayout = null;
        try {
            rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rootLayout = rootLayout;
        RootLayoutController controller = loader.getController();
        controller.setModel(model);
        controller.setMainController(this);
    }

    private void initStudentOverview() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/studentList/StudentOverview.fxml"));
        AnchorPane studentOverview = null;
        try {
            studentOverview = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.studentOverview = studentOverview;
        StudentListController controller = loader.getController();
        controller.setModel(model);
        controller.setMainController(this);
        studentListController = controller;

    }

    private void initGroupOverview() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/groupList/GroupOverview.fxml"));
        AnchorPane groupOverview = null;
        try {
            groupOverview = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.groupOverview = groupOverview;
        GroupListController controller = loader.getController();
        controller.setModel(model);
        controller.setMainController(this);
        groupListController = controller;
    }

    private void initDialog() {
        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane dialogRoot = new BorderPane();
        Scene scene = new Scene(dialogRoot);
        dialogStage.setScene(scene);
        this.dialogStage = dialogStage;
        this.dialogRoot = dialogRoot;
    }

    private void initStudentEditDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/studentEdit/StudentEditDialog.fxml"));
        AnchorPane studentEditDialog = null;
        try {
            studentEditDialog = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.studentEditDialog = studentEditDialog;
        StudentEditDialogController controller = loader.getController();
        controller.setModel(model);
        controller.setMainController(this);
        studentEditDialogController = controller;

    }

    private void initGroupEditDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/groupEdit/GroupEditDialog.fxml"));
        AnchorPane groupEditDialog = null;
        try {
            groupEditDialog = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.groupEditDialog = groupEditDialog;
        GroupEditDialogController controller = loader.getController();
        controller.setModel(model);
        controller.setMainController(this);
        groupEditDialogController = controller;
    }


    public void showStudentOverview() {
        refreshStudentOverviewDetails();
        rootLayout.setCenter(studentOverview);
    }

    public void showGroupsOverview() {
        refreshGroupOverviewDetails();
        rootLayout.setCenter(groupOverview);
    }



    public void hideEditDialog() {
        dialogStage.close();
    }

    public void showStudentEditDialog(Student student) {
        studentEditDialogController.setStudent(student);
        dialogRoot.setCenter(studentEditDialog);
        dialogStage.setTitle(EDIT_STUDENT_DIALOG_TITTLE);
        dialogStage.showAndWait();
    }

    public void showGroupEditDialog(StudGroup group) {
        groupEditDialogController.setGroup(group);
        dialogRoot.setCenter(groupEditDialog);
        dialogStage.setTitle(EDIT_GROUP_DIALOG_TITTLE);
        dialogStage.showAndWait();
    }

    public void showNewStudentDialog() {
        studentEditDialogController.setStudent(null);
        dialogRoot.setCenter(studentEditDialog);
        dialogStage.setTitle(NEW_STUDENT_DIALOG_TITTLE);
        dialogStage.showAndWait();
    }

    public void showNewGroupDialog() {
        groupEditDialogController.setGroup(null);
        dialogRoot.setCenter(groupEditDialog);
        dialogStage.setTitle(NEW_GROUP_DIALOG_TITTLE);
        dialogStage.showAndWait();
    }


    public void refreshStudentOverviewDetails() {
        studentListController.showStudentDetails();
    }

    public void refreshGroupOverviewDetails() {
        groupListController.showGroupDetails();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
