package com.example.alshifaspecializedhospital;

public class MedicalRecord implements Printable {

    private String diagnosis;

    public MedicalRecord(String diagnosis) {
        setDiagnosis(diagnosis);
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {

            this.diagnosis = diagnosis;
        }

@Override
    public void print() {
        System.out.println("Diagnosis: " + diagnosis);
    }


    @Override
    public String toString() {
        return diagnosis;
    }
}
