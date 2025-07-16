package parking_management;

import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;
    private String owner;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private boolean avilable;

    public Vehicle(String licensePlate, String owner) {
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public boolean isAvailable() {
        return avilable;
    }
}