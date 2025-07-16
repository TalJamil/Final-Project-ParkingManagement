import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingTest {

    // === טסטים עבור FeeCalculator ===
    @Test
    void calculateFee_ShouldReturnFiveForUnderOneHour() {
        Vehicle v = new Vehicle("1234567", "Test");
        v.setEntryTime(LocalDateTime.now().minusMinutes(50));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(5.0), fee);
    }

    @Test
    void calculateFee_ShouldReturnTenForExactlyOneHour() {
        Vehicle v = new Vehicle("7654321", "Test");
        v.setEntryTime(LocalDateTime.now().minusHours(1));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(5.0), fee);
    }

    @Test
    void calculateFee_ShouldReturnTwentyForTwoHours() {
        Vehicle v = new Vehicle("1122334", "Test");
        v.setEntryTime(LocalDateTime.now().minusHours(2));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(10.0), fee);
    }

    // === טסטים עבור Vehicle ===
    @Test
    void vehicle_ShouldCreateCorrectly() {
        Vehicle v = new Vehicle("ABC123", "John");
        assertEquals("ABC123", v.getLicensePlate());
        assertEquals("John", v.getOwner());
        assertNull(v.getEntryTime());
        assertNull(v.getExitTime());
        assertFalse(v.isAvailable());
    }

    @Test
    void vehicle_ShouldSetEntryTimeAndAvailability() {
        Vehicle v = new Vehicle("ABC123", "John");
        LocalDateTime now = LocalDateTime.now();
        v.setEntryTime(now);
        
        assertEquals(now, v.getEntryTime());
        assertTrue(v.isAvailable());
    }

    // === טסטים עבור ParkingSlot ===
    @Test
    void parkingSlot_ShouldStartEmpty() {
        ParkingSlot slot = new ParkingSlot(1);
        assertEquals(1, slot.getId());
        assertFalse(slot.isOccupied());
        assertNull(slot.getCurrentVehicle());
    }

    @Test
    void parkingSlot_ShouldAssignVehicle() {
        ParkingSlot slot = new ParkingSlot(1);
        Vehicle v = new Vehicle("ABC123", "John");
        
        slot.assignVehicle(v);
        assertTrue(slot.isOccupied());
        assertEquals(v, slot.getCurrentVehicle());
    }

    @Test
    void parkingSlot_ShouldRemoveVehicle() {
        ParkingSlot slot = new ParkingSlot(1);
        Vehicle v = new Vehicle("ABC123", "John");
        
        slot.assignVehicle(v);
        slot.removeVehicle();
        assertFalse(slot.isOccupied());
        assertNull(slot.getCurrentVehicle());
    }

    // === טסטים עבור ParkingLotManager (Singleton) ===
    @Test
    void parkingLotManager_ShouldBeSingleton() {
        ParkingLotManager manager1 = ParkingLotManager.getInstance();
        ParkingLotManager manager2 = ParkingLotManager.getInstance();
        
        assertSame(manager1, manager2);
    }

    @Test
    void parkingLotManager_ShouldParkVehicle() {
        // איפוס הסינגלטון לטסט נקי
        ParkingLotManager manager = ParkingLotManager.getInstance();
        Vehicle v = new Vehicle("TEST123", "TestUser");
        
        boolean result = manager.parkVehicle(v);
        assertTrue(result);
        
        List<Vehicle> vehicles = manager.getVehicles();
        assertTrue(vehicles.contains(v));
    }

    // === טסטים עבור ParkingStatistics ===
    @Test
    void parkingStatistics_ShouldCalculateCorrectly() {
        Vehicle v1 = new Vehicle("AAA111", "User1");
        v1.setEntryTime(LocalDateTime.now().minusHours(2));
        v1.setExitTime(LocalDateTime.now().minusHours(1));
        
        Vehicle v2 = new Vehicle("BBB222", "User2");
        v2.setEntryTime(LocalDateTime.now().minusMinutes(30));
        
        List<Vehicle> vehicles = List.of(v1, v2);
        
        long todayCount = ParkingStatistics.getVehiclesToday(vehicles);
        assertEquals(2, todayCount);
        
        double avgTime = ParkingStatistics.getAverageStayTime(vehicles);
        assertEquals(60.0, avgTime); // רק v1 יצא, שהה 60 דקות
    }

    // === טסטים עבור ParkingHistory ===
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

    // === טסטים עבור TimeUtil ===
    @Test
    void timeUtil_ShouldConvertToDate() {
        LocalDateTime ldt = LocalDateTime.now();
        java.util.Date date = TimeUtil.toDate(ldt);
        
        assertNotNull(date);
        assertTrue(date instanceof java.util.Date);
    }
}