

public class ParkingSlot {
    private int id; // מזהה של התא (מספר סידורי)
    private Vehicle currentVehicle; // הרכב שנמצא כרגע בתא (אם יש)

    // בנאי – יוצר תא חניה עם מזהה, מתחיל כריק
    public ParkingSlot(int id) {
        this.id = id;
        this.currentVehicle = null;
    }

    // מחזיר את מספר התא
    public int getId() {
        return id;
    }

    // מחזיר true אם התא תפוס (כלומר יש בו רכב)
    public boolean isOccupied() {
        return currentVehicle != null;
    }

    // מקצה רכב לתא
    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    // משחרר את התא (מסיר את הרכב)
    public void removeVehicle() {
        this.currentVehicle = null;
    }

    // מחזיר את הרכב שנמצא כרגע בתא
    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
