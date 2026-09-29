package com.example.alshifaspecializedhospital;

public abstract class Employee extends Person {

    private double salary;
    private int workingHours;

    public Employee(String name, String nationalId, String phoneNumber, double salary, int workingHours) {
        super(name, nationalId, phoneNumber);
        this.salary = salary;
        this.workingHours = workingHours;
    }

    public Employee(String name, String nationalId, double salary, int workingHours) {
        this(name, nationalId, "Not provided",salary, workingHours);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public void print() {
        super.print();
        System.out.print(" | Salary=" + salary + " | Working Hours=" + workingHours);
    }
}
