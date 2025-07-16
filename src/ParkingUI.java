import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

/**
 * This class represents the command-line user interface (CLI) for the parking management system.
 */
public class ParkingUI {

    // Runs the parking system user interface loop
    public static void runParkingSystem() {
        ParkingLotManager manager = ParkingLotManager.getInstance();

        // 🔔 רישום observers
        manager.addObserver(new ParkingLog());
        manager.addObserver(new DataReplication());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=====================================");
        System.out.println(" 🚗  Welcome to the Parking System  🚗 ");
        System.out.println("=====================================");

        // ...existing code...

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. 🚘  Add vehicle");
            System.out.println("2. 🚪  Remove vehicle");
            System.out.println("3. 📋  Show parking slot status");
            System.out.println("4. 📊  Show full parking report");
            System.out.println("5. 📈  Show parking statistics");
            System.out.println("6. 📅  Show history by date"); // 🆕 אפשרות חדשה
            System.out.println("7. ❌  Exit system");

            System.out.print("\nEnter your choice (1–8): ");
            String choice = scanner.nextLine();

            switch (choice) {
                // ...existing code...

                case "1":
                    System.out.print("Enter license plate: ");
                    String licensePlate = scanner.nextLine();
                    System.out.print("Enter owner name: ");
                    String ownerName = scanner.nextLine();

                    try {
                        Vehicle vehicle = new Vehicle(licensePlate, ownerName);
                        // 🔄 שימוש ב-checkInVehicle במקום parkVehicle
                        manager.checkInVehicle(vehicle);
                        System.out.println("✅ Vehicle parked successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Invalid input: " + e.getMessage());
                        System.out.println("Please try again with valid data.");
                    }
                    break;



                case "2":
                    System.out.println("\n--- Remove Vehicle ---");
                    System.out.print("Enter license plate to remove: ");
                    String plateOut = scanner.nextLine();
                    manager.checkOutVehicle(plateOut);
                    break;

                case "3":
                    System.out.println("\n--- Parking Slot Status ---");
                    Report.printStatus(manager);
                    break;

                case "4":
                    System.out.println("\n--- Full Parking Report ---");
                    manager.printFullReport();
                    break;

                case "5":
                    System.out.println("\n--- Parking Statistics ---");
                    ParkingStatistics.displayStatistics(manager.getAllVehicles());
                    break;



                case "6": // 🆕 הצגת היסטוריה לפי תאריך
                    System.out.println("\n--- History by Date ---");
                    System.out.print("Enter date (dd-MM-yyyy): ");
                    String date = scanner.nextLine();
                    ParkingHistory.displayHistoryByDate(date);
                    break;

                case "7":
                    System.out.println("\nSystem shutting down. Goodbye! 👋");
                    running = false;
                    break;

                default:
                    System.out.println("\n❗ Invalid choice. Please try again.");
                    break;
            }
        }


    }
}