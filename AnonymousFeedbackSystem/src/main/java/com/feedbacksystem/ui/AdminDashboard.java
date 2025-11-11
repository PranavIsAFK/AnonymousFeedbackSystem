package com.feedbacksystem.ui;

import com.feedbacksystem.dao.FeedbackDAO;
import com.feedbacksystem.model.Feedback;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Admin dashboard for viewing and managing feedback
 */
public class AdminDashboard extends JFrame {
    private AdminLoginForm adminLoginForm;
    private JTable feedbackTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryFilterComboBox;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton backButton;
    private FeedbackDAO feedbackDAO;
    
    // Table column names
    private static final String[] COLUMN_NAMES = {"ID", "Category", "Feedback", "Rating", "Date"};
    // Category filter options
    private static final String[] FILTER_OPTIONS = {"All Categories", "Teacher", "Event", "Facility", "Other"};
    
    public AdminDashboard(AdminLoginForm adminLoginForm) {
        this.adminLoginForm = adminLoginForm;
        this.feedbackDAO = new FeedbackDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
        loadFeedbackData();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeComponents() {
        // Initialize table model and table
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        feedbackTable = new JTable(tableModel);
        feedbackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedbackTable.getTableHeader().setReorderingAllowed(false);
        feedbackTable.setFont(new Font("Arial", Font.PLAIN, 12));
        feedbackTable.setRowHeight(20);
        
        // Initialize filter combo box
        categoryFilterComboBox = new JComboBox<>(FILTER_OPTIONS);
        
        // Initialize buttons
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete Selected");
        backButton = new JButton("Logout");
        
        // Set button colors for better visibility
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setFocusPainted(false);
        
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);
    }
    
    /**
     * Setup the layout of the window
     */
    private void setupLayout() {
        setTitle("Admin Dashboard - Feedback Management");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create top panel for filtering
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(245, 245, 245)); // Light gray
        topPanel.add(new JLabel("Filter by Category:"));
        topPanel.add(categoryFilterComboBox);
        topPanel.add(refreshButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Create table scroll pane
        JScrollPane tableScrollPane = new JScrollPane(feedbackTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Feedback Entries"));
        add(tableScrollPane, BorderLayout.CENTER);
        
        // Create bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 245)); // Light gray
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmLogout();
            }
        });
        
        // Refresh button action
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFeedbackData();
            }
        });
        
        // Category filter action
        categoryFilterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterFeedbackData();
            }
        });
        
        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedFeedback();
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmLogout();
            }
        });
    }
    
    /**
     * Setup window properties
     */
    private void setupWindow() {
        setSize(800, 500);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
    }
    
    /**
     * Load all feedback data into the table
     */
    private void loadFeedbackData() {
        try {
            List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
            updateTable(feedbackList);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading feedback data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Filter feedback data based on selected category
     */
    private void filterFeedbackData() {
        String selectedCategory = (String) categoryFilterComboBox.getSelectedItem();
        
        try {
            List<Feedback> feedbackList;
            if ("All Categories".equals(selectedCategory)) {
                feedbackList = feedbackDAO.getAllFeedback();
            } else {
                feedbackList = feedbackDAO.getFeedbackByCategory(selectedCategory);
            }
            updateTable(feedbackList);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error filtering feedback data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Update the table with feedback data
     * @param feedbackList List of feedback entries to display
     */
    private void updateTable(List<Feedback> feedbackList) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add new data
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Feedback feedback : feedbackList) {
            Object[] rowData = {
                feedback.getId(),
                feedback.getCategory(),
                feedback.getMessage(),
                feedback.getRating(),
                dateFormat.format(feedback.getDateSubmitted())
            };
            tableModel.addRow(rowData);
        }
        
        // Auto-resize columns
        for (int i = 0; i < feedbackTable.getColumnCount(); i++) {
            feedbackTable.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
    }
    
    /**
     * Delete selected feedback entry
     */
    private void deleteSelectedFeedback() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback entry to delete.", 
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int feedbackId = (int) tableModel.getValueAt(selectedRow, 0);
        String category = (String) tableModel.getValueAt(selectedRow, 1);
        String message = (String) tableModel.getValueAt(selectedRow, 2);
        
        // Confirm deletion
        int option = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to delete this feedback entry?\n\n" +
                                                  "Category: " + category + "\n" +
                                                  "Message: " + message.substring(0, Math.min(message.length(), 50)) + 
                                                  (message.length() > 50 ? "..." : ""),
                                                  "Confirm Deletion", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                if (feedbackDAO.deleteFeedback(feedbackId)) {
                    // Remove row from table
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Feedback entry deleted successfully.", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete feedback entry.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error occurred: " + e.getMessage(), 
                                            "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Confirm logout dialog
     */
    private void confirmLogout() {
        int option = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to logout?", 
                                                  "Confirm Logout", 
                                                  JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            logout();
        }
    }
    
    /**
     * Logout and return to admin login
     */
    private void logout() {
        adminLoginForm.setVisible(true);
        this.dispose();
    }
}