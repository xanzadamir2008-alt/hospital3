package model;

import exception.InvalidDataException;

public class Appointment {

    private int appointmentId;
    private String patientName;
    private String doctorName;
    private String date;
    private String status;

    public Appointment(int appointmentId, String patientName, String doctorName, String date) {
        setAppointmentId(appointmentId);
        setPatientName(patientName);
        setDoctorName(doctorName);
        setDate(date);
        status = "Scheduled";
    }

    public int getAppointmentId() { return appointmentId; }
    public String getStatus() { return status; }

    public void setAppointmentId(int appointmentId) {
        if (appointmentId <= 0)
            throw new IllegalArgumentException("Appointment ID must be positive");
        this.appointmentId = appointmentId;
    }

    public void setPatientName(String patientName) {
        if (patientName == null || patientName.trim().isEmpty()) // исправлено
            throw new InvalidDataException("Patient name cannot be empty");
        this.patientName = patientName.trim();
    }

    public void setDoctorName(String doctorName) {
        if (doctorName == null || doctorName.trim().isEmpty()) // исправлено
            throw new InvalidDataException("Doctor name cannot be empty");
        this.doctorName = doctorName.trim();
    }

    public void setDate(String date) {
        if (date == null || date.trim().isEmpty())
            throw new InvalidDataException("Date cannot be empty");
        this.date = date.trim();
    }

    public void reschedule(String newDate) {
        setDate(newDate);
        status = "Rescheduled";
    }

    public void cancel() {
        status = "Cancelled";
    }

    @Override
    public String toString() {
        return "Appointment{ID=" + appointmentId +
                ", patient='" + patientName + '\'' +
                ", doctor='" + doctorName + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
