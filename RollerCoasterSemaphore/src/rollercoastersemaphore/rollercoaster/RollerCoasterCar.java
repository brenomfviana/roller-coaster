/*
 * GNU License.
 */
package rollercoastersemaphore.rollercoaster;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import rollercoastersemaphore.Passenger;

/**
 * This class represents the Roller Coaster car.
 *
 * @author Patricia & Breno
 */
public class RollerCoasterCar {

    // Singleton
    private static RollerCoasterCar instance = new RollerCoasterCar();

    // Maximum Number of Rides
    private final int MAX_NUMBER_OF_RIDES = 4;
    // Capacity
    private final int CAPACITY = 1;

    // Total number of rides daily
    private int totalRides;
    // Is in moving
    private boolean moving;
    // Allow boarding
    private boolean allowBoarding;
    // Allow unboarding
    private boolean allowUnboarding;
    // When the car is ready
    private boolean ready;
    // List of passengers
    private List<Passenger> passengers;

    // Semaphore
    private Semaphore semaphore;

    /**
     * Constructor.
     */
    private RollerCoasterCar() {
        // Control variables
        this.totalRides = 0;
        // State variables
        this.ready = false;
        this.moving = false;
        this.allowBoarding = false;
        this.allowUnboarding = false;
        // Passengers
        this.passengers = new ArrayList<>();
        // Initializes the semaphore
        semaphore = new Semaphore(CAPACITY, true);
    }

    /**
     * Get instance of car.
     *
     * @return Instance of car
     */
    public static RollerCoasterCar getInstance() {
        return instance;
    }

    /**
     * Add passenger in the car.
     *
     * @param passenger The passenger
     */
    public void addPassenger(Passenger passenger) {
        // Try to enter the semaphore
        try {
            System.out.println(passenger.toString()
                    + " requesting semaphore; Sem getQueueLength(): "
                    + semaphore.getQueueLength());
            semaphore.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Check if the car isn't full
        if (!this.isFull() && !this.passengers.contains(passenger)) {
            this.passengers.add(passenger);
            System.out.println(passenger.toString() + " is on board.");
            // Check if the car full
            if (this.isFull()) {
                System.out.println("The car is full; AllowBoarding = false; Ready = true");
                this.allowBoarding = false;
                this.ready = true;
            }
        }
    }

    /**
     * Remove passenger from the car.
     *
     * @param passenger The passenger
     */
    public void removePassenger(Passenger passenger) {

        // Release the semaphore
        semaphore.release();
        System.out.println(passenger.toString() + " releasing semaphore");

        // Check if the car in't empty
        if (!this.passengers.isEmpty()) {
            this.passengers.remove(passenger);
            System.out.println(passenger.toString() + " disembarked.");
            passenger.walk();
            // Check if the car is empty
            if (this.passengers.isEmpty()) {
                semaphore.availablePermits();
                System.out.println("All passengers are gone; AllowUnboarding = false");
                this.allowUnboarding = false;
            }
        }
    }

    /**
     * Get true if the passenger is in the car and false otherwise.
     *
     * @param passenger The passenger
     *
     * @return True if the passenger is in the car false otherwise
     */
    public synchronized boolean isInTheCar(Passenger passenger) {
        return this.passengers.contains(passenger);
    }

    /**
     * Get true if the car allows boarding and false otherwise.
     *
     * @return True if the car allows boarding and false otherwise
     */
    public synchronized boolean isAllowBoarding() {
        return this.allowBoarding;
    }

    /**
     * Get true if the car allows unboarding and false otherwise.
     *
     * @return True if the car allows unboarding and false otherwise
     */
    public synchronized boolean isAllowUnboarding() {
        return this.allowUnboarding;
    }

    /**
     * Get true if the car is ready and false otherwise.
     *
     * @return True if the car is ready and false otherwise
     */
    public synchronized boolean isReady() {
        return this.ready;
    }

    /**
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public synchronized boolean isFull() {
        return this.passengers.size() == this.CAPACITY;
    }

    /**
     * Get true if the car is empty and false otherwise.
     *
     * @return True if the car is empty and false otherwise
     */
    public synchronized boolean isEmpty() {
        return this.passengers.isEmpty();
    }

    /**
     * Get true if the car is moving and false otherwise.
     *
     * @return True if the car is moving and false otherwise
     */
    public synchronized boolean isMoving() {
        return this.moving;
    }

    /**
     * Get true if the car is stopped and false otherwise.
     *
     * @return True if the car is stopped and false otherwise.
     */
    public synchronized boolean isStopped() {
        return !this.isMoving();
    }

    /**
     * Get true if the total number of rides is less than maximum number of
     * rides.
     *
     * @return True if the total number of rides is less than maximum number of
     * rides.
     */
    public synchronized boolean isInOperation() {
        return this.MAX_NUMBER_OF_RIDES > this.totalRides;
    }

    /**
     * Allows passengers to board.
     */
    public synchronized void load() {
        // Allow boarding
        this.allowBoarding = true;
        this.allowUnboarding = false;
        System.out.println("Boarding...");
    }

    /**
     * Allows passengers to unboard.
     */
    public synchronized void unload() {
        // Allow unboarding
        this.allowUnboarding = true;
        this.allowBoarding = false;
        System.out.println("Unboarding...");

    }

    /**
     * Run.
     */
    public synchronized void run() {
        // Check if the car will still work
        if (this.isInOperation() && this.isReady()) {
            System.out.println("Passengers" + passengers);
            try {
                // Starts moving
                this.ready = false;
                this.moving = true;
                // Ride
                System.out.println("Ride started.");
                this.totalRides++;
                TimeUnit.SECONDS.sleep((new Random()).nextInt(4) + 1);
                // Stops moving
                this.moving = false;
                System.out.println("Ride ended.");
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterCar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
