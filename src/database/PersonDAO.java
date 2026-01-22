package database;

import model.Doctor;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public void insertPatient(Patient p) {
        String sql = """
                INSERT INTO people(person_id, full_name, age, phone, person_type, blood_type, specialization, experience_years)
                VALUES (?, ?, ?, ?, 'PATIENT', ?, NULL, NULL)
                """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getBloodType());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDoctor(Doctor d) {
        String sql = """
                INSERT INTO people(person_id, full_name, age, phone, person_type, blood_type, specialization, experience_years)
                VALUES (?, ?, ?, ?, 'DOCTOR', NULL, ?, ?)
                """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, d.getId());
            ps.setString(2, d.getName());
            ps.setInt(3, d.getAge());
            ps.setString(4, d.getPhone());
            ps.setString(5, d.getSpecialization());
            ps.setInt(6, d.getExperienceYears());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE person_type = 'PATIENT' ORDER BY person_id";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("person_id"),
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("blood_type")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE person_type = 'DOCTOR' ORDER BY person_id";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Doctor d = new Doctor(
                        rs.getInt("person_id"),
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("specialization"),
                        rs.getInt("experience_years")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}