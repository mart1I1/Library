package entity;

public class User {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "\n" +
                name + "\n";
    }

    @Override
    public int hashCode() {
        return id +
                name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User)obj;

        return id == user.id && name.equals(user.name);
    }
}
