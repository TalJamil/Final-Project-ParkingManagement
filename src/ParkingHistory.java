import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParkingHistory {
    private static List<HistoryEntry> history = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * רשומה בהיסטוריה
     */
    public static class HistoryEntry {
        private String licensePlate;
        private String owner;
        private LocalDateTime timestamp;
        private String action; // "ENTRY" או "EXIT"
        private String additionalInfo; // מידע נוסף (כמו דמי חניה)

        public HistoryEntry(String licensePlate, String owner, LocalDateTime timestamp, String action, String additionalInfo) {
            this.licensePlate = licensePlate;
            this.owner = owner;
            this.timestamp = timestamp;
            this.action = action;
            this.additionalInfo = additionalInfo;
        }

        public String getLicensePlate() { return licensePlate; }
        public String getOwner() { return owner; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getAction() { return action; }
        public String getAdditionalInfo() { return additionalInfo; }
    }

    /**
     * מוסיף רשומת כניסה להיסטוריה
     */
    public static void addEntryRecord(Vehicle vehicle) {
        history.add(new HistoryEntry(
                vehicle.getLicensePlate(),
                vehicle.getOwner(),
                vehicle.getEntryTime(),
                "ENTRY",
                "נכנס לחניון"
        ));
    }

    /**
     * מוסיף רשומת יציאה להיסטוריה
     */
    public static void addExitRecord(Vehicle vehicle, String fee) {
        history.add(new HistoryEntry(
                vehicle.getLicensePlate(),
                vehicle.getOwner(),
                vehicle.getExitTime(),
                "EXIT",
                "יצא מהחניון - תשלום: " + fee + " ₪"
        ));
    }




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
     * מחזיר את ההיסטוריה המלאה
     */
    public static List<HistoryEntry> getHistory() {
        return new ArrayList<>(history);
    }
}