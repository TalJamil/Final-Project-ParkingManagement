# Parking Lot Management System

This project simulates a comprehensive parking lot management system using object-oriented design and multiple design patterns in Java.

## Features

- **Vehicle Tracking**: Entry and exit with timestamps
- **Fee Calculation**: Automated parking fee calculation (5 NIS per hour)
- **Slot Management**: Real-time tracking of parking slot availability
- **Parking History**: Complete log of all parking operations
- **Statistics**: Reports on usage, revenue, and stay times
- **Automatic Logging**: Real-time system updates using Observer pattern
- **Data Validation**: Input validation for license plates and owner names
- **User Interface**: Simple command-line interface

## Design Patterns Implemented

- **Singleton Pattern**: `ParkingLotManager` - Single system manager
- **Observer Pattern**: `ParkingObserver`, `ParkingLog`, `DataReplication` - Automatic updates
- **Strategy Pattern**: `FeeCalculator` - Parking fee calculation algorithm
- **Factory Pattern**: Vehicle and parking slot creation
- **Static Factory Method**: `TimeUtil`, `Report` - Static utility methods
- **Facade Pattern**: `ParkingUI`, `ParkingLotManager` - Simplified interface to complex subsystems

## Project Structure

### Core Files:
- `Main.java` — Application entry point
- `ParkingUI.java` — Command-line user interface (Facade)
- `Vehicle.java` — Vehicle representation with data validation
- `ParkingSlot.java` — Parking slot representation
- `ParkingLotManager.java` — Main system manager (Singleton + Facade)

### Helper Classes:
- `FeeCalculator.java` — Parking fee calculation
- `ParkingHistory.java` — Operation history management
- `ParkingStatistics.java` — Statistics calculation
- `TimeUtil.java` — Time utility functions

### Observer Pattern:
- `ParkingObserver.java` — Observer interface
- `ParkingLog.java` — Operation logging
- `DataReplication.java` — Data replication

### Reporting:
- `Report.java` — System report generation (Facade)

### Testing:
- `ParkingTest.java` — 13 comprehensive unit tests

## System Menu

1. **Add Vehicle** - Enter a vehicle into the parking lot
2. **Remove Vehicle** - Exit a vehicle and calculate fees
3. **Show Parking Slot Status** - Display status of all parking slots
4. **Show Full Parking Report** - Generate comprehensive system report
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
Right-click on `ParkingTest.java` → Run Tests

### Command Line:
```bash
javac -cp ".:junit-platform-console-standalone-1.8.2.jar" *.java
java -cp ".:junit-platform-console-standalone-1.8.2.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath
```

## Test Coverage

The test suite includes:
- **FeeCalculator Tests**: Fee calculation validation
- **Vehicle Tests**: Object creation and data validation
- **ParkingSlot Tests**: Slot management operations
- **ParkingLotManager Tests**: Singleton pattern and parking operations
- **ParkingStatistics Tests**: Statistics calculation
- **ParkingHistory Tests**: History tracking
- **TimeUtil Tests**: Time conversion utilities

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