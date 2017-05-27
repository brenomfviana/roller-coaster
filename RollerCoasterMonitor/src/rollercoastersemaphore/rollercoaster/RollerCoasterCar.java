/*
 * GNU License.
 */
package rollercoastersemaphore.rollercoaster;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import rollercoastermonitor.Passenger;

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
    private final int CAPACITY = 4;

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
    // Passenger queue
    private Queue<Passenger> queue;

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
        this.queue = new ArrayDeque<>();
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
     * Add passenger to queue.
     *
     * @param passenger The passenger
     */
    public synchronized void addPassengerToQueue(Passenger passenger) {
        if (!this.queue.contains(passenger)) {
            this.queue.add(passenger);
            System.out.println(passenger.toString() + " is in line.");
        }
    }

    /**
     * Remove passenger from the queue.
     *
     * @param passenger The passenger
     */
    public synchronized void removePassengerFromTheQueue(Passenger passenger) {
        // Check if the passenger is the next
        if (passenger == this.nextPassenger()) {
            this.queue.remove();
        }
    }

    /**
     * Get true if the passenger is in line and false otherwise.
     *
     * @param passenger The passenger
     *
     * @return True if the passenger is in line false otherwise
     */
    public boolean isInLine(Passenger passenger) {
        return this.queue.contains(passenger);
    }

    /**
     * Get true if the line is empty and false otherwise.
     *
     * @return True if the line is empty and false otherwise
     */
    public boolean lineIsEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * Get the next passenger to board in the car.
     *
     * @return The next passenger to board in the car
     */
    public synchronized Passenger nextPassenger() {
        return this.queue.peek();
    }

    /**
     * Add passenger in the car.
     *
     * @param passenger The passenger
     */
    public synchronized void addPassenger(Passenger passenger) {
        // Check if the car isn't full
        if (!this.isFull() && !this.passengers.contains(passenger)) {
            this.passengers.add(passenger);
            System.out.println(passenger.toString() + " is on board.");
            // Check if the car full
            if (this.isFull()) {
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
    public synchronized void removePassenger(Passenger passenger) {
        // Check if the car in't empty
        if (!this.passengers.isEmpty()) {
            this.passengers.remove(passenger);
            System.out.println(passenger.toString() + " disembarked.");
            // Check if the car is empty
            if (this.passengers.isEmpty()) {
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
    public boolean isReady() {
        return this.ready;
    }

    /**
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public boolean isFull() {
        return this.passengers.size() == this.CAPACITY;
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
        return !this.isMoving();
    }

    /**
     * Get true if the car is in operation. That is, get true if the total
     * number of rides is less than maximum number of rides.
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
    public void load() {
        // Allow boarding
        System.out.println("Boarding...");
        this.allowBoarding = true;
    }

    /**
     * Allows passengers to unboard.
     */
    public void unload() {
        // Allow unboarding
        System.out.println("Unboarding...");
        this.allowUnboarding = true;
    }

    /**
     * Wait for the car to be full.
     */
    public void waitFull() {
        System.out.println("Waiting for the car to be full");
        while (!this.isFull()) {
            try {
                // Do nothing
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterCar.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("The car is full");
    }

    /**
     * Wait for the car to be empty.
     */
    public void waitEmpty() {
        System.out.println("Waiting for the car to be empty");
        while (!this.isEmpty()) {
            try {
                //do nothing
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterCar.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("The car is empty");
    }

    /**
     * Run.
     */
    public void run() {
        // Check if the car will still work
        if (this.isInOperation() && this.isReady()) {
            System.out.println("Passengers" + this.passengers);
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
                Logger.getLogger(RollerCoasterCar.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "RollerCoasterCar{" + "Max number of rides per day = "
                + this.MAX_NUMBER_OF_RIDES + ", Capacity of the car = "
                + this.CAPACITY + '}';
    }
}
