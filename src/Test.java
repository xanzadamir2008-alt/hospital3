import database.PersonDAO;
import model.Doctor;
import model.Patient;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        PersonDAO dao = new PersonDAO();

        Patient p = new Patient(1, "Test Patient", 20, "87000000000", "O+");
        dao.insertPatient(p);

        List<Patient> patients = dao.getAllPatients();
        for (Patient x : patients) {
            System.out.println(x);
        }

        Doctor d = new Doctor(101, "Test Doctor", 40, "87001112233", "Surgeon", 10);
        dao.insertDoctor(d);

        List<Doctor> doctors = dao.getAllDoctors();
        for (Doctor x : doctors) {
            System.out.println(x);
        }
    }
}