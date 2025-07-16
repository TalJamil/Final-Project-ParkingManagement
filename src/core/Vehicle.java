package core;

import java.time.LocalDateTime;

/**
 * The core.Vehicle class represents an individual vehicle in the parking management system.
 * This class encapsulates all vehicle-related information including identification details,
 * parking timestamps, and availability status. It implements comprehensive input validation
 * to ensure data integrity and provides a complete set of accessor and mutator methods
 * for managing vehicle state throughout the parking process.
 */
public class Vehicle {
    private String licensePlate;       // License plate number of the vehicle
    private String owner;              // Name of the vehicle owner
    private LocalDateTime entryTime;   // Entry time to the parking lot
    private LocalDateTime exitTime;    // Exit time from the parking lot
    private boolean available;         // Whether the vehicle is currently in the parking lot (true = inside)

    /**
     * Constructor - Creates a vehicle with license plate and owner name.
     * Performs comprehensive input validation to ensure data integrity and system reliability.
     *
     * @param licensePlate The license plate number (must be 5-8 alphanumeric characters)
     * @param owner The name of the vehicle owner (must contain only letters and spaces)
     * @throws IllegalArgumentException if license plate or owner name format is invalid
     *
     * Validation Rules:
     * - License plate: 5-8 characters, alphanumeric only (A-Z, a-z, 0-9)
     * - Owner name: Letters and spaces only, cannot be null or empty
     *
     * Initial State:
     * - Entry time: null (not yet entered)
     * - Exit time: null (not yet exited)
     * - Available: false (not in parking lot initially)
     */
    public Vehicle(String licensePlate, String owner) {
        // Validate license plate - only letters and numbers
        if (licensePlate == null || !licensePlate.matches("^[A-Za-z0-9]{5,8}$")) {
            throw new IllegalArgumentException("License plate must contain only letters and numbers (5-8 characters)");
        }

        // Validate owner name - only letters and spaces
        if (owner == null || !owner.matches("^[A-Za-z\\s]+$")) {
            throw new IllegalArgumentException("Owner name must contain only letters and spaces");
        }

        this.licensePlate = licensePlate;
        this.owner = owner;
        this.entryTime = null;
        this.exitTime = null;
        this.available = false;
    }

    /**
     * Returns the license plate number of the vehicle.
     *
     * @return String representing the vehicle's license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Returns the name of the vehicle owner.
     *
     * @return String representing the owner's name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns the entry time when the vehicle entered the parking lot.
     *
     * @return LocalDateTime representing the entry time, or null if not yet entered
     */
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    /**
     * Sets the entry time when the vehicle enters the parking lot.
     * This method automatically marks the vehicle as available (present in the parking lot).
     *
     * @param entryTime The timestamp when the vehicle entered the parking lot
     *
     * Side Effects:
     * - Sets available to true (vehicle is now in the parking lot)
     * - Establishes the starting point for parking duration calculations
     */
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
        this.available = true; // core.Vehicle entered the parking lot
    }

    /**
     * Returns the exit time when the vehicle left the parking lot.
     *
     * @return LocalDateTime representing the exit time, or null if still in parking lot
     */
    public LocalDateTime getExitTime() {
        return exitTime;
    }

    /**
     * Sets the exit time when the vehicle leaves the parking lot.
     * This method automatically marks the vehicle as unavailable (no longer in the parking lot).
     *
     * @param exitTime The timestamp when the vehicle exited the parking lot
     *
     * Side Effects:
     * - Sets available to false (vehicle is no longer in the parking lot)
     * - Establishes the ending point for parking duration calculations
     * - Enables fee calculation based on stay duration
     */
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
        this.available = false; // core.Vehicle exited the parking lot
    }

    /**
     * Checks if the vehicle is currently available (present in the parking lot).
     * A vehicle is considered available if it has entered but not yet exited.
     *
     * @return boolean true if the vehicle is currently in the parking lot, false otherwise
     *
     * Business Logic:
     * - true: core.Vehicle has entered and is still in the parking lot
     * - false: core.Vehicle has not entered yet OR has already exited
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Provides a string representation of the vehicle for debugging and logging purposes.
     *
     * @return String containing vehicle details including license plate, owner, and current status
     */
    @Override
    public String toString() {
        String status = available ? "In Lot" : "Not in Lot";
        return String.format("core.Vehicle[License: %s, Owner: %s, Status: %s]",
                licensePlate, owner, status);
    }

    /**
     * Compares this vehicle with another object for equality based on license plate.
     * Two vehicles are considered equal if they have the same license plate.
     *
     * @param obj The object to compare with
     * @return boolean true if the vehicles have the same license plate, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vehicle vehicle = (Vehicle) obj;
        return licensePlate.equals(vehicle.licensePlate);
    }

    /**
     * Returns a hash code for this vehicle based on its license plate.
     *
     * @return int hash code for use in hash-based collections
     */
    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
}