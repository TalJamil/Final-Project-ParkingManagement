import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The TimeUtil class provides static factory methods for time and date conversions.
 * This utility class implements the Static Factory Method pattern to offer convenient
 * conversion methods between different Java time APIs. It serves as a bridge between
 * the modern Java 8+ time API (LocalDateTime) and the legacy Java date API (Date),
 * ensuring compatibility with older libraries and systems that still require Date objects.
 */
public class TimeUtil {

    /**
     * Converts a LocalDateTime object (from the modern Java 8+ time API)
     * to a Date object (from the legacy Java date API).
     * This method is useful when compatibility with objects that work with Date is required,
     * such as older libraries, frameworks, or systems that haven't migrated to the new time API.
     *
     * @param ldt The LocalDateTime object to convert
     * @return Date object representing the same instant in time
     *
     * Conversion Process:
     * 1. LocalDateTime → ZonedDateTime (using system default timezone)
     * 2. ZonedDateTime → Instant (converts to UTC instant)
     * 3. Instant → Date (creates legacy Date object)
     *
     * Usage Example:
     * LocalDateTime now = LocalDateTime.now();
     * Date legacyDate = TimeUtil.toDate(now);
     *
     * Note: This conversion uses the system's default timezone. If you need to work
     * with specific timezones, consider using ZonedDateTime instead of LocalDateTime
     * as the input parameter.
     *
     * Thread Safety: This method is thread-safe as it only uses immutable objects
     * and doesn't maintain any state.
     */
    public static Date toDate(LocalDateTime ldt) {
        // Conversion: LocalDateTime → Local timezone → Instant → Date
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converts a Date object (from the legacy Java date API)
     * to a LocalDateTime object (from the modern Java 8+ time API).
     * This method provides the reverse conversion of toDate(), allowing migration
     * from legacy Date objects to the modern time API.
     *
     * @param date The Date object to convert
     * @return LocalDateTime object representing the same instant in time
     *
     * Conversion Process:
     * 1. Date → Instant (converts to UTC instant)
     * 2. Instant → ZonedDateTime (using system default timezone)
     * 3. ZonedDateTime → LocalDateTime (removes timezone information)
     *
     * Usage Example:
     * Date legacyDate = new Date();
     * LocalDateTime modern = TimeUtil.fromDate(legacyDate);
     */
    public static LocalDateTime fromDate(Date date) {
        // Conversion: Date → Instant → Local timezone → LocalDateTime
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Formats a LocalDateTime object to a standard string representation
     * suitable for display in reports and user interfaces.
     *
     * @param ldt The LocalDateTime to format
     * @return String representation in "dd-MM-yyyy HH:mm:ss" format
     *
     * Usage Example:
     * LocalDateTime now = LocalDateTime.now();
     * String formatted = TimeUtil.formatForDisplay(now);
     * // Returns: "16-07-2025 14:30:45"
     */
    public static String formatForDisplay(LocalDateTime ldt) {
        if (ldt == null) {
            return "—";
        }
        return ldt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}