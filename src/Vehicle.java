

import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;       // מספר רישוי של הרכב
    private String owner;              // שם בעל הרכב
    private LocalDateTime entryTime;   // זמן כניסה לחניון
    private LocalDateTime exitTime;    // זמן יציאה מהחניון
    private boolean available;         // האם הרכב נמצא בחניון (true = בפנים)

    // בנאי – יוצר רכב עם מספר רישוי ושם בעלים
    // ...existing code...
    public Vehicle(String licensePlate, String owner) {
        // אימות מספר רישוי - רק אותיות ומספרים
        if (licensePlate == null || !licensePlate.matches("^[A-Za-z0-9]{5,8}$")) {
            throw new IllegalArgumentException("מספר רישוי חייב להכיל רק אותיות ומספרים (5-8 תווים)");
        }

        // אימות שם בעלים - רק אותיות ורווחים
        if (owner == null || !owner.matches("^[A-Za-z\\s]+$")) {
            throw new IllegalArgumentException("שם בעלים חייב להכיל רק אותיות");
        }

        this.licensePlate = licensePlate;
        this.owner = owner;
    }
// ...existing code...

    // מחזיר את מספר הרישוי
    public String getLicensePlate() {
        return licensePlate;
    }

    // מחזיר את שם הבעלים
    public String getOwner() {
        return owner;
    }

    // מחזיר את זמן הכניסה לחניון
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    // מגדיר את זמן הכניסה לחניון
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
        this.available = true; // רכב נכנס לחניון
    }

    // מחזיר את זמן היציאה מהחניון
    public LocalDateTime getExitTime() {
        return exitTime;
    }

    // מגדיר את זמן היציאה מהחניון
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
        this.available = false; // רכב יצא מהחניון
    }

    // האם הרכב עדיין נמצא בחניון?
    public boolean isAvailable() {
        return available;
    }
}
