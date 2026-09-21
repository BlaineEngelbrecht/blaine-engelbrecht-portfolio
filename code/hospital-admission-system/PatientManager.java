/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalpatientadmissionsystem;

import java.util.ArrayList;
import java.util.Comparator;
/**
 *
 * @author emeris
 */
public class PatientManager {

    // ArrayList (java platform SE 8 ) 2019
    // Oracle.com
    // https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
    // Accessed: 3 September 2026
    private ArrayList<Patient> patients;
    private Ward ward;

    public PatientManager() {
        patients = new ArrayList<>();
        ward = new Ward();
    }

    public Ward getWard() {
        return ward;
    }

// Feature 1: Patient Management
    public boolean registerPatient(Patient patient) {
        if (findPatientById(patient.getPatientID()) != null) {
            System.out.println("Error: A patient with ID " + patient.getPatientID() + " already exists.");
            return false;
        }
        patients.add(patient);
        return true;
    }

    public Patient findPatientById(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientID, String firstName, String lastName,
            int age, String gender, String medicalCondition) {
        Patient p = findPatientById(patientID);
        if (p == null) {
            return false;
        }
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(medicalCondition);
        return true;
    }

    public boolean deletePatient(String patientID) {
        Patient p = findPatientById(patientID);
        if (p == null) {
            return false;
        }

// If they were an inpatient with a bed, release it first
        if (p instanceof Inpatient) {
            Inpatient ip = (Inpatient) p;
            ward.releaseBed(ip.getBedNumber());
        }
        patients.remove(p);
        return true;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("\n All Registered Patients ");
        for (Patient p : patients) {
            p.displayDetails();
            System.out.println("------------------------");
        }
    }

// Feature 2: Bed Management helpers
    public boolean allocateBedToInpatient(Inpatient patient) {
        String bedLabel = ward.findAvailableBed();
        if (bedLabel == null) {
            System.out.println("Error: No beds available.");
            return false;
        }
        patient.setBedNumber(bedLabel);
        ward.allocateBed(bedLabel, patient);
        return true;
    }

    public boolean dischargeInpatient(String patientID) {
        Patient p = findPatientById(patientID);
        if (!(p instanceof Inpatient)) {
            return false;
        }
        Inpatient ip = (Inpatient) p;
        return ward.releaseBed(ip.getBedNumber());
    }

// Feature 3: Reports
    public int getTotalRegisteredPatients() {
        return patients.size();
    }

    public double getOccupancyPercentage() {
        return ((double) ward.getOccupiedCount() / ward.getTotalBeds()) * 100;
    }

// Sorting (Feature 5 depends on this)
    public void sortBySurname() {
        patients.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByPatientId() {
        patients.sort(Comparator.comparing(Patient::getPatientID, String.CASE_INSENSITIVE_ORDER));
    }

    public ArrayList<Patient> getAllPatients() {
        return patients;
    }
}
