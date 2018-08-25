package entity;

public class User {
    private int id;

    private String name;
    private int age;
    private String email;
    private String password;
    private String image;
    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public User(String name, int age, String email, String password, String image) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(int id, String name, int age, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    @Override
    public String toString() {
        return "id =" + id + "\n" +
                "name = " + name + "\n" +
                "age = " + age + "\n" +
                "email = " + email + "\n" +
                "password = " + password;
    }

    @Override
    public int hashCode() {
        return id +
                name.hashCode() +
                age +
                email.hashCode() +
                password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User)obj;

        return id == user.id
                && name.equals(user.name)
                && age == user.age
                && email.equals(user.email)
                && password.equals(user.password);
    }
}
