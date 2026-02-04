package menu;

import database.PersonDAO;
import model.*;

import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {

    private Scanner sc = new Scanner(System.in);
    private PersonDAO personDAO = new PersonDAO();

    @Override
    public void showMenu() {
        System.out.println("\n===== HOSPITAL MENU (Week 9 - JDBC) =====");
        System.out.println("1. Add Patient");
        System.out.println("2. Add Doctor");
        System.out.println("3. View All People");
        System.out.println("4. Update Person");
        System.out.println("5. Delete Person");
        System.out.println("6. Search by Name");
        System.out.println("7. Search by Age");
        System.out.println("0. Exit");
    }

    @Override
    public void run() {

        boolean run = true;

        while (run) {
            showMenu();
            int choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> addDoctor();
                case 3 -> viewAllPeople();
                case 4 -> updatePerson();
                case 5 -> deletePerson();
                case 6 -> searchByName();
                case 7 -> searchByAge();
                case 0 -> {
                    run = false;
                    System.out.println("Bye!");
                }
                default -> System.out.println("Wrong option.");
            }
        }
    }

    // ===============================
    // ADD
    // ===============================

    private void addPatient() {
        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String blood = readLine("Blood type: ");

        Patient patient = new Patient(id, name, age, phone, blood);
        personDAO.insertPatient(patient);

        System.out.println("Patient saved to DB.");
    }

    private void addDoctor() {
        int id = readInt("ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String spec = readLine("Specialization: ");
        int exp = readInt("Experience years: ");

        Doctor doctor = new Doctor(id, name, age, phone, spec, exp);
        personDAO.insertDoctor(doctor);

        System.out.println("Doctor saved to DB.");
    }

    // ===============================
    // VIEW
    // ===============================

    private void viewAllPeople() {
        List<Person> list = personDAO.getAllPeople();

        System.out.println("\n--- PEOPLE FROM DATABASE ---");

        if (list.isEmpty()) {
            System.out.println("No data.");
        } else {
            for (Person p : list) {
                System.out.println(p);
            }
        }
    }

    // ===============================
    // UPDATE
    // ===============================

    private void updatePerson() {

        int id = readInt("Enter person ID to update: ");

        Person existing = personDAO.getPersonById(id);

        if (existing == null) {
            System.out.println("Person not found.");
            return;
        }

        System.out.println("Current: " + existing);

        String name = readLine("New Name: ");
        int age = readInt("New Age: ");
        String phone = readLine("New Phone: ");

        if (existing instanceof Patient) {

            String blood = readLine("New Blood type: ");
            Patient updated = new Patient(id, name, age, phone, blood);
            personDAO.updatePatient(updated);

        } else if (existing instanceof Doctor) {

            String spec = readLine("New Specialization: ");
            int exp = readInt("New Experience: ");
            Doctor updated = new Doctor(id, name, age, phone, spec, exp);
            personDAO.updateDoctor(updated);
        }

        System.out.println("Updated successfully.");
    }

    // ===============================
    // DELETE
    // ===============================

    private void deletePerson() {

        int id = readInt("Enter person ID to delete: ");
        personDAO.deletePerson(id);

        System.out.println("Deleted (if existed).");
    }

    // ===============================
    // SEARCH
    // ===============================

    private void searchByName() {

        String name = readLine("Enter name: ");
        List<Person> results = personDAO.searchByName(name);

        System.out.println("\n--- SEARCH RESULTS ---");

        for (Person p : results) {
            System.out.println(p);
        }
    }

    private void searchByAge() {

        int age = readInt("Enter age: ");
        List<Person> results = personDAO.searchByAge(age);

        System.out.println("\n--- SEARCH RESULTS ---");

        for (Person p : results) {
            System.out.println(p);
        }
    }

    // ===============================
    // HELPERS
    // ===============================

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
}
