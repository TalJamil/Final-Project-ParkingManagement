

import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;       // מספר רישוי של הרכב
    private String owner;              // שם בעל הרכב
    private LocalDateTime entryTime;   // זמן כניסה לחניון
    private LocalDateTime exitTime;    // זמן יציאה מהחניון
    private boolean available;         // האם הרכב נמצא בחניון (true = בפנים)

    // בנאי – יוצר רכב עם מספר רישוי ושם בעלים
    public Vehicle(String licensePlate, String owner) {
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

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
