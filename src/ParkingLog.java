import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The ParkingLog class implements the Observer pattern to provide real-time logging
 * and monitoring of parking lot activities. It automatically receives notifications
 * when the parking lot state changes and generates comprehensive reports about
 * current parking status, vehicle entries, exits, and overall statistics.
 */
public class ParkingLog implements ParkingObserver {

    /**
     * Receives notifications from the parking lot manager when the parking state changes.
     * This method is automatically called whenever a vehicle enters or exits the parking lot,
     * triggering the logging of the current parking status.
     *
     * @param vehicles List of all vehicles that have ever been in the parking lot
     */
    @Override
    public void update(List<Vehicle> vehicles) {
        logParkingStatus(vehicles);
    }

    /**
     * Prints the current parking slots status showing which vehicles are still in the lot.
     * This method provides a quick overview of the current parking lot occupancy,
     * displaying each slot number with either the vehicle's license plate or "Empty" status.
     *
     * @param vehicles List of all vehicles to check for current parking status
     */
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

    /**
     * Prints a comprehensive parking report including entry/exit times and vehicle status
     * for each slot. This method provides detailed information about all vehicles that
     * have ever used the parking lot, including their current status and timing information.
     *
     * @param vehicles List of all vehicles that have ever been in the parking lot
     * @param totalSlots Total number of available parking slots in the lot
     *
     * Report includes:
     * - Vehicle license plate and owner information
     * - Entry and exit timestamps (formatted as dd-MM-yyyy HH:mm:ss)
     * - Current status (In Lot or Exited)
     * - Summary statistics of currently parked vs. total capacity
     */
    public static void displayFullReport(List<Vehicle> vehicles, int totalSlots) {
        System.out.println("----- Full Parking Report -----");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Display all vehicles that have ever entered the parking lot
        for (Vehicle v : vehicles) {
            if (v != null) {
                // Format entry/exit times – if not available, show dash
                String entry = v.getEntryTime() != null ? v.getEntryTime().format(formatter) : "—";
                String exit = v.getExitTime() != null ? v.getExitTime().format(formatter) : "—";

                // Vehicle status indicator
                String status = (v.getExitTime() == null) ? "🚗 In Lot" : "🚪 Exited";

                // Print comprehensive vehicle details
                System.out.println("Vehicle: " + v.getLicensePlate() +
                        " (Owner: " + v.getOwner() + ")" +
                        ", Entry: " + entry +
                        ", Exit: " + exit +
                        ", Status: " + status);
            }
        }

        // Parking slots summary statistics
        System.out.println("\n--- Parking Slots Summary ---");
        long currentlyParked = vehicles.stream()
                .filter(v -> v != null && v.getExitTime() == null)
                .count();

        System.out.println("Currently Parked: " + currentlyParked + " / " + totalSlots);
        System.out.println("Total Vehicles Ever Parked: " + vehicles.size());
    }
}