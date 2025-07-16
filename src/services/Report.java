package services;

import core.ParkingLotManager;
import core.ParkingSlot;

/**
 * The services.Report class provides static factory methods for generating various types of parking reports.
 * This class implements the Static Factory Method pattern to create standardized reporting
 * functionality across the parking management system. It serves as a centralized utility
 * for generating formatted reports that display parking lot status and vehicle information
 * in a user-friendly manner.
 */
public class Report {

    /**
     * Prints the current parking lot status showing which slots are occupied and by which vehicles.
     * This static factory method generates a comprehensive status report that displays each
     * parking slot's current state, providing administrators with a quick overview of the
     * entire parking lot occupancy at any given moment.
     *
     * @param manager The parking lot manager instance containing the list of all parking slots
     *
     * services.Report Format:
     * - Occupied slots: "Slot [ID] - [License Plate]"
     * - Empty slots: "Slot [ID] - Empty"
     *
     * Usage Example:
     * services.Report.printStatus(core.ParkingLotManager.getInstance());
     *
     * Output Example:
     * Slot 1 - ABC123
     * Slot 2 - Empty
     * Slot 3 - XYZ789
     * Slot 4 - Empty
     *
     * This method iterates through all available parking slots and displays their current
     * occupancy status, making it easy for parking administrators to quickly assess
     * parking lot utilization and available capacity.
     */
    public static void printStatus(ParkingLotManager manager) {
        System.out.println("\n--- Current Parking Lot Status ---");

        for (ParkingSlot slot : manager.getSlots()) {
            if (slot.isOccupied()) {
                // If slot is occupied - display the license plate of the vehicle in it
                System.out.println("Slot " + slot.getId() + " - " + slot.getCurrentVehicle().getLicensePlate());
            } else {
                // If slot is empty - display "Empty"
                System.out.println("Slot " + slot.getId() + " - Empty");
            }
        }

        // Display summary statistics
        long occupiedSlots = manager.getSlots().stream()
                .filter(ParkingSlot::isOccupied)
                .count();

        System.out.println("\nSummary: " + occupiedSlots + "/" + manager.getTotalSlots() + " slots occupied");
        System.out.println("Available slots: " + (manager.getTotalSlots() - occupiedSlots));
        System.out.println("===================================");
    }
}