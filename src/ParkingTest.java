

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingTest {

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

    @Test
    void calculateFee_ShouldReturnThirtyFiveForThreeAndHalfHours() {
        Vehicle v = new Vehicle("5566778", "Test");
        v.setEntryTime(LocalDateTime.now().minusHours(3).minusMinutes(30));
        v.setExitTime(LocalDateTime.now());

        BigDecimal fee = FeeCalculator.calculateFee(v);
        assertEquals(BigDecimal.valueOf(20.0), fee);
    }
}
