package menu;

import exception.InvalidDataException;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Person;


import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public MenuManager() {
        seedData();
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== HOSPITAL MENU (Assignment 3) =====");
        System.out.println("1. Add Patient");
        System.out.println("2. Add Doctor");
        System.out.println("3. View All People");
        System.out.println("4. Polymorphism Demo (work())");
        System.out.println("5. View Only Patients (instanceof)");
        System.out.println("6. View Only Doctors (instanceof)");
        System.out.println("7. Add Appointment");
        System.out.println("8. View Appointments");
        System.out.println("9. Cancel Appointment by ID");
        System.out.println("10. Reschedule Appointment by ID");
        System.out.println("0. Exit");
    }

    @Override
    public void run() {
        boolean run = true;

        while (run) {
            showMenu();
            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        addDoctor();
                        break;
                    case 3:
                        viewAllPeople();
                        break;
                    case 4:
                        demoPolymorphism();
                        break;
                    case 5:
                        viewOnlyPatients();
                        break;
                    case 6:
                        viewOnlyDoctors();
                        break;
                    case 7:
                        addAppointment();
                        break;
                    case 8:
                        viewAppointments();
                        break;
                    case 9:
                        cancelAppointmentById();
                        break;
                    case 10:
                        rescheduleAppointmentById();
                        break;
                    case 0:
                        run = false;
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Wrong option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (InvalidDataException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        sc.close();
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Enter number: ");
            sc.nextLine();
        }
        int x = sc.nextInt();
        sc.nextLine();
        return x;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    // ------- seed data -------
    private void seedData() {
        people.add(new Patient(2, "Mirambaeve T.", 17, "+77000000009", "2+"));
        people.add(new Doctor(3, "Dr. Maqsat", 37, "+77000000006", "Surgeon", 10));
        appointments.add(new Appointment(5001, "Mirambaeve T.", "Dr. Maqsat", "20.03.2025"));
    }

    // ------- people -------
    private void addPatient() {
        System.out.println("\n--- ADD PATIENT ---");
        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String blood = readLine("Blood type: ");

        Person p = new Patient(id, name, age, phone, blood);
        people.add(p);

        System.out.println("Added: " + p);
    }

    private void addDoctor() {
        System.out.println("\n--- ADD DOCTOR ---");
        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String spec = readLine("Specialization: ");
        int exp = readInt("Experience years: ");

        Person d = new Doctor(id, name, age, phone, spec, exp);
        people.add(d);

        System.out.println("Added: " + d);
    }

    private void viewAllPeople() {
        System.out.println("\n--- ALL PEOPLE ---");
        if (people.isEmpty()) {
            System.out.println("No people.");
            return;
        }
        for (Person p : people) {
            System.out.println(p);
        }
    }

    private void demoPolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMO ---");
        for (Person p : people) {
            System.out.print(p.getRole() + ": ");
            p.work();
        }
    }

    private void viewOnlyPatients() {
        System.out.println("\n--- ONLY PATIENTS ---");
        for (Person p : people) {
            if (p instanceof Patient) {
                Patient x = (Patient) p;
                System.out.println(x);
                System.out.println("   Minor=" + x.isMinor() + ", Category=" + x.getAgeCategory());
            }
        }
    }

    private void viewOnlyDoctors() {
        System.out.println("\n--- ONLY DOCTORS ---");
        for (Person p : people) {
            if (p instanceof Doctor) {
                Doctor x = (Doctor) p;
                System.out.println(x);
                System.out.println("   Experienced=" + x.isExperienced() + ", Surgery=" + x.canPerformSurgery());
            }
        }
    }

    private void addAppointment() {
        System.out.println("\n--- ADD APPOINTMENT ---");
        int id = readInt("Appointment ID: ");
        String patient = readLine("Patient name: ");
        String doctor = readLine("Doctor name: ");
        String date = readLine("Date: ");

        Appointment a = new Appointment(id, patient, doctor, date);
        appointments.add(a);

        System.out.println("Added: " + a);
    }

    private void viewAppointments() {
        System.out.println("\n--- APPOINTMENTS ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments.");
            return;
        }
        for (Appointment a : appointments) {
            System.out.println(a);
        }
    }

    private Appointment findAppointmentById(int id) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == id) return a;
        }
        return null;
    }

    private void cancelAppointmentById() {
        System.out.println("\n--- CANCEL APPOINTMENT ---");
        int id = readInt("Enter appointment ID: ");
        Appointment a = findAppointmentById(id);

        if (a == null) {
            System.out.println("Not found.");
            return;
        }

        a.cancel();
        System.out.println("Cancelled: " + a);
    }

    private void rescheduleAppointmentById() {
        System.out.println("\n--- RESCHEDULE APPOINTMENT ---");
        int id = readInt("Enter appointment ID: ");
        Appointment a = findAppointmentById(id);

        if (a == null) {
            System.out.println("Not found.");
            return;
        }

        String newDate = readLine("New date: ");
        a.reschedule(newDate);
        System.out.println("Rescheduled: " + a);
    }
}