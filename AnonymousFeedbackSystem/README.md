# Anonymous Student Feedback System

A Java Swing application for collecting anonymous student feedback with an admin dashboard for managing feedback entries.

## Features

- **Submit Feedback**: Students can submit anonymous feedback with category, message, and rating
- **Admin Login**: Secure admin login to access the feedback dashboard
- **Feedback Management**: Admins can view, filter, and delete feedback entries
- **Database Storage**: SQLite database for persistent storage

## Prerequisites

- Java JDK 8 or higher
- SQLite JDBC Driver (included in the project)

## Project Structure

```
AnonymousFeedbackSystem/
├── compile.ps1            # PowerShell script to compile the project
├── database.sql           # SQL script to create database tables
├── feedback.db            # SQLite database file (created on first run)
├── run.ps1                # PowerShell script to run the application
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── feedbacksystem/
│                   ├── db/     # Database utilities
│                   ├── model/  # Data models
│                   ├── dao/    # Data access objects
│                   └── ui/     # User interface components
└── lib/                   # External libraries (SQLite JDBC driver)
```

## Installation

1. **Clone or Download the Project**:
   Download the project files to your local machine.

2. **Add SQLite JDBC Driver**:
   - Download the SQLite JDBC driver from [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)
   - Place the `sqlite-jdbc-xxx.jar` file in the `lib/` directory

3. **Compile the Project**:
   - On Windows: Run `compile.ps1`
   - On other systems: 
   ```bash
   javac -cp "lib/*;src/main/java" src/main/java/com/feedbacksystem/ui/MainWindow.java src/main/java/com/feedbacksystem/ui/SubmitFeedbackForm.java src/main/java/com/feedbacksystem/ui/AdminLoginForm.java src/main/java/com/feedbacksystem/ui/AdminDashboard.java src/main/java/com/feedbacksystem/dao/FeedbackDAO.java src/main/java/com/feedbacksystem/dao/AdminDAO.java src/main/java/com/feedbacksystem/db/DatabaseManager.java src/main/java/com/feedbacksystem/model/Feedback.java src/main/java/com/feedbacksystem/model/Admin.java
   ```

4. **Run the Application**:
   - On Windows: Run `run.ps1`
   - On other systems:
   ```bash
   java -cp "src/main/java;lib/*" com.feedbacksystem.ui.MainWindow
   ```

## Database Setup

The application automatically creates the required database tables on first run. The default admin credentials are:
- **Username**: admin
- **Password**: admin123

You can modify these by editing the database.sql file before first run.

## Usage

1. **Submitting Feedback**:
   - Click "Submit Feedback" on the main screen
   - Select a category from the dropdown
   - Enter your feedback message (minimum 10 characters)
   - Select a rating from 1 to 5
   - Click "Submit Feedback"

2. **Admin Login**:
   - Click "Admin Login" on the main screen
   - Enter admin credentials
   - Click "Login"

3. **Managing Feedback** (Admin):
   - View all feedback entries in the table
   - Filter feedback by category using the dropdown
   - Delete feedback entries by selecting a row and clicking "Delete Selected"
   - Refresh the table to see latest entries

## Color Palette

The application uses a carefully selected color palette for better visibility:
- Primary Buttons: Blue/Purple tones
- Action Buttons: Green/Red for positive/negative actions
- Backgrounds: Light grays for reduced eye strain
- Text: High contrast for readability

## Security Notes

- Passwords are stored in plain text in this demonstration version
- In a production environment, passwords should be hashed
- The application uses PreparedStatement to prevent SQL injection attacks

## Customization

You can customize:
- Categories in the SubmitFeedbackForm.java file
- Rating scale in the SubmitFeedbackForm.java file
- Color scheme in each UI class
- Database schema in database.sql

## Troubleshooting

- **Database Connection Issues**: Ensure the SQLite JDBC driver is in the lib/ directory
- **Classpath Errors**: Make sure to include both src directory and lib JAR files in classpath
- **Port in Use**: The application uses SQLite file database, so no port conflicts occur

## License

This project is for educational purposes and can be freely modified and distributed.