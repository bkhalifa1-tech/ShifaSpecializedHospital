package com.example.alshifaspecializedhospital;

public abstract class Person implements Printable {

    private static int nextPersonId = 0;

    private final int id;
    private String name;
    private String nationalId;
    private String phoneNumber;

    public Person(String name, String nationalId, String phoneNumber) {
        this.id = ++nextPersonId;
        this.name = name;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }

    public Person(String name, String nationalId) {
        this(name, nationalId, "Not provided");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void print() {
        System.out.print("ID=" + id + " | Name=" + name + " | National ID=" + nationalId + " | Phone=" + phoneNumber);
    }
}
