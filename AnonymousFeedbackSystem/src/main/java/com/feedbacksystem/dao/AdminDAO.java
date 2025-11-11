package com.feedbacksystem.dao;

import com.feedbacksystem.db.DatabaseManager;
import com.feedbacksystem.model.Admin;
import java.sql.*;

/**
 * Data Access Object for Admin operations
 */
public class AdminDAO {
    
    /**
     * Authenticates an admin user
     * @param username The admin username
     * @param password The admin password
     * @return true if authentication is successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean authenticateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }
        }
    }
}