package com.example.alshifaspecializedhospital;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class DisplayAppointmentsController {

    private Stage stage;

    @FXML
    private RadioButton rbCancel;

    @FXML
    private RadioButton rbComplete;

    @FXML
    private RadioButton rbStart;

    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private TextArea txt_allAppointments;

    @FXML
    private TextField txtAppointmentId;

    public void fillFields() {
        if (stage != null && stage.getUserData() != null) {
            String data = (String) stage.getUserData();
            txt_allAppointments.setText(data);
        }
    }

    @FXML
    void handleApplyStatusChange(ActionEvent event) {
        String appointmentIdText = txtAppointmentId.getText().trim();

        if (appointmentIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "تنبيه", "يرجى إدخال رقم الموعد.");
            return;
        }

        try {
            int appointmentId = Integer.parseInt(appointmentIdText);
            Appointment appointment = HospitalSystem.getInstance()
                    .findAppointmentById(appointmentId);

            if (appointment == null) {
                showAlert(Alert.AlertType.ERROR, "خطأ", "الموعد غير موجود.");
                return;
            }

            if (rbStart.isSelected()) {
                appointment.startAppointment();
            } else if (rbComplete.isSelected()) {
                appointment.completeAppointment();
            } else if (rbCancel.isSelected()) {
                appointment.cancelAppointment();
            } else {
                showAlert(Alert.AlertType.WARNING, "تنبيه", "يرجى اختيار حالة الموعد.");
                return;
            }

            HospitalSystem.getInstance().saveAppointmentsToFile("appointments.txt");

            String data = getAppointmentsAsString();
            stage.setUserData(data);
            fillFields();

            txtAppointmentId.clear();
            statusGroup.selectToggle(null);

            showAlert(Alert.AlertType.INFORMATION, "تم التحديث", "تم تحديث حالة الموعد بنجاح.");

        } catch (NumberFormatException exception) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "رقم الموعد يجب أن يكون رقمًا صحيحًا.");
        } catch (IllegalStateException exception) {
            showAlert(Alert.AlertType.WARNING, "تنبيه", exception.getMessage());
        }
    }

    @FXML
    void onButtonBackPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("AppointmentBookingScreen.fxml")
        );
        Parent root = loader.load();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String getAppointmentsAsString() {
        StringBuilder data = new StringBuilder();

        for (Appointment appointment : HospitalSystem.getInstance().getAppointmentsList()) {
            data.append("رقم الموعد: ").append(appointment.getId()).append("\n");
            data.append("اسم المريض: ").append(appointment.getPatient().getName()).append("\n");
            data.append("اسم الطبيب: ").append(appointment.getDoctor().getName()).append("\n");
            data.append("التاريخ: ").append(appointment.getAppointmentDate()).append("\n");
            data.append("الساعة: ").append(appointment.getAppointmentHour()).append(":00\n");
            data.append("الحالة: ").append(appointment.getStatus()).append("\n");
            data.append("--------------------------------\n");
        }

        return data.toString();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}