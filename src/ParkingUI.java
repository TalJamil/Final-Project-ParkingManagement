import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

/**
 * The ParkingUI class represents the command-line user interface (CLI) for the parking management system.
 * It implements the Facade pattern by providing a simplified, user-friendly interface that hides the
 * complexity of the underlying parking management operations. This class serves as the main interaction
 * point between users and the parking system, offering intuitive menu-driven access to all system features.
 */
public class ParkingUI {

    /**
     * Runs the main parking system user interface loop.
     * This method initializes the system, registers observers, and provides an interactive
     * menu-driven interface for all parking operations. It serves as the primary entry point
     * for user interactions with the parking management system.
     *
     * Features provided:
     * - Vehicle entry and exit management
     * - Real-time parking status display
     * - Comprehensive reporting and statistics
     * - Historical data queries
     * - Observer pattern integration for real-time updates
     *
     * Observer Registration:
     * - ParkingLog: Provides real-time logging of parking activities
     * - DataReplication: Creates automatic backups of parking data
     */
    public static void runParkingSystem() {
        ParkingLotManager manager = ParkingLotManager.getInstance();

        // Register observers for real-time monitoring and data backup
        manager.addObserver(new ParkingLog());
        manager.addObserver(new DataReplication());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=====================================");
        System.out.println(" 🚗  Welcome to the Parking System  🚗 ");
        System.out.println("=====================================");

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. 🚘  Add vehicle");
            System.out.println("2. 🚪  Remove vehicle");
            System.out.println("3. 📋  Show parking slot status");
            System.out.println("4. 📊  Show full parking report");
            System.out.println("5. 📈  Show parking statistics");
            System.out.println("6. 📅  Show history by date");
            System.out.println("7. ❌  Exit system");

            System.out.print("\nEnter your choice (1–7): ");
            String choice = scanner.nextLine();

            switch (choice) {
                /**
                 * Case 1: Add Vehicle
                 * Handles vehicle entry process including input validation and error handling.
                 * Uses the facade pattern to simplify the complex check-in process.
                 */
                case "1":
                    System.out.print("Enter license plate: ");
                    String licensePlate = scanner.nextLine();
                    System.out.print("Enter owner name: ");
                    String ownerName = scanner.nextLine();

                    try {
                        Vehicle vehicle = new Vehicle(licensePlate, ownerName);
                        // Use checkInVehicle facade method for simplified vehicle entry
                        manager.checkInVehicle(vehicle);
                        System.out.println("✅ Vehicle parked successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Invalid input: " + e.getMessage());
                        System.out.println("Please try again with valid data.");
                    }
                    break;

                /**
                 * Case 2: Remove Vehicle
                 * Handles vehicle exit process including fee calculation and history recording.
                 * Uses the facade pattern to simplify the complex check-out process.
                 */
                case "2":
                    System.out.println("\n--- Remove Vehicle ---");
                    System.out.print("Enter license plate to remove: ");
                    String plateOut = scanner.nextLine();
                    manager.checkOutVehicle(plateOut);
                    break;

                /**
                 * Case 3: Show Parking Slot Status
                 * Displays current occupancy status of all parking slots.
                 * Provides quick overview of which slots are occupied or empty.
                 */
                case "3":
                    System.out.println("\n--- Parking Slot Status ---");
                    Report.printStatus(manager);
                    break;

                /**
                 * Case 4: Show Full Parking Report
                 * Displays comprehensive parking information including all vehicles
                 * that have ever used the parking lot, their entry/exit times, and current status.
                 */
                case "4":
                    System.out.println("\n--- Full Parking Report ---");
                    manager.printFullReport();
                    break;

                /**
                 * Case 5: Show Parking Statistics
                 * Displays statistical analysis including vehicle counts, average stay times,
                 * and revenue calculations for different time periods.
                 */
                case "5":
                    System.out.println("\n--- Parking Statistics ---");
                    ParkingStatistics.displayStatistics(manager.getAllVehicles());
                    break;

                /**
                 * Case 6: Show History by Date
                 * Allows users to query parking history for a specific date.
                 * Displays all entry and exit activities that occurred on the specified date.
                 */
                case "6":
                    System.out.println("\n--- History by Date ---");
                    System.out.print("Enter date (dd-MM-yyyy): ");
                    String date = scanner.nextLine();
                    ParkingHistory.displayHistoryByDate(date);
                    break;

                /**
                 * Case 7: Exit System
                 * Gracefully terminates the parking management system.
                 * Displays goodbye message and ends the main loop.
                 */
                case "7":
                    System.out.println("\nSystem shutting down. Goodbye! 👋");
                    running = false;
                    break;

                /**
                 * Default Case: Invalid Input Handling
                 * Provides user feedback for invalid menu choices and prompts for retry.
                 */
                default:
                    System.out.println("\n❗ Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}