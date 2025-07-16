import java.time.format.DateTimeFormatter;
import java.util.List;

public class ParkingLog implements ParkingObserver {

    @Override
    public void update(List<Vehicle> vehicles) {
        logParkingStatus(vehicles);
    }

    // Prints the current parking slots status – which vehicles are still in the lot
    public static void logParkingStatus(List<Vehicle> vehicles) {
        System.out.println("----- Vehicle Log -----");
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);

            // If the slot has a vehicle and it's still in the lot – print its license plate
            if (v != null && v.isAvailable()) {
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate());
            } else {
                // Otherwise – the slot is empty
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }
    }

    // Prints a full parking report: entry/exit times and vehicle status for each slot
    public static void displayFullReport(List<Vehicle> vehicles, int totalSlots) {
        System.out.println("----- Full Parking Report -----");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v != null) {
                // Format entry/exit times – if not available, show dash
                String entry = v.getEntryTime() != null ? v.getEntryTime().format(formatter) : "—";
                String exit = v.getExitTime() != null ? v.getExitTime().format(formatter) : "—";

                // If no exit time – the vehicle is still in the lot
                String status = (v.getExitTime() == null) ? "In Lot" : "Exited";

                // Print vehicle details for this slot
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate() +
                        ", Entry: " + entry +
                        ", Exit: " + exit +
                        ", Status: " + status);
            } else {
                // If no vehicle – print that the slot is empty
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }

        // Count how many slots are currently occupied (vehicles without exit time)
        long occupied = vehicles.stream().filter(v -> v != null && v.getExitTime() == null).count();
        System.out.println("Occupied: " + occupied + " / " + totalSlots);
    }
}
