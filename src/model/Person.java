public abstract class Person {

    protected int id;
    protected String name;
    protected int age;
    protected String phone;

    public Person(int id, String name, int age, String phone) {
        setId(id);
        setName(name);
        setAge(age);
        setPhone(phone);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null  name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    public void setPhone(String phone) {
        if (phone == null  phone.trim().length() < 5) {
            throw new InvalidDataException("Phone is too short");
        }
        this.phone = phone.trim();
    }

    public String basicInfo() {
        return "ID=" + id + ", Name=" + name + ", Age=" + age + ", Phone=" + phone;
    }

    // polymorphism methods
    public abstract void work();
    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + " {" + basicInfo() + "}";
    }
}
