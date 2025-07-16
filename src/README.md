# Parking Lot Management System

This project simulates a comprehensive parking lot management system using object-oriented design and multiple design patterns in Java.

## Features

- **core.Vehicle Tracking**: Entry and exit with timestamps
- **Fee Calculation**: Automated parking fee calculation (5 NIS per hour)
- **Slot Management**: Real-time tracking of parking slot availability
- **Parking History**: Complete log of all parking operations
- **Statistics**: Reports on usage, revenue, and stay times
- **Automatic Logging**: Real-time system updates using Observer pattern
- **Data Validation**: Input validation for license plates and owner names
- **User Interface**: Simple command-line interface

## Design Patterns Implemented

- **Singleton Pattern**: `core.ParkingLotManager` - Single system manager
- **Observer Pattern**: `observer.ParkingObserver`, `observer.ParkingLog`, `observer.DataReplication` - Automatic updates
- **Strategy Pattern**: `services.FeeCalculator` - Parking fee calculation algorithm
- **Factory Pattern**: core.Vehicle and parking slot creation
- **Static Factory Method**: `services.TimeUtil`, `services.Report` - Static utility methods
- **Facade Pattern**: `ui.ParkingUI`, `core.ParkingLotManager` - Simplified interface to complex subsystems

## Project Structure

### Core Files:
- `Main.java` — Application entry point
- `ui.ParkingUI.java` — Command-line user interface (Facade)
- `core.Vehicle.java` — core.Vehicle representation with data validation
- `core.ParkingSlot.java` — Parking slot representation
- `core.ParkingLotManager.java` — Main system manager (Singleton + Facade)

### Helper Classes:
- `services.FeeCalculator.java` — Parking fee calculation
- `services.ParkingHistory.java` — Operation history management
- `services.ParkingStatistics.java` — Statistics calculation
- `services.TimeUtil.java` — Time utility functions

### Observer Pattern:
- `observer.ParkingObserver.java` — Observer interface
- `observer.ParkingLog.java` — Operation logging
- `observer.DataReplication.java` — Data replication

### Reporting:
- `services.Report.java` — System report generation (Facade)

### Testing:
- `test.ParkingTest.java` — 13 comprehensive unit tests

## System Menu

1. **Add core.Vehicle** - Enter a vehicle into the parking lot
2. **Remove core.Vehicle** - Exit a vehicle and calculate fees
3. **Show Parking Slot Status** - Display status of all parking slots
4. **Show Full Parking services.Report** - Generate comprehensive system report
5. **Replicate Parking Data** - Duplicate parking lot data
6. **Show Parking Statistics** - Display usage statistics
7. **Show Full History** - View complete operation history
8. **Show History by Date** - Search history by specific date
9. **Exit System** - Close the application

## How to Run

1. Open the project in IntelliJ IDEA or Eclipse
2. Ensure you have Java 17+ and JUnit 5
3. Compile all files
4. Run `Main.java`
5. Follow the menu instructions

## How to Test

### In IDE:
Right-click on `test.ParkingTest.java` → Run Tests

### Command Line:
```bash
javac -cp ".:junit-platform-console-standalone-1.8.2.jar" *.java
java -cp ".:junit-platform-console-standalone-1.8.2.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath
```

## Test Coverage

The test suite includes:
- **services.FeeCalculator Tests**: Fee calculation validation
- **core.Vehicle Tests**: Object creation and data validation
- **core.ParkingSlot Tests**: Slot management operations
- **core.ParkingLotManager Tests**: Singleton pattern and parking operations
- **services.ParkingStatistics Tests**: Statistics calculation
- **services.ParkingHistory Tests**: History tracking
- **services.TimeUtil Tests**: Time conversion utilities

## Requirements

- **Java**: Version 17 or higher
- **JUnit**: Version 5+
- **Memory**: 128MB RAM minimum
- **OS**: Windows/Linux/Mac compatible

## Input Validation

- **License Plate**: 3-8 characters, letters and numbers only
- **Owner Name**: Letters and spaces only
- **Error Handling**: User-friendly error messages with retry options

## Authors

**Liad Biton**  
**Tal Moshe Jamil**

---

*Project for Design Patterns Course - Semester B, Year B*