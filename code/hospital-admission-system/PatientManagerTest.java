/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.hospitalpatientadmissionsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author emeris
 */
public class PatientManagerTest {

private PatientManager manager;

@BeforeEach
void setUp() {
manager = new PatientManager();
}

// CRUD Operation Tests 

@Test
void testRegisterPatient() {
Patient p = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
assertTrue(manager.registerPatient(p));
assertEquals(1, manager.getTotalRegisteredPatients());
}

@Test
void testSearchForPatient() {
Patient p = new Patient("P002", "Jane", "Doe", 25, "Female", "Checkup", PatientCategory.OUTPATIENT);
manager.registerPatient(p);
Patient found = manager.findPatientById("P002");
assertNotNull(found);
assertEquals("Jane", found.getFirstName());
}

@Test
void testUpdatePatientDetails() {
Patient p = new Patient("P003", "Sam", "Green", 40, "Male", "Cold", PatientCategory.OUTPATIENT);
manager.registerPatient(p);
boolean updated = manager.updatePatient("P003", "Sam", "Greenwood", 41, "Male", "Recovered");
assertTrue(updated);
assertEquals("Greenwood", manager.findPatientById("P003").getLastName());
assertEquals(41, manager.findPatientById("P003").getAge());
}

@Test
void testDeletePatient() {
Patient p = new Patient("P004", "Alex", "Brown", 35, "Male", "Fracture", PatientCategory.OUTPATIENT);
manager.registerPatient(p);
assertTrue(manager.deletePatient("P004"));
assertNull(manager.findPatientById("P004"));
}

// Bed Management Tests

@Test
void testAllocateBed() {
Inpatient ip = new Inpatient("P005", "Kim", "White", 50, "Female", "Surgery", 1, "");
manager.registerPatient(ip);
assertTrue(manager.allocateBedToInpatient(ip));
assertNotNull(ip.getBedNumber());
assertFalse(ip.getBedNumber().isEmpty());
}

@Test
void testReleaseBed() {
Inpatient ip = new Inpatient("P006", "Lee", "Black", 45, "Male", "Observation", 1, "");
manager.registerPatient(ip);
manager.allocateBedToInpatient(ip);
assertTrue(manager.dischargeInpatient("P006"));
}

// Validation and Boundary Test

@Test
void testPreventDuplicatePatientIDs() {
Patient p1 = new Patient("P007", "Tom", "Grey", 28, "Male", "Allergy", PatientCategory.OUTPATIENT);
Patient p2 = new Patient("P007", "Different", "Person", 60, "Female", "Other", PatientCategory.EMERGENCY);
assertTrue(manager.registerPatient(p1));
assertFalse(manager.registerPatient(p2)); // should be rejected, ID already exists
assertEquals(1, manager.getTotalRegisteredPatients());
}

@Test
void testPreventAllocatingOccupiedBed() {
Inpatient ip1 = new Inpatient("P008", "Nina", "Blue", 33, "Female", "Injury", 1, "");
manager.registerPatient(ip1);
manager.allocateBedToInpatient(ip1);
String occupiedBed = ip1.getBedNumber();

// Try to directly allocate the same bed to a second inpatient via the Ward
Inpatient ip2 = new Inpatient("P009", "Omar", "Silver", 29, "Male", "Fever", 1, "");
boolean result = manager.getWard().allocateBed(occupiedBed, ip2);
assertFalse(result); // should fail, bed already taken
}

@Test
void testPreventBedAllocationWhenWardFull() {
// Fill all 20 beds
for (int i = 1; i <= 20; i++) {
Inpatient ip = new Inpatient("F" + i, "First" + i, "Last" + i, 30, "Male", "Condition", 1, "");
manager.registerPatient(ip);
manager.allocateBedToInpatient(ip);
}
// 21st inpatient should fail to get a bed
Inpatient overflow = new Inpatient("F21", "Extra", "Patient", 30, "Male", "Condition", 1, "");
manager.registerPatient(overflow);
assertFalse(manager.allocateBedToInpatient(overflow));
}

@Test
void testSortPatientsBySurname() {
manager.registerPatient(new Patient("S1", "A", "Zeta", 20, "Male", "X", PatientCategory.OUTPATIENT));
manager.registerPatient(new Patient("S2", "B", "Alpha", 20, "Male", "X", PatientCategory.OUTPATIENT));
manager.sortBySurname();
assertEquals("Alpha", manager.getAllPatients().get(0).getLastName());
assertEquals("Zeta", manager.getAllPatients().get(1).getLastName());
}

@Test
void testSortPatientsByPatientId() {
manager.registerPatient(new Patient("S9", "A", "One", 20, "Male", "X", PatientCategory.OUTPATIENT));
manager.registerPatient(new Patient("S2", "B", "Two", 20, "Male", "X", PatientCategory.OUTPATIENT));
manager.sortByPatientId();
assertEquals("S2", manager.getAllPatients().get(0).getPatientID());
assertEquals("S9", manager.getAllPatients().get(1).getPatientID());
}
}


