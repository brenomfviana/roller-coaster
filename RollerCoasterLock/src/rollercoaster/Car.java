/*
 * GNU License.
 */
package rollercoaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
    private static Car instance = new Car(4, 4);

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
    // When the car is ready
    private boolean ready;
    // List of passengers
    private List<Passenger> passengers;

    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

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
        this.ready = false;
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
     * Add passenger in the car.
     *
     * @param passenger The passenger
     */
    public void addPassenger(Passenger passenger) {
        this.lock.lock();
        try {
            if (this.isFull()) {
                this.full.await();
            }
            // Check if the car isn't full
            if (!this.isFull() && !this.passengers.contains(passenger)) {
                this.passengers.add(passenger);
                System.out.println("Passenger " + passenger.getID() + " is on board.");
                // Check if the car full
                if (this.isFull()) {
                    this.allowBoarding = false;
                    this.ready = true;
                }
            }
            this.empty.signal();
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Remove passenger from the car.
     *
     * @param passenger The passenger
     */
    public void removePassenger(Passenger passenger) {
        this.lock.lock();
        try {
            // Check if the car in't empty
            if (!this.passengers.isEmpty()) {
                this.passengers.remove(passenger);
                System.out.println("Passenger " + passenger.getID() + " disembarked.");
                passenger.walk();
                // Check if the car is empty
                if (this.passengers.isEmpty()) {
                    this.allowUnboarding = false;
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the passenger is in the car and false otherwise.
     *
     * @param passenger The passenger
     *
     * @return True if the passenger is in the car false otherwise
     */
    public boolean isInTheCar(Passenger passenger) {
        this.lock.lock();
        try {
            return this.passengers.contains(passenger);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car allows boarding and false otherwise.
     *
     * @return True if the car allows boarding and false otherwise
     */
    public boolean isAllowBoarding() {
        this.lock.lock();
        try {
            return this.allowBoarding;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car allows unboarding and false otherwise.
     *
     * @return True if the car allows unboarding and false otherwise
     */
    public boolean isAllowUnboarding() {
        this.lock.lock();
        try {
            return this.allowUnboarding;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car is ready and false otherwise.
     *
     * @return True if the car is ready and false otherwise
     */
    public boolean isReady() {
        this.lock.lock();
        try {
            return this.ready;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car is full and false otherwise.
     *
     * @return True if the car is full and false otherwise
     */
    public boolean isFull() {
        this.lock.lock();
        try {
            return this.capacity == this.passengers.size();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car is empty and false otherwise.
     *
     * @return True if the car is empty and false otherwise
     */
    public boolean isEmpty() {
        this.lock.lock();
        try {
            return this.passengers.isEmpty();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car is moving and false otherwise.
     *
     * @return True if the car is moving and false otherwise
     */
    public boolean isMoving() {
        this.lock.lock();
        try {
            return this.moving;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the car is stopped and false otherwise.
     *
     * @return True if the car is stopped and false otherwise.
     */
    public boolean isStopped() {
        this.lock.lock();
        try {
            return !this.isMoving();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the total number of rides is less than maximum number of
     * rides.
     *
     * @return True if the total number of rides is less than maximum number of
     * rides.
     */
    public boolean isWorking() {
        this.lock.lock();
        try {
            return this.maximumNumberOfRides > this.totalRides;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Allows passengers to board.
     */
    public void load() {
        this.lock.lock();
        try {
            // Allow boarding
            System.out.println("Boarding...");
            this.allowBoarding = true;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Allows passengers to unboard.
     */
    public void unload() {
        this.lock.lock();
        try {
            // Allow unboarding
            System.out.println("Unboarding...");
            this.allowUnboarding = true;
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Run.
     */
    public void run() {
        // Check if the car will still work
        if (this.isWorking() && this.isReady()) {
            System.out.println("Passengers" + passengers);
            this.lock.lock();
            try {
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
                    Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                this.lock.unlock();
            }
        }
    }
}
