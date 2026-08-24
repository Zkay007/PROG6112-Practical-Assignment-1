/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Khanya
 */

import java.util.ArrayList;
import java.util.Comparator;

public class HospitalSystem {
    
     // Storeing all patients.
    private ArrayList<Patient> patients;


    private Inpatient[][] beds;

    public HospitalSystem() {

        patients = new ArrayList<>();

        beds = new Inpatient[4][5];
    }


    // REGISTER PATIENT

    public boolean registerPatient(Patient patient) {


        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }


    // SEARCH PATIENT

    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

       
        return null;
    }


    // UPDATE PATIENT

    public boolean updatePatient(String patientId,
                                 String firstName,
                                 String lastName,
                                 int age,
                                 String gender,
                                 String medicalCondition) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);

        return true;
    }


    // DELETE PATIENT

    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }


        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            if (inpatient.getBedNumber() != null) {
                releaseBed(patientId);
            }
        }

        patients.remove(patient);
        return true;
    }


    // DISPLAY ALL PATIENTS

    public void displayAllPatients() {

        if (patients.isEmpty()) {

            System.out.println("No patients are currently registered.");
            return;
        }

        for (Patient patient : patients) {
            patient.displayDetails();
        }
    }

    // ALLOCATE BED

    public boolean allocateBed(String patientId) {

        Patient patient = searchPatient(patientId);

       
        if (patient == null) {
            return false;
        }

        // Only Inpatients can receive beds.
        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        // Prevent one inpatient from having multiple beds.
        if (inpatient.getBedNumber() != null) {
            return false;
        }

        // Search for the first available bed.
        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] == null) {

                    beds[row][column] = inpatient;

                    String bedNumber =
                            "B" + String.format("%02d",
                            (row * 5) + column + 1);

                    inpatient.setBedNumber(bedNumber);

                    return true;
                }
            }
        }

       
        return false;
    }


    // RELEASE BED

    public boolean releaseBed(String patientId) {

        Patient patient = searchPatient(patientId);

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] == inpatient) {

                    beds[row][column] = null;
                    inpatient.setBedNumber(null);

                    return true;
                }
            }
        }

        return false;
    }


    // DISPLAY WARD

    public void displayWardLayout() {

        System.out.println("\n========== WARD LAYOUT ==========");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                String bedNumber =
                        "B" + String.format("%02d",
                        (row * 5) + column + 1);

                if (beds[row][column] == null) {

                    System.out.printf("%-18s", bedNumber + " [Available]");

                } else {

                    System.out.printf(
                        "%-18s",
                        bedNumber + " [Occupied]"
                    );
                }
            }

            System.out.println();
        }
    }


    // DISPLAY AVAILABLE BEDS

    public void displayAvailableBeds() {

        System.out.println("\nAvailable Beds:");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] == null) {

                    String bedNumber =
                            "B" + String.format("%02d",
                            (row * 5) + column + 1);

                    System.out.println(bedNumber);
                }
            }
        }
    }


    // DISPLAY OCCUPIED BEDS

    public void displayOccupiedBeds() {

        System.out.println("\nOccupied Beds:");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] != null) {

                    String bedNumber =
                            "B" + String.format("%02d",
                            (row * 5) + column + 1);

                    System.out.println(
                            bedNumber
                            + " - "
                            + beds[row][column].getFirstName()
                            + " "
                            + beds[row][column].getLastName()
                    );
                }
            }
        }
    }


    // REPORT METHODS

    public int getTotalPatients() {
        return patients.size();
    }

    public int getOccupiedBedCount() {

        int count = 0;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] != null) {
                    count++;
                }
            }
        }

        return count;
    }

    public double getOccupancyPercentage() {

        return (getOccupiedBedCount() / 20.0) * 100;
    }


    // SORT BY SURNAME


    public void sortPatientsBySurname() {

        patients.sort(
                Comparator.comparing(Patient::getLastName)
        );
    }


    // SORT BY PATIENT ID

    public void sortPatientsById() {

        patients.sort(
                Comparator.comparing(Patient::getPatientId)
        );
    }


    public ArrayList<Patient> getPatients() {
        return patients;
    }   
}
