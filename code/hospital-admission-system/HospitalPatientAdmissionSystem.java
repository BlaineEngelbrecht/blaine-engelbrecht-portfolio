/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalpatientadmissionsystem;

import java.util.Scanner;
/**
 *
 * @author emeris
 */
public class HospitalPatientAdmissionSystem {

    private static PatientManager manager = new PatientManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerPatientMenu();
                    break;
                case 2:
                    searchPatientMenu();
                    break;
                case 3:
                    updatePatientMenu();
                    break;
                case 4:
                    deletePatientMenu();
                    break;
                case 5:
                    manager.displayAllPatients();
                    break;
                case 6:
                    manager.getWard().displayWardLayout();
                    break;
                case 7:
                    manager.getWard().displayAvailableBeds();
                    break;
                case 8:
                    manager.getWard().displayOccupiedBeds();
                    break;
                case 9:
                    displayReports();
                    break;
                case 10:
                    sortMenu();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n===== MediCare Hospital Patient Admission System =====");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search for Patient");
        System.out.println("3. Update Patient Details");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Display Ward Layout");
        System.out.println("7. Display Available Beds");
        System.out.println("8. Display Occupied Beds");
        System.out.println("9. Display Reports");
        System.out.println("10. Sort Patients");
        System.out.println("0. Exit");
    }

// Menu handlers
    private static void registerPatientMenu() {
        System.out.println("\n--- Register New Patient ---");
        String id = getStringInput("Patient ID: ");
        String firstName = getStringInput("First Name: ");
        String lastName = getStringInput("Last Name: ");
        int age = getIntInput("Age: ");
        String gender = getStringInput("Gender: ");
        String condition = getStringInput("Medical Condition: ");

        System.out.println("Category (1=Inpatient, 2=Outpatient, 3=Emergency): ");
        int catChoice = getIntInput("Choice: ");

        if (catChoice == 1) {
            Inpatient patient = new Inpatient(id, firstName, lastName, age, gender, condition, 1, "");
            if (manager.registerPatient(patient)) {
                if (manager.allocateBedToInpatient(patient)) {
                    System.out.println("Inpatient registered and assigned bed " + patient.getBedNumber());
                } else {
                    System.out.println("Patient registered, but no bed could be assigned.");
                }
            }
        } else {
            PatientCategory category = (catChoice == 2) ? PatientCategory.OUTPATIENT : PatientCategory.EMERGENCY;
            Patient patient = new Patient(id, firstName, lastName, age, gender, condition, category);
            if (manager.registerPatient(patient)) {
                System.out.println("Patient registered successfully.");
            }
        }
    }

    private static void searchPatientMenu() {
        String id = getStringInput("Enter Patient ID to search: ");
        Patient p = manager.findPatientById(id);
        if (p == null) {
            System.out.println("No patient found with ID " + id);
        } else {
            p.displayDetails();
        }
    }

    private static void updatePatientMenu() {
        String id = getStringInput("Enter Patient ID to update: ");
        if (manager.findPatientById(id) == null) {
            System.out.println("No patient found with ID " + id);
            return;
        }
        String firstName = getStringInput("New First Name: ");
        String lastName = getStringInput("New Last Name: ");
        int age = getIntInput("New Age: ");
        String gender = getStringInput("New Gender: ");
        String condition = getStringInput("New Medical Condition: ");

        if (manager.updatePatient(id, firstName, lastName, age, gender, condition)) {
            System.out.println("Patient updated successfully.");
        }
    }

    private static void deletePatientMenu() {
        String id = getStringInput("Enter Patient ID to delete: ");
        if (manager.deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("No patient found with ID " + id);
        }
    }

    private static void displayReports() {
        System.out.println("\n--- Reports ---");
        manager.displayAllPatients();
        manager.getWard().displayAvailableBeds();
        manager.getWard().displayOccupiedBeds();
        System.out.println("Total registered patients: " + manager.getTotalRegisteredPatients());
        System.out.println("Total occupied beds: " + manager.getWard().getOccupiedCount());
        System.out.printf("Ward occupancy: %.2f%%\n", manager.getOccupancyPercentage());
    }

    private static void sortMenu() {
        System.out.println("Sort by: 1=Surname, 2=Patient ID");
        int choice = getIntInput("Choice: ");
        if (choice == 1) {
            manager.sortBySurname();
        } else {
            manager.sortByPatientId();
        }
        manager.displayAllPatients();
    }

// ---------- Input helpers ----------
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            System.out.print(prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear the leftover newline
        return value;
    }
}
