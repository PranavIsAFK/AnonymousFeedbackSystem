@echo off
echo Compiling Anonymous Student Feedback System...

javac -cp "lib/*;src/main/java" src/main/java/com/feedbacksystem/ui/MainWindow.java src/main/java/com/feedbacksystem/ui/SubmitFeedbackForm.java src/main/java/com/feedbacksystem/ui/AdminLoginForm.java src/main/java/com/feedbacksystem/ui/AdminDashboard.java src/main/java/com/feedbacksystem/dao/FeedbackDAO.java src/main/java/com/feedbacksystem/dao/AdminDAO.java src/main/java/com/feedbacksystem/db/DatabaseManager.java src/main/java/com/feedbacksystem/model/Feedback.java src/main/java/com/feedbacksystem/model/Admin.java

if %errorlevel% == 0 (
    echo Compilation successful!
    echo.
    echo To run the application, execute:
    echo java -cp "src/main/java;lib/*" com.feedbacksystem.ui.MainWindow
) else (
    echo Compilation failed!
    pause
)