package observer;

import core.Vehicle;

import java.util.List;

/**
 * The observer.ParkingObserver interface defines the contract for the Observer pattern
 * implementation in the parking management system. Classes that implement this
 * interface will receive automatic notifications whenever the parking lot state
 * changes, enabling real-time monitoring and logging of parking activities.
 *
 * This interface is part of the Observer design pattern where:
 * - Subject: core.ParkingLotManager (notifies observers of state changes)
 * - Observer: Classes implementing this interface (receive notifications)
 * - ConcreteObservers: observer.ParkingLog, observer.DataReplication, etc.
 */
public interface ParkingObserver {

    /**
     * Called automatically by the core.ParkingLotManager when the parking lot state changes.
     * Implementing classes should define their specific response to parking state updates,
     * such as logging, data replication, or generating reports.
     *
     * @param vehicles List of vehicles currently in the parking lot at the time of notification.
     *                 This list contains only vehicles that are physically present (not exited).
     *
     * Typical implementations:
     * - observer.ParkingLog: Logs current parking status and generates reports
     * - observer.DataReplication: Creates backup copies of parking data
     * - Custom observers: Can implement specific business logic for parking events
     *
     * Note: This method is called automatically by the subject (core.ParkingLotManager)
     * whenever vehicles enter or exit the parking lot, ensuring observers stay
     * synchronized with the current parking state.
     */
    void update(List<Vehicle> vehicles);
}