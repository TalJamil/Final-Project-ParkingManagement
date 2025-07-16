import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The ParkingHistory class implements a comprehensive logging system for tracking
 * all parking lot activities. It maintains a complete history of vehicle entries
 * and exits, providing functionality to search and filter historical data by date.
 * This class uses the Data Transfer Object (DTO) pattern through its inner HistoryEntry class.
 */
public class ParkingHistory {
    private static List<HistoryEntry> history = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Data Transfer Object representing a single entry in the parking history.
     * This inner class encapsulates all information related to a parking event
     * (vehicle entry or exit) in a structured format.
     */
    public static class HistoryEntry {
        private String licensePlate;
        private String owner;
        private LocalDateTime timestamp;
        private String action; // "ENTRY" or "EXIT"
        private String additionalInfo; // Additional information (such as parking fee)

        /**
         * Constructs a new HistoryEntry with the specified parking event details.
         *
         * @param licensePlate The license plate number of the vehicle
         * @param owner The name of the vehicle owner
         * @param timestamp The date and time when the event occurred
         * @param action The type of action ("ENTRY" or "EXIT")
         * @param additionalInfo Additional information about the event
         */
        public HistoryEntry(String licensePlate, String owner, LocalDateTime timestamp, String action, String additionalInfo) {
            this.licensePlate = licensePlate;
            this.owner = owner;
            this.timestamp = timestamp;
            this.action = action;
            this.additionalInfo = additionalInfo;
        }

        /**
         * Returns the license plate number of the vehicle.
         * @return String representing the license plate
         */
        public String getLicensePlate() { return licensePlate; }

        /**
         * Returns the name of the vehicle owner.
         * @return String representing the owner's name
         */
        public String getOwner() { return owner; }

        /**
         * Returns the timestamp when the parking event occurred.
         * @return LocalDateTime representing the event timestamp
         */
        public LocalDateTime getTimestamp() { return timestamp; }

        /**
         * Returns the type of parking action performed.
         * @return String representing the action ("ENTRY" or "EXIT")
         */
        public String getAction() { return action; }

        /**
         * Returns additional information about the parking event.
         * @return String containing additional event details
         */
        public String getAdditionalInfo() { return additionalInfo; }
    }

    /**
     * Adds an entry record to the parking history when a vehicle enters the parking lot.
     * Creates a new HistoryEntry with "ENTRY" action and relevant vehicle information.
     *
     * @param vehicle The vehicle object containing entry information
     */
    public static void addEntryRecord(Vehicle vehicle) {
        history.add(new HistoryEntry(
                vehicle.getLicensePlate(),
                vehicle.getOwner(),
                vehicle.getEntryTime(),
                "ENTRY",
                "Entered parking lot"
        ));
    }

    /**
     * Adds an exit record to the parking history when a vehicle leaves the parking lot.
     * Creates a new HistoryEntry with "EXIT" action and includes the calculated parking fee.
     *
     * @param vehicle The vehicle object containing exit information
     * @param fee The calculated parking fee as a string
     */
    public static void addExitRecord(Vehicle vehicle, String fee) {
        history.add(new HistoryEntry(
                vehicle.getLicensePlate(),
                vehicle.getOwner(),
                vehicle.getExitTime(),
                "EXIT",
                "Exited parking lot - Fee: " + fee + " NIS"
        ));
    }

    /**
     * Displays the parking history filtered by a specific date.
     * Shows all entry and exit records that occurred on the specified date,
     * formatted in a user-friendly manner with timestamps and details.
     *
     * @param date The date to filter by in "dd-MM-yyyy" format
     */
    public static void displayHistoryByDate(String date) {
        System.out.println("\n📅 === History for Date: " + date + " === 📅");

        List<HistoryEntry> filteredHistory = history.stream()
                .filter(entry -> entry.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).equals(date))
                .toList();

        if (filteredHistory.isEmpty()) {
            System.out.println("No data found for this date.");
            return;
        }

        for (HistoryEntry entry : filteredHistory) {
            String action = entry.getAction().equals("ENTRY") ? "🚗 ENTRY" : "🚪 EXIT";
            String timestamp = "Date: " + entry.getTimestamp().format(formatter);
            String licensePlate = "License Plate: " + entry.getLicensePlate();
            String owner = "Owner: " + entry.getOwner();
            String actionInfo = "Action: " + action;
            String additionalInfo = entry.getAdditionalInfo().isEmpty() ? "" : "Details: " + entry.getAdditionalInfo();

            System.out.println(timestamp + ", " + licensePlate + ", " + owner + ", " + actionInfo +
                    (additionalInfo.isEmpty() ? "" : ", " + additionalInfo));
        }

        System.out.println("\nRecords for " + date + ": " + filteredHistory.size());
        System.out.println("=====================================");
    }

    /**
     * Returns a copy of the complete parking history.
     * This method provides access to all historical records while maintaining
     * data integrity by returning a new ArrayList copy.
     *
     * @return List<HistoryEntry> containing all parking history records
     */
    public static List<HistoryEntry> getHistory() {
        return new ArrayList<>(history);
    }
}