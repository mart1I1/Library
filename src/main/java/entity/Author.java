package entity;

import java.util.Date;

public class Author {

    private int id;
    private String name;
    private String birthday;

    public Author(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Author(int id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = " + name + "\n" +
                "birthday = " + birthday.toString();
    }

    @Override
    public int hashCode() {
        return id +
                name.hashCode() +
                birthday.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Author author = (Author)obj;

        return id == author.id && name.equals(author.name) && birthday.equals(author.birthday);
    }
}
