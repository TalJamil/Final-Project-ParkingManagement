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

    /**
     * מציג את כל ההיסטוריה
     */
    public static void displayFullHistory() {
        System.out.println("\n📜 === היסטוריה מלאה של כניסה ויציאה === 📜");

        if (history.isEmpty()) {
            System.out.println("אין נתונים בהיסטוריה.");
            return;
        }

        System.out.println("┌─────────────────────┬─────────────┬─────────────┬─────────┬─────────────────────────┐");
        System.out.println("│     תאריך ושעה      │   לוחית     │   בעלים     │ פעולה   │       פרטים נוספים      │");
        System.out.println("├─────────────────────┼─────────────┼─────────────┼─────────┼─────────────────────────┤");

        for (HistoryEntry entry : history) {
            String action = entry.getAction().equals("ENTRY") ? "🚗 כניסה" : "🚪 יציאה";

            System.out.printf("│ %-19s │ %-11s │ %-11s │ %-7s │ %-23s │%n",
                    entry.getTimestamp().format(formatter),
                    entry.getLicensePlate(),
                    entry.getOwner(),
                    action,
                    entry.getAdditionalInfo()
            );
        }

        System.out.println("└─────────────────────┴─────────────┴─────────────┴─────────┴─────────────────────────┘");
        System.out.println("סה\"כ רשומות: " + history.size());
    }

    /**
     * מציג היסטוריה מסונן לפי תאריך
     */
    public static void displayHistoryByDate(String date) {
        System.out.println("\n📅 === היסטוריה לתאריך: " + date + " === 📅");

        List<HistoryEntry> filteredHistory = history.stream()
                .filter(entry -> entry.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).equals(date))
                .toList();

        if (filteredHistory.isEmpty()) {
            System.out.println("אין נתונים לתאריך זה.");
            return;
        }

        System.out.println("┌─────────────────────┬─────────────┬─────────────┬─────────┬─────────────────────────┐");
        System.out.println("│     תאריך ושעה      │   לוחית     │   בעלים     │ פעולה   │       פרטים נוספים      │");
        System.out.println("├─────────────────────┼─────────────┼─────────────┼─────────┼─────────────────────────┤");

        for (HistoryEntry entry : filteredHistory) {
            String action = entry.getAction().equals("ENTRY") ? "🚗 כניסה" : "🚪 יציאה";

            System.out.printf("│ %-19s │ %-11s │ %-11s │ %-7s │ %-23s │%n",
                    entry.getTimestamp().format(formatter),
                    entry.getLicensePlate(),
                    entry.getOwner(),
                    action,
                    entry.getAdditionalInfo()
            );
        }

        System.out.println("└─────────────────────┴─────────────┴─────────────┴─────────┴─────────────────────────┘");
        System.out.println("סה\"כ רשומות לתאריך זה: " + filteredHistory.size());
    }

    /**
     * מחזיר את ההיסטוריה המלאה
     */
    public static List<HistoryEntry> getHistory() {
        return new ArrayList<>(history);
    }
}