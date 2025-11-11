-- Create admin table
CREATE TABLE admin (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50),
    password VARCHAR(50)
);

-- Create feedback table
CREATE TABLE feedback (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    category VARCHAR(50),
    message TEXT,
    rating INTEGER,
    date_submitted DATE
);

-- Insert default admin user (username: admin, password: admin123)
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');