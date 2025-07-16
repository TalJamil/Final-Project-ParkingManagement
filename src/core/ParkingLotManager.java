package core;
import observer.ParkingObserver;
import observer.ParkingLog;
import services.FeeCalculator;
import services.ParkingHistory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The core.ParkingLotManager class implements the Singleton pattern to ensure only one
 * instance manages the parking lot system. It serves as the central coordinator
 * for all parking operations and implements the Observer pattern to notify
 * registered observers of parking state changes. Additionally, it provides
 * Facade pattern functionality by offering simplified interfaces for complex operations.
 */
public class ParkingLotManager {
    private static ParkingLotManager instance;
    private List<ParkingSlot> slots;

    // List of observers (watchers) for parking state changes
    private List<ParkingObserver> observers = new ArrayList<>();

    // Complete list of all vehicles that have ever entered the parking lot (including those that exited)
    private List<Vehicle> allVehicles = new ArrayList<>();

    /**
     * Private constructor to prevent external instantiation (Singleton pattern).
     * Initializes the parking lot with the specified number of slots.
     *
     * @param totalSlots The total number of parking slots to create
     */
    private ParkingLotManager(int totalSlots) {
        slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    /**
     * Returns the single instance of core.ParkingLotManager (Singleton pattern).
     * Creates a new instance with 10 slots if none exists.
     *
     * @return The singleton instance of core.ParkingLotManager
     */
    public static ParkingLotManager getInstance() {
        if (instance == null) {
            instance = new ParkingLotManager(10); // Default: 10 parking slots
        }
        return instance;
    }

    /**
     * Returns the list of all parking slots in the lot.
     *
     * @return List<core.ParkingSlot> containing all parking slots
     */
    public List<ParkingSlot> getSlots() {
        return slots;
    }

    /**
     * Returns a list of vehicles currently parked in the lot.
     * Only includes vehicles that are physically present (not exited).
     *
     * @return List<core.Vehicle> containing currently parked vehicles
     */
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                vehicles.add(slot.getCurrentVehicle());
            }
        }
        return vehicles;
    }

    /**
     * Returns the complete list of all vehicles that have ever entered the parking lot.
     * This includes both currently parked and previously exited vehicles.
     *
     * @return List<core.Vehicle> containing all vehicles in the system history
     */
    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    /**
     * Returns the total number of parking slots in the lot.
     *
     * @return int representing the total slot capacity
     */
    public int getTotalSlots() {
        return slots.size();
    }

    /**
     * Attempts to park a vehicle in the first available slot.
     * Validates that the vehicle is not already parked before attempting to park.
     *
     * @param vehicle The vehicle to park
     * @return boolean true if parking was successful, false otherwise
     *
     * Validation:
     * - Checks if vehicle is already parked (prevents duplicate parking)
     * - Finds first available slot
     * - Assigns vehicle to slot or reports lot full
     */
    public boolean parkVehicle(Vehicle vehicle) {
        // Check if vehicle is already parked
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() &&
                    slot.getCurrentVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                System.out.println("Error: core.Vehicle with this license plate is already parked in the lot.");
                return false; // Do not proceed with parking
            }
        }

        // Park only if not already parked
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.assignVehicle(vehicle);
                return true; // Successfully parked the vehicle
            }
        }

        // If no available slot found
        System.out.println("Parking lot is full! Cannot park the vehicle.");
        return false;
    }

    // === Observer Pattern Implementation ===

    /**
     * Registers a new observer to receive parking state change notifications.
     *
     * @param observer The observer to add to the notification list
     */
    public void addObserver(ParkingObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers of parking state changes.
     * Sends the current list of parked vehicles to each observer.
     */
    private void notifyObservers() {
        List<Vehicle> currentVehicles = getVehicles();
        for (ParkingObserver observer : observers) {
            observer.update(currentVehicles);
        }
    }

    // === Facade Pattern Methods ===

    /**
     * Provides a simplified interface for vehicle check-in process.
     * Handles all necessary operations for vehicle entry in a single method call.
     *
     * @param vehicle The vehicle to check into the parking lot
     *
     * Operations performed:
     * - Sets entry time to current timestamp
     * - Attempts to park the vehicle
     * - Adds vehicle to historical records
     * - Logs entry in parking history
     * - Notifies all observers of the state change
     */
    public void checkInVehicle(Vehicle vehicle) {
        vehicle.setEntryTime(LocalDateTime.now());
        boolean success = parkVehicle(vehicle);
        if (success) {
            // Add vehicle to the complete list
            allVehicles.add(vehicle);
            // Record entry in history
            ParkingHistory.addEntryRecord(vehicle);
            System.out.println("core.Vehicle entered parking lot: " + vehicle.getLicensePlate());
            notifyObservers(); // Update observers
        }
    }

    /**
     * Provides a simplified interface for vehicle check-out process.
     * Handles all necessary operations for vehicle exit in a single method call.
     *
     * @param licensePlate The license plate of the vehicle to check out
     *
     * Operations performed:
     * - Locates the vehicle in the parking lot
     * - Sets exit time to current timestamp
     * - Calculates parking fee
     * - Records exit in parking history
     * - Removes vehicle from parking slot
     * - Notifies all observers of the state change
     */
    public void checkOutVehicle(String licensePlate) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                Vehicle v = slot.getCurrentVehicle();
                if (v.getLicensePlate().equals(licensePlate) && v.getExitTime() == null) {
                    v.setExitTime(LocalDateTime.now());
                    var fee = FeeCalculator.calculateFee(v);

                    // Record exit in history
                    ParkingHistory.addExitRecord(v, fee.toString());
                    System.out.println("core.Vehicle exited parking lot. Amount to pay: " + fee + " NIS");

                    // Remove vehicle from slot
                    slot.removeVehicle();

                    notifyObservers(); // Update observers
                    return;
                }
            }
        }
        System.out.println("core.Vehicle not found in parking lot.");
    }

    /**
     * Generates and displays a comprehensive parking report.
     * Shows detailed information about all vehicles that have ever used the parking lot.
     */
    public void printFullReport() {
        ParkingLog.displayFullReport(allVehicles, getTotalSlots()); // Use complete vehicle history
    }
}