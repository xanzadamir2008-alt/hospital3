package model;

import exception.InvalidDataException;

public class Patient extends Person {

    private String bloodType;

    public Patient(int id, String name, int age, String phone, String bloodType) {
        super(id, name, age, phone);
        setBloodType(bloodType);
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        if (bloodType == null || bloodType.trim().isEmpty()) {
            throw new InvalidDataException("Blood type cannot be empty");
        }
        this.bloodType = bloodType.trim();
    }

    public boolean isMinor() {
        return age < 18;
    }

    public String getAgeCategory() {
        if (age < 18) return "Child";
        if (age < 60) return "Adult";
        return "Senior";
    }

    @Override
    public void work() {
        System.out.println(name + " is getting treatment.");
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    @Override
    public String toString() {
        return getRole() + " {" + basicInfo() + ", BloodType=" + bloodType + "}";
    }
}