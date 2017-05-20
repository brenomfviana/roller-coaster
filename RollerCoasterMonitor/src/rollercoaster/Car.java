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
 * @version 20/05/2017
 */
public class Car {

    // Singleton
    private static Car instance = new Car(10, 4);

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
     */
    public void addPassenger(Passenger passenger) {
        // Check if this car isn't full
        if (!this.isFull()) {
            this.passengers.add(passenger);
            System.out.println("Passenger " + passenger.getID() + " is on board.");
        } else {
            this.allowBoarding = false;
        }
    }

    /**
     * Remove passenger from the car.
     *
     * @param passenger The passenger
     */
    public void removePassenger(Passenger passenger) {
        // Check if the car in't empty
        if (!this.passengers.isEmpty()) {
            this.passengers.remove(passenger);
            System.out.println("Passenger " + passenger.getID() + " disembarked.");
        } else {
            this.allowUnboarding = false;
        }
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
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public boolean isFull() {
        return this.capacity == this.passengers.size();
    }

    /**
     * Get true if the car is empty and false otherwise.
     *
     * @return True if the car is empty and false otherwise
     */
    public boolean isEmpty() {
        return this.passengers.isEmpty();
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
        return this.maximumNumberOfRides > this.totalRides;
    }

    /**
     * Allows passengers to board.
     */
    public void load() {
        // Check if the car is stopped and if the car will still work
        if (this.isWorking() && this.isStopped()) {
            // Allow boarding
            this.allowBoarding = true;
        }
    }

    /**
     * Allows passengers to unboard.
     */
    public void unload() {
        // Check if the car is stopped
        if (this.isStopped()) {
            // Allow unboarding
            this.allowUnboarding = true;
        }
    }

    /**
     * Run.
     */
    public void run() {
        // Check if the car will still work
        if (this.isWorking()) {
            try {
                // Starts moving
                this.moving = true;
                // Ride
                System.out.println("Ride started.");
                this.totalRides++;
                TimeUnit.SECONDS.sleep((new Random()).nextInt(4000) + 1000);
                // Stops moving
                this.moving = false;
                System.out.println("Ride ended.");
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
