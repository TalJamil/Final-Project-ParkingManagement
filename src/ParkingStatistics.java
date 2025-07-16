import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public class ParkingStatistics {

    /**
     * מחשב כמה רכבים נכנסו לחניון בשעה האחרונה
     */
    public static long getVehiclesInLastHour(List<Vehicle> allVehicles) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null)
                .filter(v -> v.getEntryTime().isAfter(oneHourAgo))
                .count();
    }

    /**
     * מחשב כמה רכבים נכנסו לחניון היום
     */
    public static long getVehiclesToday(List<Vehicle> allVehicles) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null)
                .filter(v -> v.getEntryTime().isAfter(startOfDay))
                .count();
    }

    /**
     * מחשב את הממוצע של זמן שהייה בחניון (בדקות)
     * רק עבור רכבים שכבר יצאו
     */
    public static double getAverageStayTime(List<Vehicle> allVehicles) {
        List<Vehicle> exitedVehicles = allVehicles.stream()
                .filter(v -> v.getEntryTime() != null && v.getExitTime() != null)
                .toList();

        if (exitedVehicles.isEmpty()) {
            return 0.0;
        }

        long totalMinutes = exitedVehicles.stream()
                .mapToLong(v -> Duration.between(v.getEntryTime(), v.getExitTime()).toMinutes())
                .sum();

        return (double) totalMinutes / exitedVehicles.size();
    }

    /**
     * 🆕 מחשב את סך הרווחים היומיים
     */
    public static BigDecimal getDailyRevenue(List<Vehicle> allVehicles) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null && v.getExitTime() != null)
                .filter(v -> v.getExitTime().isAfter(startOfDay))
                .map(FeeCalculator::calculateFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 🆕 מחשב את סך הרווחים השבועיים
     */
    public static BigDecimal getWeeklyRevenue(List<Vehicle> allVehicles) {
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null && v.getExitTime() != null)
                .filter(v -> v.getExitTime().isAfter(startOfWeek))
                .map(FeeCalculator::calculateFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * מציג את כל הסטטיסטיקות בפורמט נוח
     */
    public static void displayStatistics(List<Vehicle> allVehicles) {
        System.out.println("\n📊 === סטטיסטיקות חניון === 📊");
        System.out.println("🕐 רכבים שנכנסו בשעה האחרונה: " + getVehiclesInLastHour(allVehicles));
        System.out.println("📅 רכבים שנכנסו היום: " + getVehiclesToday(allVehicles));

        double avgStay = getAverageStayTime(allVehicles);
        if (avgStay > 0) {
            long hours = (long) (avgStay / 60);
            long minutes = (long) (avgStay % 60);
            System.out.println("⏱️ ממוצע זמן שהייה: " + hours + " שעות ו-" + minutes + " דקות");
        } else {
            System.out.println("⏱️ ממוצע זמן שהייה: אין נתונים זמינים");
        }

        // 🆕 הצגת רווחים
        BigDecimal dailyRevenue = getDailyRevenue(allVehicles);
        BigDecimal weeklyRevenue = getWeeklyRevenue(allVehicles);

        System.out.println("💰 רווחים יומיים: " + dailyRevenue + " ₪");
        System.out.println("💰 רווחים שבועיים: " + weeklyRevenue + " ₪");
        System.out.println("=====================================\n");
    }
}