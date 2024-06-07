package entity;

public class CD extends Item{
    private String artist;

    public CD(String id, String title, String publicsher, int year, String artist) {
        super(id, title, publicsher, year);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String getType() {
        return "CD";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "CD " + super.toString() + ", artist=" + artist + "]";
    }

}
