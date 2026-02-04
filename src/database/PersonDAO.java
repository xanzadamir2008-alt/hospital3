package database;

import model.Doctor;
import model.Patient;
import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    // ================================
    // INSERT
    // ================================

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

    // ================================
    // SELECT ALL
    // ================================

    public List<Person> getAllPeople() {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people ORDER BY person_id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Person person = extractPerson(rs);
                if (person != null) {
                    list.add(person);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // ================================
    // SELECT BY ID
    // ================================

    public Person getPersonById(int id) {
        String sql = "SELECT * FROM people WHERE person_id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPerson(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // ================================
    // UPDATE
    // ================================

    public void updatePatient(Patient p) {
        String sql = """
                UPDATE people
                SET full_name = ?, age = ?, phone = ?, blood_type = ?
                WHERE person_id = ? AND person_type = 'PATIENT'
                """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getBloodType());
            ps.setInt(5, p.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDoctor(Doctor d) {
        String sql = """
                UPDATE people
                SET full_name = ?, age = ?, phone = ?, specialization = ?, experience_years = ?
                WHERE person_id = ? AND person_type = 'DOCTOR'
                """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setInt(2, d.getAge());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getSpecialization());
            ps.setInt(5, d.getExperienceYears());
            ps.setInt(6, d.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ================================
    // DELETE
    // ================================

    public void deletePerson(int id) {
        String sql = "DELETE FROM people WHERE person_id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ================================
    // SEARCH BY NAME (ILIKE)
    // ================================

    public List<Person> searchByName(String name) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE full_name ILIKE ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Person person = extractPerson(rs);
                if (person != null) {
                    list.add(person);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // ================================
    // SEARCH BY AGE (numeric)
    // ================================

    public List<Person> searchByAge(int age) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE age = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, age);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Person person = extractPerson(rs);
                if (person != null) {
                    list.add(person);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // ================================
    // HELPER METHOD
    // ================================

    private Person extractPerson(ResultSet rs) throws SQLException {

        int id = rs.getInt("person_id");
        String name = rs.getString("full_name");
        int age = rs.getInt("age");
        String phone = rs.getString("phone");
        String type = rs.getString("person_type");

        if ("PATIENT".equals(type)) {
            return new Patient(
                    id,
                    name,
                    age,
                    phone,
                    rs.getString("blood_type")
            );
        } else if ("DOCTOR".equals(type)) {
            return new Doctor(
                    id,
                    name,
                    age,
                    phone,
                    rs.getString("specialization"),
                    rs.getInt("experience_years")
            );
        }

        return null;
    }
}
