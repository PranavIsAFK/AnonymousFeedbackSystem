package com.feedbacksystem.ui;

import com.feedbacksystem.dao.FeedbackDAO;
import com.feedbacksystem.model.Feedback;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Date;

/**
 * Form for submitting anonymous feedback
 */
public class SubmitFeedbackForm extends JFrame {
    private MainWindow mainWindow;
    private JComboBox<String> categoryComboBox;
    private JTextArea feedbackTextArea;
    private JComboBox<Integer> ratingComboBox;
    private JButton submitButton;
    private JButton backButton;
    private FeedbackDAO feedbackDAO;
    
    // Category options
    private static final String[] CATEGORIES = {"Teacher", "Event", "Facility", "Other"};
    // Rating options
    private static final Integer[] RATINGS = {1, 2, 3, 4, 5};
    
    public SubmitFeedbackForm(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.feedbackDAO = new FeedbackDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeComponents() {
        categoryComboBox = new JComboBox<>(CATEGORIES);
        feedbackTextArea = new JTextArea(8, 30);
        ratingComboBox = new JComboBox<>(RATINGS);
        submitButton = new JButton("Submit Feedback");
        backButton = new JButton("Back to Main Menu");
        
        // Configure text area
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Set button colors for better visibility
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);
        
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
    }
    
    /**
     * Setup the layout of the window
     */
    private void setupLayout() {
        setTitle("Submit Anonymous Feedback");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel with gridbag layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 248, 255)); // Lavender blush
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title label
        JLabel titleLabel = new JLabel("Submit Your Anonymous Feedback");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 100, 0)); // Dark green
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Category label and combo box
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Category:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(categoryComboBox, gbc);
        
        // Feedback label and text area
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Feedback Message:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        feedbackTextArea.setPreferredSize(new Dimension(300, 120));
        mainPanel.add(new JScrollPane(feedbackTextArea), gbc);
        
        // Rating label and combo box
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Rating (1-5):"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(ratingComboBox, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 255)); // Lavender blush
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
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
        
        // Submit button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainMenu();
            }
        });
    }
    
    /**
     * Setup window properties
     */
    private void setupWindow() {
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Validate input fields
     * @return true if all validations pass, false otherwise
     */
    private boolean validateInput() {
        String feedback = feedbackTextArea.getText().trim();
        
        if (feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your feedback message.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (feedback.length() < 10) {
            JOptionPane.showMessageDialog(this, "Feedback message should be at least 10 characters long.", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Submit feedback to database
     */
    private void submitFeedback() {
        if (!validateInput()) {
            return;
        }
        
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            String message = feedbackTextArea.getText().trim();
            int rating = (Integer) ratingComboBox.getSelectedItem();
            
            Feedback feedback = new Feedback(category, message, rating, new Date());
            
            if (feedbackDAO.insertFeedback(feedback)) {
                JOptionPane.showMessageDialog(this, "Thank you for your feedback! It has been submitted anonymously.", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear the form
                feedbackTextArea.setText("");
                categoryComboBox.setSelectedIndex(0);
                ratingComboBox.setSelectedIndex(4); // Default to 5 stars
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit feedback. Please try again.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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