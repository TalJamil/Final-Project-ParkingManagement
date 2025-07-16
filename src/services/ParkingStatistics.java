package services;

import core.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * The services.ParkingStatistics class provides comprehensive statistical analysis and reporting
 * capabilities for the parking management system. It implements the Static Factory Method
 * pattern to offer utility methods for calculating various metrics such as vehicle counts,
 * average stay times, and revenue calculations. This class serves as a centralized hub
 * for all statistical operations and business intelligence within the parking system.
 */
public class ParkingStatistics {

    /**
     * Calculates how many vehicles entered the parking lot in the last hour.
     * This method filters vehicles based on their entry time and counts those
     * that entered within the past 60 minutes from the current time.
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     * @return long representing the number of vehicles that entered in the last hour
     */
    public static long getVehiclesInLastHour(List<Vehicle> allVehicles) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null)
                .filter(v -> v.getEntryTime().isAfter(oneHourAgo))
                .count();
    }

    /**
     * Calculates how many vehicles entered the parking lot today.
     * This method counts all vehicles that entered from midnight of the current day
     * until the current time.
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     * @return long representing the number of vehicles that entered today
     */
    public static long getVehiclesToday(List<Vehicle> allVehicles) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        return allVehicles.stream()
                .filter(v -> v.getEntryTime() != null)
                .filter(v -> v.getEntryTime().isAfter(startOfDay))
                .count();
    }

    /**
     * Calculates the average stay time in the parking lot (in minutes).
     * This calculation is performed only for vehicles that have already exited
     * the parking lot (have both entry and exit times).
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     * @return double representing the average stay time in minutes, or 0.0 if no data available
     *
     * Algorithm:
     * 1. Filter vehicles that have both entry and exit times
     * 2. Calculate duration for each vehicle
     * 3. Sum all durations and divide by number of vehicles
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
     * Calculates the total daily revenue from parking fees.
     * This method sums up all parking fees collected from vehicles that
     * exited the parking lot during the current day (from midnight to now).
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     * @return BigDecimal representing the total daily revenue in NIS
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
     * Calculates the total weekly revenue from parking fees.
     * This method sums up all parking fees collected from vehicles that
     * exited the parking lot during the past 7 days.
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     * @return BigDecimal representing the total weekly revenue in NIS
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
     * Displays comprehensive parking statistics in a user-friendly format.
     * This method generates a complete statistical report including vehicle counts,
     * average stay times, and revenue information with proper formatting and icons.
     *
     * @param allVehicles List of all vehicles that have ever entered the parking lot
     *
     * services.Report includes:
     * - Number of vehicles entered in the last hour
     * - Number of vehicles entered today
     * - Average stay time (formatted as hours and minutes)
     * - Daily revenue total
     * - Weekly revenue total
     */
    public static void displayStatistics(List<Vehicle> allVehicles) {
        System.out.println("\n📊 === Parking Lot Statistics === 📊");
        System.out.println("🕐 Vehicles entered in the last hour: " + getVehiclesInLastHour(allVehicles));
        System.out.println("📅 Vehicles entered today: " + getVehiclesToday(allVehicles));

        double avgStay = getAverageStayTime(allVehicles);
        if (avgStay > 0) {
            long hours = (long) (avgStay / 60);
            long minutes = (long) (avgStay % 60);
            System.out.println("⏱️ Average stay time: " + hours + " hours and " + minutes + " minutes");
        } else {
            System.out.println("⏱️ Average stay time: No data available");
        }

        // Revenue display
        BigDecimal dailyRevenue = getDailyRevenue(allVehicles);
        BigDecimal weeklyRevenue = getWeeklyRevenue(allVehicles);

        System.out.println("💰 Daily revenue: " + dailyRevenue + " NIS");
        System.out.println("💰 Weekly revenue: " + weeklyRevenue + " NIS");
        System.out.println("=====================================\n");
    }
}