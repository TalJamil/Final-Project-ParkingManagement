import java.util.List;

public class DataReplication implements ParkingObserver {

    @Override
    public void update(List<Vehicle> vehicles) {
        replicateData(vehicles);
    }

    // מתודה שמדפיסה את מצב החניה – רכב או ריק – לכל תא במגרש
    public static void replicateData(List<Vehicle> vehicles) {
        System.out.println("Replicating parking data...");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);

            if (v != null && v.isAvailable()) {
                // יש רכב בתא והוא עדיין בחניון – מדפיסים את מספר הרישוי
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate());
            } else {
                // אין רכב בתא או שהוא כבר יצא – מדפיסים "Empty"
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }
    }
}
