import java.util.List;
/**
 * The DataReplication class implements the Observer pattern to provide automatic
 * backup and replication of parking lot data. It receives notifications when
 * the parking lot state changes and creates real-time backups of the current
 * parking status for system reliability and data integrity.
 */
public class DataReplication implements ParkingObserver {
    /**
     * Receives notifications from the parking lot manager when the parking state changes.
     * This method is automatically called whenever a vehicle enters or exits the parking lot.
     *
     * @param vehicles List of all vehicles currently in the parking lot
     */
    @Override
    public void update(List<Vehicle> vehicles) {
        replicateData(vehicles);
    }

    /**
     * Creates a backup copy of the current parking lot status by printing the state
     * of each parking slot. This method displays whether each slot is occupied
     * (with license plate number) or empty, serving as a data backup mechanism.
     *
     * @param vehicles List of all vehicles to replicate data for
     */
    public static void replicateData(List<Vehicle> vehicles) {
        System.out.println("Replicating parking data...");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);

            if (v != null && v.isAvailable()) {

                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate());
            } else {

                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }
    }
}
