package com.example.alshifaspecializedhospital;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Button nextBtn;

    @FXML
    private Button selectFileBtn;

    @FXML
    void onSelectFileClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("اختر ملف الأطباء doctors.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            boolean isLoaded = HospitalSystem.getInstance().loadDoctorsFromFile(selectedFile.getAbsolutePath());

            if (isLoaded) {
                HospitalSystem.getInstance().loadAppointmentsFromFile("appointments.txt");
                nextBtn.setDisable(false);
                showAlert(Alert.AlertType.INFORMATION, "تم التحميل", "تم تحميل بيانات الأطباء والمواعيد.");
            } else {
                showAlert(Alert.AlertType.ERROR, "خطأ", "تعذر تحميل ملف الأطباء.");
            }
        }
    }

    @FXML
    void onGoToMainMenuClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentBookingScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}