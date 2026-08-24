/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Khanya
 */

import java.util.Scanner;

public class HospitalAdmissionSystem {

 public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        HospitalSystem hospital = new HospitalSystem();

        int option;

        do {

            System.out.println("\n======================================");
            System.out.println("      MEDICARE HOSPITAL SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Ward Report");
            System.out.println("12. Sort Patients By Surname");
            System.out.println("13. Sort Patients By ID");
            System.out.println("0. Exit");
            System.out.println("======================================");

            System.out.print("Enter your option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    registerPatient(scanner, hospital);
                    break;

                case 2:
                    searchPatient(scanner, hospital);
                    break;

                case 3:
                    updatePatient(scanner, hospital);
                    break;

                case 4:
                    deletePatient(scanner, hospital);
                    break;

                case 5:
                    hospital.displayAllPatients();
                    break;

                case 6:
                    allocateBed(scanner, hospital);
                    break;

                case 7:
                    releaseBed(scanner, hospital);
                    break;

                case 8:
                    hospital.displayWardLayout();
                    break;

                case 9:
                    hospital.displayAvailableBeds();
                    break;

                case 10:
                    hospital.displayOccupiedBeds();
                    break;

                case 11:
                    displayReport(hospital);
                    break;

                case 12:
                    hospital.sortPatientsBySurname();
                    hospital.displayAllPatients();
                    break;

                case 13:
                    hospital.sortPatientsById();
                    hospital.displayAllPatients();
                    break;

                case 0:
                    System.out.println("Thank you for using the system.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);

        scanner.close();
    }

   
    // REGISTER
 
    public static void registerPatient(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Patient ID: ");
        String id = scanner.nextLine();

        // Check duplicates immediately.
        if (hospital.searchPatient(id) != null) {

            System.out.println("ERROR: Patient ID already exists.");
            return;
        }

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (age <= 0) {

            System.out.println("ERROR: Age must be greater than zero.");
            return;
        }

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Medical Condition: ");
        String condition = scanner.nextLine();

        System.out.println("\nPatient Category");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");

        System.out.print("Choose category: ");

        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        Patient patient;

        switch (categoryChoice) {

            case 1:

                patient = new Inpatient(
                        id,
                        firstName,
                        lastName,
                        age,
                        gender,
                        condition,
                        1
                );

                break;

            case 2:

                patient = new Patient(
                        id,
                        firstName,
                        lastName,
                        age,
                        gender,
                        condition,
                        PatientCategory.OUTPATIENT
                );

                break;

            case 3:

                patient = new Patient(
                        id,
                        firstName,
                        lastName,
                        age,
                        gender,
                        condition,
                        PatientCategory.EMERGENCY
                );

                break;

            default:

                System.out.println("Invalid category.");
                return;
        }

        if (hospital.registerPatient(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient could not be registered.");
        }
    }

    
    // SEARCH

    public static void searchPatient(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Enter Patient ID: ");

        String id = scanner.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {

            System.out.println("Patient not found.");

        } else {

            patient.displayDetails();
        }
    }

    
    // UPDATE
  
    public static void updatePatient(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Enter Patient ID: ");

        String id = scanner.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {

            System.out.println("Patient not found.");
            return;
        }

        System.out.print("New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (age <= 0) {

            System.out.println("Invalid age.");
            return;
        }

        System.out.print("New Gender: ");
        String gender = scanner.nextLine();

        System.out.print("New Medical Condition: ");
        String condition = scanner.nextLine();

        boolean updated = hospital.updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition
        );

        if (updated) {
            System.out.println("Patient updated successfully.");
        }
    }


    // DELETE

    public static void deletePatient(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Enter Patient ID: ");

        String id = scanner.nextLine();

        if (hospital.deletePatient(id)) {

            System.out.println("Patient deleted successfully.");

        } else {

            System.out.println("Patient not found.");
        }
    }

 
    // ALLOCATE BED

    public static void allocateBed(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Enter Inpatient ID: ");

        String id = scanner.nextLine();

        if (hospital.allocateBed(id)) {

            System.out.println("Bed allocated successfully.");

            Patient patient = hospital.searchPatient(id);

            if (patient instanceof Inpatient) {

                Inpatient inpatient = (Inpatient) patient;

                System.out.println(
                        "Allocated bed: "
                        + inpatient.getBedNumber()
                );
            }

        } else {

            System.out.println(
                    "Bed could not be allocated."
            );
        }
    }


    // RELEASE BED

    public static void releaseBed(
            Scanner scanner,
            HospitalSystem hospital) {

        System.out.print("Enter Patient ID: ");

        String id = scanner.nextLine();

        if (hospital.releaseBed(id)) {

            System.out.println("Bed released successfully.");

        } else {

            System.out.println(
                    "No allocated bed was found for this patient."
            );
        }
    }


    // REPORT

    public static void displayReport(HospitalSystem hospital) {

        System.out.println("\n================================");
        System.out.println("          WARD REPORT");
        System.out.println("================================");

        System.out.println(
                "Total Registered Patients: "
                + hospital.getTotalPatients()
        );

        System.out.println(
                "Occupied Beds: "
                + hospital.getOccupiedBedCount()
        );

        System.out.println(
                "Available Beds: "
                + (20 - hospital.getOccupiedBedCount())
        );

        System.out.printf(
                "Occupancy Percentage: %.2f%%\n",
                hospital.getOccupancyPercentage()
        );

        System.out.println("================================");
    }
}
