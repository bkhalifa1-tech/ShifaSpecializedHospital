package com.example.alshifaspecializedhospital;

public class Patient extends Person {

    private String bloodType;
    private final MedicalRecord medicalRecord;

    public Patient(String name, String nationalId) {
        this(name, nationalId, "Not provided", "Unknown", "No diagnosis");
    }

    public Patient(String name, String nationalId, String phoneNumber, String bloodType, String diagnosis) {
        super(name, nationalId, phoneNumber);
        this.bloodType = bloodType;
        this.medicalRecord = new MedicalRecord(diagnosis);
    }

    public Patient(String name, String nationalId, String bloodType, String diagnosis) {
        this(name, nationalId, "Not provided", bloodType, diagnosis);
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    @Override
    public void print() {
        System.out.print("Patient | ");
        super.print();
        System.out.println(" | Blood Type=" + bloodType + " | Diagnosis=" + medicalRecord.getDiagnosis());
    }
}
