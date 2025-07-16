import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    /**
     * ממירה אובייקט מסוג LocalDateTime (מהספרייה החדשה של Java 8)
     * לאובייקט מסוג Date (מהספרייה הישנה של Java).
     * שימושי כאשר צריך תאימות לאובייקטים שעובדים עם Date.
     */
    public static Date toDate(LocalDateTime ldt) {
        // המרה לאזור זמן מקומי → Instant → Date
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
