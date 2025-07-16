package parking_management;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculator {
    public static BigDecimal calculateFee(Vehicle vehicle) {
        LocalDateTime entry = vehicle.getEntryTime();
        LocalDateTime exit = vehicle.getExitTime();

        if (entry == null || exit == null) {
            return BigDecimal.ZERO;
        }

        long minutes = Duration.between(entry, exit).toMinutes();
        long hours = (minutes + 59) / 60;
        return BigDecimal.valueOf(hours * 5.0);
    }
}