import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class FeeCalculator {

    // מחשבת את דמי החניה לפי זמן שהרכב שהה בחניון
    public static BigDecimal calculateFee(Vehicle vehicle) {
        LocalDateTime entry = vehicle.getEntryTime(); // זמן כניסה
        LocalDateTime exit = vehicle.getExitTime();   // זמן יציאה

        if (entry == null || exit == null) {
            // אם אין נתוני כניסה/יציאה – אין תשלום
            return BigDecimal.ZERO;
        }

        long minutes = Duration.between(entry, exit).toMinutes(); // חישוב דקות
        long hours = (minutes + 59) / 60; // עיגול כלפי מעלה לשעות שלמות
        return BigDecimal.valueOf(hours * 5.0); // כל שעה = 5 שקלים
    }
}
