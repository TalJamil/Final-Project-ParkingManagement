
package parking_management;

/**
 * מחלקה ליצירת דו"ח חניה עבור רכבים ותאים.
 */
public class Report {
    /**
     * מדפיס את סטטוס החניון.
     * @param manager מנהל החניון
     */
    public static void printStatus(ParkingLotManager manager) {
        for (ParkingSlot slot : manager.getSlots()) {
            if (slot.isOccupied()) {
                System.out.println("Slot " + slot.getId() + " - " + slot.getCurrentVehicle().getLicensePlate());
            } else {
                System.out.println("Slot " + slot.getId() + " - Empty");
            }
        }
    }
}
