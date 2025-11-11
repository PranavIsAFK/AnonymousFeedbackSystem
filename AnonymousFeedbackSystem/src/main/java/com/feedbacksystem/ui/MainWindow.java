package com.feedbacksystem.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application window with options for submitting feedback or admin login
 */
public class MainWindow extends JFrame {
    private JButton submitFeedbackButton;
    private JButton adminLoginButton;
    
    public MainWindow() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeComponents() {
        submitFeedbackButton = new JButton("Submit Feedback");
        adminLoginButton = new JButton("Admin Login");
        
        // Set button colors for better visibility
        submitFeedbackButton.setBackground(Color.WHITE);
        submitFeedbackButton.setForeground(Color.BLACK);
        submitFeedbackButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitFeedbackButton.setFocusPainted(false);
        
        adminLoginButton.setBackground(Color.WHITE);
        adminLoginButton.setForeground(Color.BLACK);
        adminLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminLoginButton.setFocusPainted(false);
    }
    
    /**
     * Setup the layout of the window
     */
    private void setupLayout() {
        setTitle("Anonymous Student Feedback System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create title label
        JLabel titleLabel = new JLabel("Anonymous Student Feedback System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 0)); // Dark green
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 50;
        gbc.ipady = 20;
        buttonPanel.add(submitFeedbackButton, gbc);
        
        gbc.gridy = 1;
        buttonPanel.add(adminLoginButton, gbc);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // Add footer
        JLabel footerLabel = new JLabel("Your feedback is anonymous and valued", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.GRAY);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(footerLabel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers for buttons
     */
    private void setupEventHandlers() {
        submitFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubmitFeedbackForm();
            }
        });
        
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginForm();
            }
        });
    }
    
    /**
     * Setup window properties
     */
    private void setupWindow() {
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
    }
    
    /**
     * Open the submit feedback form
     */
    private void openSubmitFeedbackForm() {
        SubmitFeedbackForm feedbackForm = new SubmitFeedbackForm(this);
        feedbackForm.setVisible(true);
        this.setVisible(false);
    }
    
    /**
     * Open the admin login form
     */
    private void openAdminLoginForm() {
        AdminLoginForm adminForm = new AdminLoginForm(this);
        adminForm.setVisible(true);
        this.setVisible(false);
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show the main window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}