import database.PersonDAO;
import model.*;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        PersonDAO dao = new PersonDAO();

        // ============================
        // INSERT PATIENT
        // ============================
        Patient p = new Patient(1, "Test Patient", 20, "87000000000", "O+");
        dao.insertPatient(p);

        // ============================
        // INSERT DOCTOR
        // ============================
        Doctor d = new Doctor(101, "Test Doctor", 40, "87001112233", "Surgeon", 10);
        dao.insertDoctor(d);

        // ============================
        // SELECT ALL PEOPLE
        // ============================
        System.out.println("\n===== ALL PEOPLE =====");

        List<Person> people = dao.getAllPeople();
        for (Person person : people) {
            System.out.println(person);
        }

        // ============================
        // SEARCH BY NAME
        // ============================
        System.out.println("\n===== SEARCH BY NAME 'Test' =====");

        List<Person> searchByName = dao.searchByName("Test");
        for (Person person : searchByName) {
            System.out.println(person);
        }

        // ============================
        // SEARCH BY AGE
        // ============================
        System.out.println("\n===== SEARCH BY AGE 20 =====");

        List<Person> searchByAge = dao.searchByAge(20);
        for (Person person : searchByAge) {
            System.out.println(person);
        }

        // ============================
        // UPDATE PATIENT
        // ============================
        System.out.println("\n===== UPDATE PATIENT =====");

        Patient updatedPatient = new Patient(1, "Updated Patient", 25, "87009999999", "A+");
        dao.updatePatient(updatedPatient);

        // ============================
        // UPDATE DOCTOR
        // ============================
        System.out.println("\n===== UPDATE DOCTOR =====");

        Doctor updatedDoctor = new Doctor(101, "Updated Doctor", 45, "87008888888", "Cardiologist", 15);
        dao.updateDoctor(updatedDoctor);

        // ============================
        // SELECT AFTER UPDATE
        // ============================
        System.out.println("\n===== AFTER UPDATE =====");

        List<Person> updatedList = dao.getAllPeople();
        for (Person person : updatedList) {
            System.out.println(person);
        }

        // ============================
        // DELETE PERSON
        // ============================
        System.out.println("\n===== DELETE PERSON ID 1 =====");

        dao.deletePerson(1);

        // ============================
        // SELECT AFTER DELETE
        // ============================
        System.out.println("\n===== AFTER DELETE =====");

        List<Person> afterDelete = dao.getAllPeople();
        for (Person person : afterDelete) {
            System.out.println(person);
        }

        System.out.println("\n===== TEST FINISHED =====");
    }
}
