package parking_management;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParkingLotManager manager = ParkingLotManager.getInstance();

        // יצירת רכבים
        Vehicle car1 = new Vehicle("1234567", "משה כהן");
        Vehicle car2 = new Vehicle("7654321", "דנה לוי");

        // כניסה לחניון
        car1.setEntryTime(LocalDateTime.now());

        manager.parkVehicle(car1);

        car2.setEntryTime(LocalDateTime.now());
        manager.parkVehicle(car2);

        // הדפסת מצב ראשוני
        System.out.println("\n🔵 מצב החניון אחרי כניסת רכבים:");
        ParkingLog.logParkingStatus(manager.getVehicles());


        // יציאת רכב
        sleep(2000); // הדמיית זמן חנייה
        car1.setExitTime(LocalDateTime.now());

        manager.removeVehicle(car1);

        // הדפסת מצב אחרי יציאה
        System.out.println("\n🔵 מצב החניון אחרי יציאה:");
        ParkingLog.logParkingStatus(manager.getVehicles());


        // שכפול מידע
        System.out.println("\n🔁 שכפול נתוני חניון:");
        DataReplication.replicateData(manager.getVehicles());

        // הדפסת רישום רכבים
        System.out.println("\n📋 רישום רכבים בחניון:");
        ParkingLog.logParkingStatus(manager.getVehicles());

        // דו"ח סופי
        System.out.println("\n📊 דו\"ח חניון כולל:");
        ParkingLog.displayFullReport(manager.getVehicles(), manager.getTotalSlots());
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
