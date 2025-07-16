package parking_management;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotManager {
    private static ParkingLotManager instance;
    private List<ParkingSlot> slots;

    private ParkingLotManager(int totalSlots) {
        slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    public static ParkingLotManager getInstance() {
        if (instance == null) {
            instance = new ParkingLotManager(10); // ברירת מחדל: 10 מקומות
        }
        return instance;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                vehicles.add(slot.getCurrentVehicle());
            }
        }
        return vehicles;
    }

    public int getTotalSlots() {
        return slots.size();
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.assignVehicle(vehicle);
                return true;
            }
        }
        return false; // אין מקום פנוי
    }

    public boolean removeVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && slot.getCurrentVehicle().equals(vehicle)) {
                slot.removeVehicle();
                return true;
            }
        }
        return false;
    }
}
