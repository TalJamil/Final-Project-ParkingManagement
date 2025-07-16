package test;

import core.ParkingLotManager;
import core.ParkingSlot;
import core.Vehicle;
import org.junit.jupiter.api.Test;
import services.FeeCalculator;
import services.ParkingHistory;
import services.ParkingStatistics;
import services.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test.ParkingTest class provides comprehensive unit testing for the parking management system.
 * It uses JUnit 5 framework to validate the functionality of all core components including
 * fee calculation, vehicle management, parking slot operations, singleton pattern implementation,
 * statistics calculations, and history tracking. This test suite ensures system reliability
 * and validates that all design patterns are working correctly.
 */
public class ParkingTest {

    // === Tests for services.FeeCalculator ===

    /**
     * Tests that the fee calculator returns 5 NIS for parking duration under one hour.
     * Validates the ceiling rounding behavior where any partial hour is charged as a full hour.
     */
    @Test
    void calculateFee_ShouldReturnFiveForUnderOneHour() {
        Vehicle v = new Vehicle("1234567", "Test");
        v.setEntryTime(LocalDateTime.now().minusMinutes(50));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(5.0), fee);
    }

    /**
     * Tests that the fee calculator returns 5 NIS for exactly one hour of parking.
     * Validates that exactly one hour is charged as one full hour (5 NIS).
     */
    @Test
    void calculateFee_ShouldReturnFiveForExactlyOneHour() {
        Vehicle v = new Vehicle("7654321", "Test");
        v.setEntryTime(LocalDateTime.now().minusHours(1));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(5.0), fee);
    }

    /**
     * Tests that the fee calculator returns 10 NIS for exactly two hours of parking.
     * Validates the hourly rate multiplication (2 hours × 5 NIS = 10 NIS).
     */
    @Test
    void calculateFee_ShouldReturnTenForTwoHours() {
        Vehicle v = new Vehicle("1122334", "Test");
        v.setEntryTime(LocalDateTime.now().minusHours(2));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(10.0), fee);
    }

    // === Tests for core.Vehicle ===

    /**
     * Tests that a core.Vehicle object is created correctly with proper initialization.
     * Validates that license plate and owner are set, while entry/exit times are null,
     * and the vehicle is initially not available.
     */
    @Test
    void vehicle_ShouldCreateCorrectly() {
        Vehicle v = new Vehicle("ABC123", "John");
        assertEquals("ABC123", v.getLicensePlate());
        assertEquals("John", v.getOwner());
        assertNull(v.getEntryTime());
        assertNull(v.getExitTime());
        assertFalse(v.isAvailable());
    }

    /**
     * Tests that setting entry time makes the vehicle available.
     * Validates that when entry time is set, the vehicle becomes available for parking operations.
     */
    @Test
    void vehicle_ShouldSetEntryTimeAndAvailability() {
        Vehicle v = new Vehicle("ABC123", "John");
        LocalDateTime now = LocalDateTime.now();
        v.setEntryTime(now);

        assertEquals(now, v.getEntryTime());
        assertTrue(v.isAvailable());
    }

    // === Tests for core.ParkingSlot ===

    /**
     * Tests that a core.ParkingSlot starts in an empty state.
     * Validates initial state: correct ID, not occupied, and no current vehicle.
     */
    @Test
    void parkingSlot_ShouldStartEmpty() {
        ParkingSlot slot = new ParkingSlot(1);
        assertEquals(1, slot.getId());
        assertFalse(slot.isOccupied());
        assertNull(slot.getCurrentVehicle());
    }

    /**
     * Tests that a vehicle can be successfully assigned to a parking slot.
     * Validates that after assignment, the slot is occupied and returns the correct vehicle.
     */
    @Test
    void parkingSlot_ShouldAssignVehicle() {
        ParkingSlot slot = new ParkingSlot(1);
        Vehicle v = new Vehicle("ABC123", "John");

        slot.assignVehicle(v);
        assertTrue(slot.isOccupied());
        assertEquals(v, slot.getCurrentVehicle());
    }

    /**
     * Tests that a vehicle can be removed from a parking slot.
     * Validates that after removal, the slot becomes empty and unoccupied.
     */
    @Test
    void parkingSlot_ShouldRemoveVehicle() {
        ParkingSlot slot = new ParkingSlot(1);
        Vehicle v = new Vehicle("ABC123", "John");

        slot.assignVehicle(v);
        slot.removeVehicle();
        assertFalse(slot.isOccupied());
        assertNull(slot.getCurrentVehicle());
    }

    // === Tests for core.ParkingLotManager (Singleton Pattern) ===

    /**
     * Tests that core.ParkingLotManager correctly implements the Singleton pattern.
     * Validates that multiple calls to getInstance() return the same instance.
     */
    @Test
    void parkingLotManager_ShouldBeSingleton() {
        ParkingLotManager manager1 = ParkingLotManager.getInstance();
        ParkingLotManager manager2 = ParkingLotManager.getInstance();

        assertSame(manager1, manager2);
    }

    /**
     * Tests that the core.ParkingLotManager can successfully park a vehicle.
     * Validates that parking operation returns true and the vehicle appears in the vehicles list.
     */
    @Test
    void parkingLotManager_ShouldParkVehicle() {
        // Reset singleton for clean test
        ParkingLotManager manager = ParkingLotManager.getInstance();
        Vehicle v = new Vehicle("TEST123", "TestUser");

        boolean result = manager.parkVehicle(v);
        assertTrue(result);

        List<Vehicle> vehicles = manager.getVehicles();
        assertTrue(vehicles.contains(v));
    }

    // === Tests for services.ParkingStatistics ===

    /**
     * Tests that services.ParkingStatistics calculates metrics correctly.
     * Validates vehicle count for today and average stay time calculation
     * for vehicles with complete entry/exit data.
     */
    @Test
    void parkingStatistics_ShouldCalculateCorrectly() {
        Vehicle v1 = new Vehicle("AAA111", "UserA");  // שינוי מ-"User1" ל-"UserA"
        v1.setEntryTime(LocalDateTime.now().minusHours(2));
        v1.setExitTime(LocalDateTime.now().minusHours(1));

        Vehicle v2 = new Vehicle("BBB222", "UserB");  // שינוי מ-"User2" ל-"UserB"
        v2.setEntryTime(LocalDateTime.now().minusMinutes(30));

        List<Vehicle> vehicles = List.of(v1, v2);

        long todayCount = ParkingStatistics.getVehiclesToday(vehicles);
        assertEquals(2, todayCount);

        double avgTime = ParkingStatistics.getAverageStayTime(vehicles);
        assertEquals(60.0, avgTime); // רק v1 יצא, שהה 60 דקות
    }

    /**
     * Tests that services.ParkingHistory correctly adds entry records.
     * Validates that entry records are properly stored with correct license plate and action type.
     */
    @Test
    void parkingHistory_ShouldAddEntryRecord() {
        Vehicle v = new Vehicle("HIST123", "HistUser");
        v.setEntryTime(LocalDateTime.now());

        ParkingHistory.addEntryRecord(v);

        List<ParkingHistory.HistoryEntry> history = ParkingHistory.getHistory();
        assertFalse(history.isEmpty());

        ParkingHistory.HistoryEntry lastEntry = history.get(history.size() - 1);
        assertEquals("HIST123", lastEntry.getLicensePlate());
        assertEquals("ENTRY", lastEntry.getAction());
    }

    // === Tests for services.TimeUtil ===

    /**
     * Tests that services.TimeUtil correctly converts LocalDateTime to Date.
     * Validates that the conversion produces a valid Date object that is not null.
     */
    @Test
    void timeUtil_ShouldConvertToDate() {
        LocalDateTime ldt = LocalDateTime.now();
        java.util.Date date = TimeUtil.toDate(ldt);

        assertNotNull(date);
        assertTrue(date instanceof java.util.Date);
    }
}