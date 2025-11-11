package com.feedbacksystem.model;

import java.util.Date;

/**
 * Model class representing a feedback entry
 */
public class Feedback {
    private int id;
    private String category;
    private String message;
    private int rating;
    private Date dateSubmitted;
    
    // Constructors
    public Feedback() {}
    
    public Feedback(String category, String message, int rating, Date dateSubmitted) {
        this.category = category;
        this.message = message;
        this.rating = rating;
        this.dateSubmitted = dateSubmitted;
    }
    
    public Feedback(int id, String category, String message, int rating, Date dateSubmitted) {
        this.id = id;
        this.category = category;
        this.message = message;
        this.rating = rating;
        this.dateSubmitted = dateSubmitted;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public Date getDateSubmitted() {
        return dateSubmitted;
    }
    
    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
    
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", dateSubmitted=" + dateSubmitted +
                '}';
    }
}