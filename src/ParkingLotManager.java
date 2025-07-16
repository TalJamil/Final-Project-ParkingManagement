import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotManager {
    private static ParkingLotManager instance;
    private List<ParkingSlot> slots;

    // רשימת observers (צופים)
    private List<ParkingObserver> observers = new ArrayList<>();

    // 🆕 רשימה לשמירת כל הרכבים שנכנסו אי פעם (כולל אלה שיצאו)
    private List<Vehicle> allVehicles = new ArrayList<>();

    private ParkingLotManager(int totalSlots) {
        slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    public static ParkingLotManager getInstance() {
        if (instance == null) {
            instance = new ParkingLotManager(10); // ברירת מחדל: 10 מקומות
        }
        return instance;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                vehicles.add(slot.getCurrentVehicle());
            }
        }
        return vehicles;
    }

    // 🆕 מחזיר את כל הרכבים שנכנסו אי פעם
    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public int getTotalSlots() {
        return slots.size();
    }

    public boolean parkVehicle(Vehicle vehicle) {
        // בדיקה אם הרכב כבר חונה
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() &&
                    slot.getCurrentVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                System.out.println("שגיאה: רכב עם לוחית רישוי זו כבר חונה בחניון.");
                return false; // לא נמשיך לנסות להחנות
            }
        }

        // חניה רק אם לא חונה כבר
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.assignVehicle(vehicle);
                return true; // הצלחנו להכניס את הרכב
            }
        }

        // אם לא מצאנו אף תא פנוי
        System.out.println("החניון מלא! לא ניתן להכניס את הרכב.");
        return false;
    }



    // === תבנית Observer ===

    public void addObserver(ParkingObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        List<Vehicle> currentVehicles = getVehicles();
        for (ParkingObserver observer : observers) {
            observer.update(currentVehicles);
        }
    }

    // === שיטות Facade ===

    // ...existing code...

// ...existing code...

    public void checkInVehicle(Vehicle vehicle) {
        vehicle.setEntryTime(LocalDateTime.now());
        boolean success = parkVehicle(vehicle);
        if (success) {
            // 🆕 הוספת הרכב לרשימה הכללית
            allVehicles.add(vehicle);
            // 🆕 תיעוד כניסה בהיסטוריה
            ParkingHistory.addEntryRecord(vehicle);
            System.out.println("הרכב נכנס לחניון: " + vehicle.getLicensePlate());
            notifyObservers(); // ⬅️ עדכון observers
        }
    }



    // ...existing code...

    public void checkOutVehicle(String licensePlate) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                Vehicle v = slot.getCurrentVehicle();
                if (v.getLicensePlate().equals(licensePlate) && v.getExitTime() == null) {
                    v.setExitTime(LocalDateTime.now());
                    var fee = FeeCalculator.calculateFee(v);

                    // 🆕 תיעוד יציאה בהיסטוריה
                    ParkingHistory.addExitRecord(v, fee.toString());
                    System.out.println("הרכב יצא מהחניון. סכום לתשלום: " + fee + " ₪");

                    // ✅ כעת מוציאים את הרכב מהתא
                    slot.removeVehicle();

                    notifyObservers(); // ⬅️ עדכון observers
                    return;
                }
            }
        }
        System.out.println("הרכב לא נמצא בחניון.");
    }

// ...existing code...



    public void printFullReport() {
        ParkingLog.displayFullReport(allVehicles, getTotalSlots()); // 🔄 שינוי מ-getVehicles() ל-allVehicles
    }


    public void replicate() {
        DataReplication.replicateData(getVehicles());
    }
}