package edu.virginia.sde.reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewView {
    private int rating;
    private String timestamp;
    private String comment;
    private String course;
    public ReviewView(Review review)
    {
        this.rating = review.getRating();
        this.timestamp = review.getTimestamp().toString();
        this.comment = review.getComment();
        this.course = String.format("%s %s", review.getCourse().getSubject(), review.getCourse().getNumber());
    }

    // Getter for the rating property
    public int getRating() {
        return rating;
    }

    // Getter for the timestamp property
    public String getTimestamp() {
        return timestamp;
    }

    // Getter for the comment property
    public String getComment() {
        return comment;
    }

    public String getCourse() { return course; }
}
