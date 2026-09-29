package com.example.alshifaspecializedhospital;

public class Doctor extends Employee {

    private String medicalSpecialty;
    private double examinationFee;

    public Doctor(String name, String nationalId, String phoneNumber, double salary, int workingHours, String medicalSpecialty, double examinationFee) {
        super(name, nationalId, phoneNumber, salary, workingHours);

        this.medicalSpecialty = medicalSpecialty;
        this.examinationFee = examinationFee;
    }

    public Doctor(String name, String nationalId, double salary, int workingHours, String medicalSpecialty, double examinationFee) {
        this(name, nationalId, "Not provided", salary, workingHours, medicalSpecialty, examinationFee);
    }

    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public void setMedicalSpecialty(String medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }

    public double getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(double examinationFee) {
        this.examinationFee = examinationFee;
    }

    @Override
    public void print() {
        System.out.print("Doctor | ");
        super.print();
        System.out.println(" | Specialty=" + medicalSpecialty + " | Examination Fee=" + examinationFee);
    }

    @Override
    public String toString() {
        return "د. " + getName() + " (" + medicalSpecialty + ")";
    }
}
