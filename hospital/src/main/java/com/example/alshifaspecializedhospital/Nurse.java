package com.example.alshifaspecializedhospital;

public class Nurse extends Employee {

    private String assignedRoom;
    private String department;

    public Nurse(String name, String nationalId, String phoneNumber, double salary, int workingHours, String assignedRoom, String department) {
        super(name, nationalId, phoneNumber, salary, workingHours);

        this.assignedRoom = assignedRoom;
        this.department = department;
    }

    public Nurse(String name, String nationalId, double salary, int workingHours, String assignedRoom, String department) {
        this(name, nationalId, "Not provided", salary, workingHours, assignedRoom, department);
    }

    public String getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(String assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public void print() {
        System.out.print("Nurse | ");
        super.print();
        System.out.println(" | Room=" + assignedRoom + " | Department=" + department);
    }
}