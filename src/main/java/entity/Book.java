package entity;

public class Book {

    private int id;
    private String title;
    private int authorId;
    private String description;
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book(String title, int authorId, String description, int quantity) {
        this.title = title;
        this.authorId = authorId;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public Book(int id, String title, int authorId, String description, int quantity) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "title = " + title + "\n" +
                "authorId = " + authorId + "\n" +
                "description = " + description + "\n" +
                "quantity = " + quantity + "\n";
    }

    @Override
    public int hashCode() {
        return id +
                title.hashCode() +
                authorId +
                description.hashCode() +
                quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        return id == book.id && title.equals(book.title) && authorId == book.authorId
                && description.equals(book.description) && quantity == book.quantity;
    }
}
