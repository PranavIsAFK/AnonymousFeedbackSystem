# PowerShell script to compile the Anonymous Feedback System
Write-Host "Compiling Anonymous Student Feedback System..."
Write-Host ""

# Compile all Java files
javac -cp "lib/*;src/main/java" `
    src/main/java/com/feedbacksystem/ui/MainWindow.java `
    src/main/java/com/feedbacksystem/ui/SubmitFeedbackForm.java `
    src/main/java/com/feedbacksystem/ui/AdminLoginForm.java `
    src/main/java/com/feedbacksystem/ui/AdminDashboard.java `
    src/main/java/com/feedbacksystem/dao/FeedbackDAO.java `
    src/main/java/com/feedbacksystem/dao/AdminDAO.java `
    src/main/java/com/feedbacksystem/db/DatabaseManager.java `
    src/main/java/com/feedbacksystem/model/Feedback.java `
    src/main/java/com/feedbacksystem/model/Admin.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!"
    Write-Host ""
    Write-Host "To run the application, execute:"
    Write-Host ".\run.ps1"
} else {
    Write-Host "Compilation failed!"
}