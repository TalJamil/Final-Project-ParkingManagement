package parking_management;

import java.util.List;

public class DataReplication {

    public static void replicateData(List<Vehicle> vehicles) {
        System.out.println("Replicating parking data...");
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v != null) {
                System.out.println("Slot " + (i + 1) + ": " + v.getLicensePlate());
            } else {
                System.out.println("Slot " + (i + 1) + ": Empty");
            }
        }
    }
}
