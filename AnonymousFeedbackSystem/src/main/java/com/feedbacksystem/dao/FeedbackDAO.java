package com.feedbacksystem.dao;

import com.feedbacksystem.db.DatabaseManager;
import com.feedbacksystem.model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Feedback operations
 */
public class FeedbackDAO {
    
    /**
     * Inserts a new feedback entry into the database
     * @param feedback The feedback object to insert
     * @return true if insertion was successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean insertFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback (category, message, rating, date_submitted) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, feedback.getCategory());
            pstmt.setString(2, feedback.getMessage());
            pstmt.setInt(3, feedback.getRating());
            pstmt.setDate(4, new java.sql.Date(feedback.getDateSubmitted().getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Retrieves all feedback entries from the database
     * @return List of Feedback objects
     * @throws SQLException if database operation fails
     */
    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY date_submitted DESC";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setCategory(rs.getString("category"));
                feedback.setMessage(rs.getString("message"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setDateSubmitted(rs.getDate("date_submitted"));
                feedbackList.add(feedback);
            }
        }
        
        return feedbackList;
    }
    
    /**
     * Retrieves feedback entries by category
     * @param category The category to filter by
     * @return List of Feedback objects
     * @throws SQLException if database operation fails
     */
    public List<Feedback> getFeedbackByCategory(String category) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback WHERE category = ? ORDER BY date_submitted DESC";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, category);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(rs.getInt("id"));
                    feedback.setCategory(rs.getString("category"));
                    feedback.setMessage(rs.getString("message"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setDateSubmitted(rs.getDate("date_submitted"));
                    feedbackList.add(feedback);
                }
            }
        }
        
        return feedbackList;
    }
    
    /**
     * Deletes a feedback entry by ID
     * @param id The ID of the feedback to delete
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean deleteFeedback(int id) throws SQLException {
        String sql = "DELETE FROM feedback WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}