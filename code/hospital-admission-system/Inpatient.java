/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalpatientadmissionsystem;

/**
 *
 * @author emeris
 */
// Oracle
// docs.oracle.com
// https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
// Accessed: 3 September 2026
public class Inpatient extends Patient {

    private int wardNumber;
    private String bedNumber;

    public Inpatient(String patientID, String firstName, String lastName, int age,
            String gender, String medicalCondition,
            int wardNumber, String bedNumber) {
        
        // The Java Tutorials
        // docs.orcle.com
        // https://docs.oracle.com/javase/tutorial/java/IandI/super.html
        // Accessed: 3 September 2026
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
}
