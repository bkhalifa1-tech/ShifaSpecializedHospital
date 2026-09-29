package com.example.alshifaspecializedhospital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("نظام مستشفى الشفاء التخصصي - تحميل البيانات");
        primaryStage.setScene(new Scene(root, 655, 442));
        primaryStage.show();
    }

    @Override
    public void stop() {
        HospitalSystem.getInstance().saveAppointmentsToFile("appointments.txt");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
