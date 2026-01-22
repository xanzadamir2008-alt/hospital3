package menu;

import database.PersonDAO;
import exception.InvalidDataException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private PersonDAO personDAO = new PersonDAO();

    public MenuManager() {
        seedData();
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== HOSPITAL MENU (Week 6 + Week 7) =====");
        System.out.println("1. Add Patient (DB)");
        System.out.println("2. Add Doctor (DB)");
        System.out.println("3. View All People (ArrayList)");
        System.out.println("4. Polymorphism Demo (work())");
        System.out.println("5. View Patients (DB)");
        System.out.println("6. View Doctors (DB)");
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
                    case 1 -> addPatient();
                    case 2 -> addDoctor();
                    case 3 -> viewAllPeople();
                    case 4 -> demoPolymorphism();
                    case 5 -> viewPatientsFromDB();
                    case 6 -> viewDoctorsFromDB();
                    case 7 -> addAppointment();
                    case 8 -> viewAppointments();
                    case 9 -> cancelAppointmentById();
                    case 10 -> rescheduleAppointmentById();
                    case 0 -> {
                        run = false;
                        System.out.println("Bye!");
                    }
                    default -> System.out.println("Wrong option.");
                }
            } catch (IllegalArgumentException | InvalidDataException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // ---------- helpers ----------

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Enter number: ");
            sc.next();
        }
        int x = sc.nextInt();
        sc.nextLine();
        return x;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private void seedData() {
        people.add(new Patient(2, "Mirambaeva T.", 17, "+77000000007", "2+"));
        people.add(new Doctor(3, "Dr. Maqsat", 37, "+77000000006", "Surgeon", 10));
        appointments.add(new Appointment(5007, "Mirambaeva T.", "Dr. Maqsat", "20.03.2025"));
    }

    // ---------- people ----------

    private void addPatient() {
        System.out.println("\n--- ADD PATIENT ---");

        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String blood = readLine("Blood type: ");

        Patient patient = new Patient(id, name, age, phone, blood);

        people.add(patient);                 // Week 6
        personDAO.insertPatient(patient);    // Week 7

        System.out.println("Patient saved to DB: " + patient);
    }

    private void addDoctor() {
        System.out.println("\n--- ADD DOCTOR ---");

        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String spec = readLine("Specialization: ");
        int exp = readInt("Experience years: ");

        Doctor doctor = new Doctor(id, name, age, phone, spec, exp);

        people.add(doctor);                 // Week 6
        personDAO.insertDoctor(doctor


        );     // Week 7

        System.out.println("Doctor saved to DB: " + doctor);
    }

    private void viewAllPeople() {
        System.out.println("\n--- ALL PEOPLE (ArrayList) ---");
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

    private void viewPatientsFromDB() {
        System.out.println("\n--- PATIENTS FROM DATABASE ---");
        for (Patient p : personDAO.getAllPatients()) {
            System.out.println(p);
        }
    }

    private void viewDoctorsFromDB() {
        System.out.println("\n--- DOCTORS FROM DATABASE ---");
        for (Doctor d : personDAO.getAllDoctors()) {
            System.out.println(d);
        }
    }

    // ---------- appointments ----------

    private void addAppointment() {
        System.out.println("\n--- ADD APPOINTMENT ---");
        int id = readInt("Appointment ID: ");
        String patient = readLine("Patient name: ");
        String doctor = readLine("Doctor name: ");
        String date = readLine("Date: ");

        appointments.add(new Appointment(id, patient, doctor, date));
    }

    private void viewAppointments() {
        System.out.println("\n--- APPOINTMENTS ---");
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
        int id = readInt("Appointment ID: ");
        Appointment a = findAppointmentById(id);
        if (a != null) a.cancel();
    }

    private void rescheduleAppointmentById() {
        int id = readInt("Appointment ID: ");
        Appointment a = findAppointmentById(id);
        if (a != null) a.reschedule(readLine("New date: "));
    }
}
