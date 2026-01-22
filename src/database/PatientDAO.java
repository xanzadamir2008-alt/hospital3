package database;

import model.Patient;

import java.sql.*;
import java.util.ArrayList;

public class PatientDAO {

    public void insert(Patient p) {
        String sql = "INSERT INTO patients(id, full_name, age, phone, blood_type) VALUES (?, ?, ?, ?, ?)";
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

    public ArrayList<Patient> getAll() {
        ArrayList<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
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
}