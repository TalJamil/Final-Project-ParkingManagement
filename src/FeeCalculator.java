import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
/**
 * The FeeCalculator class implements the Strategy pattern to calculate parking fees
 * based on the duration of vehicle stay in the parking lot. This class provides
 * a flexible fee calculation algorithm that can be easily modified or extended
 * to support different pricing strategies in the future.
 */
public class FeeCalculator {

    /**
     * Calculates the parking fee based on the time the vehicle stayed in the parking lot.
     * The fee is calculated at 5 NIS per hour, with partial hours rounded up to full hours.
     *
     * @param vehicle The vehicle object containing entry and exit time information
     * @return BigDecimal representing the calculated parking fee in NIS (Israeli Shekels)
     *         Returns BigDecimal.ZERO if entry or exit time is null
     * Algorithm:
     * 1. Extract entry and exit times from the vehicle
     * 2. Validate that both times are not null
     * 3. Calculate duration in minutes between entry and exit
     * 4. Round up to full hours (partial hours count as full hours)
     * 5. Multiply by hourly rate (5 NIS per hour)
     */
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
