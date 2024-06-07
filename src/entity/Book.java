package entity;

public class Book extends Item{
    private String author;

    public Book(String id, String title, String publicsher, int year, String author) {
        super(id, title, publicsher, year);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String toString() {
        return "Book " +  super.toString() + ", author=" + author + "]";
    }

}
