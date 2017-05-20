/*
 * GNU License.
 */
package rollercoaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Roller Coaster car.
 *
 * @author Breno Viana
 * @version 17/05/2017
 */
public class Car extends Thread {

    // Singleton
    private static Car instance = new Car(10, 10);

    // Maximum number of rides
    private final int maximumNumberOfRides;
    // Total number of rides daily
    private int totalRides;
    // Car capacity
    private final int capacity;
    // Is in moving
    private boolean moving;
    // Allow boarding
    private boolean allowBoarding;
    // Allow unboarding
    private boolean allowUnboarding;
    // List of passengers
    private List<Passenger> passengers;

    /**
     * Constructor.
     *
     * @param maximumNumberOfRides Maximum number of rides
     * @param capacity Car capacity
     */
    private Car(int maximumNumberOfRides, int capacity) {
        this.maximumNumberOfRides = maximumNumberOfRides;
        this.totalRides = 0;
        this.capacity = capacity;
        this.moving = false;
        this.allowBoarding = false;
        this.allowUnboarding = false;
        this.passengers = new ArrayList<>();
    }

    /**
     * Get instance of car.
     *
     * @return Instance of car
     */
    public static Car getInstance() {
        return instance;
    }

    /**
     * Get maximum number of rides.
     *
     * @return Maximum number of rides
     */
    public int getMaximumNumberOfRides() {
        return this.maximumNumberOfRides;
    }

    /**
     * Get current number of rides.
     *
     * @return Current number of rides
     */
    public int getTotalRides() {
        return this.totalRides;
    }

    /**
     * Get car capacity.
     *
     * @return Car capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Get current number of passengers.
     *
     * @return Current number of passengers
     */
    public int getNumberOfPassengers() {
        return this.passengers.size();
    }

    /**
     * Get true if the car is moving and false otherwise.
     *
     * @return True if the car is moving and false otherwise
     */
    public boolean isMoving() {
        return this.moving;
    }

    /**
     * Get true if the car allows boarding and false otherwise.
     *
     * @return True if the car allows boarding and false otherwise
     */
    public boolean isAllowBoarding() {
        return this.allowBoarding;
    }

    /**
     * Get true if the car allows unboarding and false otherwise.
     *
     * @return True if the car allows unboarding and false otherwise
     */
    public boolean isAllowUnboarding() {
        return this.allowUnboarding;
    }

    /**
     * Get passengers list.
     *
     * @return Passengers list
     */
    public List<Passenger> getPassengers() {
        return this.passengers;
    }

    /**
     * Add passenger in the car.
     *
     * @param passenger The passenger
     * @throws java.lang.Exception The car is full.
     */
    public void addPassenger(Passenger passenger) throws Exception {
        // Check if this car isn't full
        if (!this.isFull()) {
            this.passengers.add(passenger);
        } else {
            throw new Exception("The car is full, "
                    + "it's not possible to add passengers.");
        }
    }

    /**
     * Remove passenger from the car.
     *
     * @param passenger The passenger
     * @throws java.lang.Exception The car is already empty
     */
    public void removePassenger(Passenger passenger) throws Exception {
        // Check if the car in't empty
        if (!this.passengers.isEmpty()) {
            this.passengers.remove(passenger);
        } else {
            throw new Exception("The car is already empty, "
                    + "it's not possible remove passengers.");
        }
    }

    /**
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public boolean isFull() {
        return this.capacity == this.passengers.size();
    }

    /**
     * Get true if the car is stopped and false otherwise.
     *
     * @return True if the car is stopped and false otherwise.
     */
    public boolean isStopped() {
        return this.isMoving() == false;
    }

    /**
     * Get true if the total number of rides is less than maximum number of
     * rides.
     *
     * @return True if the total number of rides is less than maximum number of
     * rides.
     */
    public boolean isWorking() {
        return this.maximumNumberOfRides != this.totalRides;
    }

    /**
     * Allows passengers to board.
     */
    public void load() {
        // Check if the car isn't moving
        if (!this.isMoving()) {
            // Allow boarding
            this.allowBoarding = true;
        }
    }

    /**
     * Allows passengers to unboard.
     */
    public void unload() {
        // Check if the car isn't moving
        if (!this.isMoving()) {
            // Allow unboarding
            this.allowUnboarding = true;
        }
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        // Check if the car is full
        if (this.isWorking() && this.isFull() && this.isStopped()) {
            try {
                // Prevents boarding and unboarding
                this.allowUnboarding = false;
                this.allowBoarding = false;
                // Starts moving
                this.moving = true;
                // Ride
                TimeUnit.SECONDS.sleep((new Random()).nextInt(4000) + 1000);
                // Stops moving
                this.moving = false;
                // Allow unboarding
                unload();
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
