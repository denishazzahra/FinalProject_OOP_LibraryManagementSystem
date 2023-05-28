package Others;

public class Book implements BookInterface {
    private String title;
    private String author;
    private int year;
    private String genre;
    private int rentdays;
    private int late;
    public Book(String title, String author, int year, String genre){
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public int calculateRent() {
        return 10000;
    }

    @Override
    public int calculateFine() {
        return 5000;
    }
}
