package com.feedbacksystem.ui;

import com.feedbacksystem.dao.AdminDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Form for admin login
 */
public class AdminLoginForm extends JFrame {
    private MainWindow mainWindow;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private AdminDAO adminDAO;
    
    public AdminLoginForm(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.adminDAO = new AdminDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        backButton = new JButton("Back to Main Menu");
        
        // Set button colors for better visibility
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
    }
    
    /**
     * Setup the layout of the window
     */
    private void setupLayout() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title label
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(128, 0, 128)); // Purple
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);
        mainPanel.add(titleLabel, gbc);
        
        // Username label and field
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);
        
        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainMenu();
            }
        });
        
        // Allow Enter key to trigger login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
    }
    
    /**
     * Setup window properties
     */
    private void setupWindow() {
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Validate login credentials
     */
    private boolean validateCredentials() {
        String username = usernameField.getText().trim();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return false;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Attempt to log in as admin
     */
    private void attemptLogin() {
        if (!validateCredentials()) {
            return;
        }
        
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        try {
            if (adminDAO.authenticateAdmin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                // Open admin dashboard
                openAdminDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", 
                                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                usernameField.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Open admin dashboard
     */
    private void openAdminDashboard() {
        AdminDashboard dashboard = new AdminDashboard(this);
        dashboard.setVisible(true);
        this.setVisible(false);
    }
    
    /**
     * Confirm exit dialog
     */
    private void confirmExit() {
        int option = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to go back to the main menu?", 
                                                  "Confirm Exit", 
                                                  JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            backToMainMenu();
        }
    }
    
    /**
     * Return to main menu
     */
    private void backToMainMenu() {
        mainWindow.setVisible(true);
        this.dispose();
    }
}