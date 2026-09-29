package com.example.alshifaspecializedhospital;

public class Appointment implements Printable {

    private static int nextAppointmentId = 0;

    private final int id;
    private final Doctor doctor;
    private final Patient patient;
    private final int appointmentDate;
    private final int appointmentHour;

    private AppointmentStatus status;

    public Appointment(Doctor doctor, Patient patient, int appointmentDate, int appointmentHour) {
        if (doctor == null || patient == null) {
            throw new IllegalArgumentException("الطبيب والمريض مطلوبان.");
        }

        if (appointmentHour < 0 || appointmentHour > 23) {
            throw new IllegalArgumentException("الساعة يجب أن تكون من 0 إلى 23.");
        }

        this.id = ++nextAppointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentHour = appointmentHour;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public Appointment(int id, Doctor doctor, Patient patient, int appointmentDate, int appointmentHour, AppointmentStatus status) {
        if (doctor == null || patient == null) {
            throw new IllegalArgumentException("الطبيب والمريض مطلوبان.");
        }

        if (appointmentHour < 0 || appointmentHour > 23) {
            throw new IllegalArgumentException("الساعة يجب أن تكون من 0 إلى 23.");
        }

        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentHour = appointmentHour;
        this.status = status;

        if (id > nextAppointmentId) {
            nextAppointmentId = id;
        }
    }

    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getAppointmentDate() {
        return appointmentDate;
    }

    public int getAppointmentHour() {
        return appointmentHour;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void startAppointment() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("يمكن بدء الموعد المجدول فقط.");
        }
        status = AppointmentStatus.IN_PROGRESS;
    }

    public void completeAppointment() {
        if (status != AppointmentStatus.IN_PROGRESS) {
            throw new IllegalStateException("يجب بدء الموعد قبل إنهائه.");
        }
        status = AppointmentStatus.COMPLETED;
    }
    public void cancelAppointment() {
        if (status == AppointmentStatus.COMPLETED || status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("لا يمكن إلغاء هذا الموعد.");
        }
        status = AppointmentStatus.CANCELLED;
    }

    @Override
    public void print() {
        System.out.println("Appointment | ID=" + id + " | Doctor=" + doctor.getName() + " | Patient=" + patient.getName() + " | Date=" + appointmentDate + " | Hour=" + appointmentHour + " | Status=" + status);
    }
}