package entity;

public abstract class Item {
    private String id;
    private String title;
    private String publicsher;
    private int year;
    private boolean status;

    public Item(String id, String title, String publicsher, int year) {
        this.id = id;
        this.title = title;
        this.publicsher = publicsher;
        this.year = year;
        this.status = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicsher() {
        return publicsher;
    }

    public void setPublicsher(String publicsher) {
        this.publicsher = publicsher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public abstract String getType();

    @Override
    public String toString() {
        return "[id=" + id + ", title=" + title + ", publicsher=" + publicsher + ", year=" + year + ", status="
                + status ;
    }
}
