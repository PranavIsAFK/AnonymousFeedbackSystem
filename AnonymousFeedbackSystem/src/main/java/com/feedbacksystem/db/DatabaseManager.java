package com.feedbacksystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

/**
 * Database utility class for managing SQLite database connections
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:feedback.db";
    private static Connection connection;
    
    /**
     * Establishes a connection to the SQLite database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                createTablesIfNotExists();
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Creates the required tables if they don't exist
     * @throws SQLException if table creation fails
     */
    private static void createTablesIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create admin table
            stmt.execute("CREATE TABLE IF NOT EXISTS admin (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username VARCHAR(50), " +
                        "password VARCHAR(50))");
            
            // Create feedback table
            stmt.execute("CREATE TABLE IF NOT EXISTS feedback (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "category VARCHAR(50), " +
                        "message TEXT, " +
                        "rating INTEGER, " +
                        "date_submitted DATE)");
            
            // Insert default admin if not exists
            stmt.execute("INSERT OR IGNORE INTO admin (id, username, password) VALUES (1, 'admin', 'admin123')");
        }
    }
    
    /**
     * Closes the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}