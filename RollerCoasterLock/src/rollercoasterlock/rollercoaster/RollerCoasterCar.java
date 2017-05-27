/*
 * GNU License.
 */
package rollercoasterlock.rollercoaster;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import rollercoasterlock.Passenger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class represents the Roller Coaster car.
 *
 * @author Breno & Patrícia
 * @version 27/05/2017
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

    // Lock
    private Lock lock = new ReentrantLock();
    private Condition full = this.lock.newCondition();
    private Condition empty = this.lock.newCondition();

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
    public void addPassengerToQueue(Passenger passenger) {
        this.lock.lock();
        try {
            if (!this.queue.contains(passenger)) {
                this.queue.add(passenger);
                System.out.println(passenger.toString() + " is in line.");
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Remove passenger from the queue.
     *
     * @param passenger The passenger
     */
    public void removePassengerFromTheQueue(Passenger passenger) {
        this.lock.lock();
        try {
            // Check if the passenger is the next
            if (passenger == this.nextPassenger()) {
                this.queue.remove();
            }
        } finally {
            this.lock.unlock();
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
        this.lock.lock();
        try {
            return this.queue.contains(passenger);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get true if the line is empty and false otherwise.
     *
     * @return True if the line is empty and false otherwise
     */
    public boolean lineIsEmpty() {
        this.lock.lock();
        try {
            return this.queue.isEmpty();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Get the next passenger to board in the car.
     *
     * @return The next passenger to board in the car
     */
    public Passenger nextPassenger() {
        this.lock.lock();
        try {
            return this.queue.peek();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Add passenger in the car.
     *
     * @param passenger The passenger
     */
    public void addPassenger(Passenger passenger) {
        this.lock.lock();
        try {
            // Check if the car isn't full
            if (!this.isFull() && !this.passengers.contains(passenger)) {
                this.passengers.add(passenger);
                System.out.println(passenger.toString() + " is on board.");
                // Check if the car full
                if (this.isFull()) {
                    this.full.signal();
                    this.allowBoarding = false;
                    this.ready = true;
                }
            }
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
                System.out.println(passenger.toString() + " disembarked.");
                // Check if the car is empty
                if (this.passengers.isEmpty()) {
                    this.empty.signal();
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
            return this.passengers.size() == this.CAPACITY;
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
     * Get true if the car is in operation. That is, get true if the total
     * number of rides is less than maximum number of rides.
     *
     * @return True if the total number of rides is less than maximum number of
     * rides.
     */
    public boolean isInOperation() {
        this.lock.lock();
        try {
            return this.MAX_NUMBER_OF_RIDES > this.totalRides;
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
     * Wait for the car to be full.
     */
    public void waitFull() {
        this.lock.lock();
        try {
            try {
                this.full.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterCar.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Wait for the car to be empty.
     */
    public void waitEmpty() {
        this.lock.lock();
        try {
            try {
                this.empty.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterCar.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Run.
     */
    public void run() {
        // Check if the car will still work
        if (this.isInOperation() && this.isReady()) {
            System.out.println("Passengers" + this.passengers);
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
                    Logger.getLogger(RollerCoasterCar.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            } finally {
                this.lock.unlock();
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
