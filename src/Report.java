
/**
 * מחלקה ליצירת דו"ח חניה עבור רכבים ותאים.
 */

public class Report {

    /**
     * מדפיסה את סטטוס החניון – אילו תאים תפוסים ועל ידי איזה רכב.
     * @param manager מנהל החניון (כולל רשימת התאים)
     */
    public static void printStatus(ParkingLotManager manager) {
        for (ParkingSlot slot : manager.getSlots()) {
            if (slot.isOccupied()) {
                // אם התא תפוס – מדפיסים את מספר הרכב שבתוכו
                System.out.println("Slot " + slot.getId() + " - " + slot.getCurrentVehicle().getLicensePlate());
            } else {
                // אם התא ריק – מציגים "Empty"
                System.out.println("Slot " + slot.getId() + " - Empty");
            }
        }
    }
}
