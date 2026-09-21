/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalpatientadmissionsystem;

/**
 *
 * @author emeris
 */
public class Ward {

    private static final int ROWS = 4;
    private static final int COLS = 5;
    
    // W3schools 
    // www.W3Schools.com
    // https://www.w3schools.com/java/java_arrays_multi.asp
    // Accessed: 3 Septemeber 2026

    // JDK 26 documentation 2026
    // Oracle Help Centre
    // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.ht
    // Accessed: 3 September 2026
    private Inpatient[][] beds; // holds the patient occupying each bed, or null if empty
    private String[][] bedLabels; // B01, B02, ... B20

    public Ward() {
        beds = new Inpatient[ROWS][COLS];
        bedLabels = new String[ROWS][COLS];
        int bedNum = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                bedLabels[row][col] = String.format("B%02d", bedNum);
                bedNum++;
            }
        }
    }

// Returns the bed label (e.g. "B07") of the first free bed, or null if the ward is full
    public String findAvailableBed() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col] == null) {
                    return bedLabels[row][col];
                }
            }
        }
        return null;
    }

// Allocates a specific bed label to an inpatient. Returns true if successful.
    public boolean allocateBed(String bedLabel, Inpatient patient) {
        int[] position = findPosition(bedLabel);
        if (position == null) {
            return false; // invalid bed label
        }
        int row = position[0], col = position[1];
        if (beds[row][col] != null) {
            return false; // already occupied
        }
        beds[row][col] = patient;
        return true;
    }

// Releases a bed when a patient is discharged. Returns true if successful.
    public boolean releaseBed(String bedLabel) {
        int[] position = findPosition(bedLabel);
        if (position == null) {
            return false;
        }
        int row = position[0], col = position[1];
        if (beds[row][col] == null) {
            return false; // already empty
        }
        beds[row][col] = null;
        return true;
    }

    private int[] findPosition(String bedLabel) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (bedLabels[row][col].equals(bedLabel)) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public void displayWardLayout() {
        System.out.println("\n Ward Layout");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String status = (beds[row][col] == null) ? bedLabels[row][col] : bedLabels[row][col] + "(X)";
                System.out.printf("%-8s", status);
            }
            System.out.println();
        }
        System.out.println("Legend: (X) = Occupied");
    }

    public void displayAvailableBeds() {
        System.out.println("\n Available Beds");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col] == null) {
                    System.out.println(bedLabels[row][col]);
                }
            }
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n Occupied Beds");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col] != null) {
                    System.out.println(bedLabels[row][col] + " - " + beds[row][col].getFirstName()
                            + " " + beds[row][col].getLastName());
                }
            }
        }
    }

    public int getOccupiedCount() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getTotalBeds() {
        return ROWS * COLS;
    }
}
