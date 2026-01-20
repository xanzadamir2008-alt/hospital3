public class Doctor extends Person {

    private String specialization;
    private int experienceYears;

    public Doctor(int id, String name, int age, String phone, String specialization, int experienceYears) {
        super(id, name, age, phone);
        setSpecialization(specialization);
        setExperienceYears(experienceYears);
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new InvalidDataException("Specialization cannot be empty");
        }
        this.specialization = specialization.trim();
    }

    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience cannot be negative");
        }
        this.experienceYears = experienceYears;
    }

    public boolean isExperienced() {
        return experienceYears >= 10;
    }

    public boolean canPerformSurgery() {
        return specialization.equalsIgnoreCase("Surgeon");
    }

    @Override
    public void work() {
        System.out.println(name + " is treating patients (" + specialization + ").");
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    @Override
    public String toString() {
        return getRole() + " {" + basicInfo() +
                ", Spec=" + specialization +
                ", Exp=" + experienceYears + "}";
    }
}