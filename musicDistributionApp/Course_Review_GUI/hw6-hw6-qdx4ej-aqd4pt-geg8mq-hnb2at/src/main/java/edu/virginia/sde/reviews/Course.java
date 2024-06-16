package edu.virginia.sde.reviews;

//class for mock data until database can be integrated
public class Course {
    private String subject;
    private int number;
    private String title;
    private double rating;

    // Constructor
    public Course(String subject, int number, String title, double rating) {
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}


