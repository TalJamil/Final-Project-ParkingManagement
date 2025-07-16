package core;

/**
 * The core.ParkingSlot class represents an individual parking space within the parking lot.
 * It encapsulates the state and behavior of a single parking slot, including its
 * unique identifier and the vehicle currently occupying it (if any). This class
 * provides essential functionality for slot management, vehicle assignment, and
 * occupancy tracking within the parking management system.
 */
public class ParkingSlot {
    private int id; // Unique identifier of the slot (sequential number)
    private Vehicle currentVehicle; // The vehicle currently occupying the slot (if any)

    /**
     * Constructor - Creates a parking slot with a unique identifier, initially empty.
     * The slot starts in an unoccupied state with no vehicle assigned.
     *
     * @param id The unique identifier for this parking slot
     */
    public ParkingSlot(int id) {
        this.id = id;
        this.currentVehicle = null;
    }

    /**
     * Returns the unique identifier number of this parking slot.
     *
     * @return int representing the slot's ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the parking slot is currently occupied by a vehicle.
     *
     * @return boolean true if the slot is occupied (has a vehicle), false if empty
     */
    public boolean isOccupied() {
        return currentVehicle != null;
    }

    /**
     * Assigns a vehicle to this parking slot, marking it as occupied.
     * This method should only be called when the slot is empty to avoid
     * overwriting existing vehicle assignments.
     *
     * @param vehicle The vehicle object to assign to this slot
     */
    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    /**
     * Releases the parking slot by removing the current vehicle assignment.
     * This method makes the slot available for new vehicle assignments
     * by setting the current vehicle reference to null.
     */
    public void removeVehicle() {
        this.currentVehicle = null;
    }

    /**
     * Returns the vehicle currently occupying this parking slot.
     *
     * @return core.Vehicle object currently assigned to this slot, or null if the slot is empty
     */
    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}