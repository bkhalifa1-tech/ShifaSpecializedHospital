package com.example.alshifaspecializedhospital;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppointmentBookingController {

    @FXML
    private ComboBox<Doctor> cmbDoctors;

    @FXML
    private DatePicker dpAppointmentDate;

    @FXML
    private TextField txtAppointmentTime;

    @FXML
    private TextField txtPatientAge;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPatientPhone;

    @FXML
    private TextField txtSymptoms;

    @FXML
    public void initialize() {
        cmbDoctors.setItems(FXCollections.observableArrayList(
                HospitalSystem.getInstance().getDoctorsList()
        ));
    }

    @FXML
    void handleBookAppointment(ActionEvent event) {
        try {
            String patientName = txtPatientName.getText().trim();
            String patientId = txtPatientId.getText().trim();
            String patientPhone = txtPatientPhone.getText().trim();
            String symptoms = txtSymptoms.getText().trim();
            Doctor selectedDoctor = cmbDoctors.getValue();
            LocalDate selectedDate = dpAppointmentDate.getValue();

            if (patientName.isEmpty() || patientId.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "تنبيه", "يرجى إدخال اسم المريض ورقم الهوية.");
                return;
            }

            if (selectedDoctor == null) {
                showAlert(Alert.AlertType.WARNING, "تنبيه", "يرجى اختيار الطبيب.");
                return;
            }

            if (selectedDate == null) {
                showAlert(Alert.AlertType.WARNING, "تنبيه", "يرجى اختيار تاريخ الموعد.");
                return;
            }

            int appointmentHour = getAppointmentHour();
            int appointmentDate = Integer.parseInt(
                    selectedDate.format(DateTimeFormatter.BASIC_ISO_DATE)
            );

            Patient patient = HospitalSystem.getInstance()
                    .findPatientByNationalId(patientId);

            if (patient == null) {
                patient = new Patient(
                        patientName,
                        patientId,
                        patientPhone,
                        "Unknown",
                        symptoms
                );
                HospitalSystem.getInstance().addPerson(patient);
            }

            Appointment appointment = new Appointment(
                    selectedDoctor,
                    patient,
                    appointmentDate,
                    appointmentHour
            );

            HospitalSystem.getInstance().addAppointment(appointment);
            HospitalSystem.getInstance().saveAppointmentsToFile("appointments.txt");

            showAlert(Alert.AlertType.INFORMATION, "تم الحجز", "تم حجز الموعد بنجاح.");
            clearFields();

        } catch (NumberFormatException exception) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "الرجاء إدخال وقت صحيح.");
        } catch (DoctorUnavailableException exception) {
            showAlert(Alert.AlertType.WARNING, "الطبيب غير متاح", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            showAlert(Alert.AlertType.ERROR, "خطأ", exception.getMessage());
        }
    }

    @FXML
    void handleViewAppointments(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("DisplayAppointmentsScreen.fxml")
        );
        Parent root = loader.load();

        String data = getAppointmentsAsString();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        DisplayAppointmentsController controller =
                (DisplayAppointmentsController) loader.getController();

        stage.setUserData(data);
        controller.setStage(stage);
        controller.fillFields();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private int getAppointmentHour() {
        String time = txtAppointmentTime.getText().trim();

        if (time.contains(":")) {
            String[] parts = time.split(":");
            return Integer.parseInt(parts[0]);
        }

        return Integer.parseInt(time);
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

    private void clearFields() {
        txtPatientName.clear();
        txtPatientId.clear();
        txtPatientPhone.clear();
        txtPatientAge.clear();
        txtSymptoms.clear();
        txtAppointmentTime.clear();
        dpAppointmentDate.setValue(null);
        cmbDoctors.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}