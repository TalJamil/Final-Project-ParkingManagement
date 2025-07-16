package parking_management;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ParkingLog {

    public static void logParkingStatus(List<Vehicle> vehicles) {
        System.out.println("----- רישום רכבים -----");
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v != null && v.isAvailable()) {
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate());
            } else {
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }
    }

    public static void displayFullReport(List<Vehicle> vehicles, int totalSlots) {
        System.out.println("----- דו\"ח חניון כולל -----");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v != null) {
                String entry = v.getEntryTime() != null ? v.getEntryTime().format(formatter) : "—";
                String exit = v.getExitTime() != null ? v.getExitTime().format(formatter) : "—";
                String status = (v.getExitTime() == null) ? "בחניון" : "יצא";
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate() +
                        ", כניסה: " + entry +
                        ", יציאה: " + exit +
                        ", סטטוס: " + status);
            } else {
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }

        long occupied = vehicles.stream().filter(v -> v != null && v.getExitTime() == null).count();
        System.out.println("תפוס: " + occupied + "/" + totalSlots);
    }
}
