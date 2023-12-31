package edu.virginia.sde.reviews;

import java.sql.Time;
import java.sql.Timestamp;

public class Review {
    private int rating;
    private Timestamp timestamp;
    private String comment;
    private Course course;

    // Constructor
    public Review(int rating, Timestamp timestamp, String comment, Course course) {
        this.rating = rating;
        this.timestamp = timestamp;
        this.comment = comment;
        this.course = course;
    }

    // Getters and Setters
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() { return this.course; }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", timestamp=" + timestamp +
                ", comment='" + comment + '\'' +
                '}';
    }
}