package parking_management;

public class ParkingSlot {
    private int id;
    private Vehicle currentVehicle;

    public ParkingSlot(int id) {
        this.id = id;
        this.currentVehicle = null;
    }

    public int getId() {
        return id;
    }

    public boolean isOccupied() {
        return currentVehicle != null;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    public void removeVehicle() {
        this.currentVehicle = null;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
