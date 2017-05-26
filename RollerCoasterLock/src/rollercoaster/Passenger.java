/*
 * GNU License.
 */
package rollercoastersemaphore;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;

/**
 * This class represents the Roller Coaster passenger.
 *
 * @author Patricia & Breno
 */
public class Passenger implements Runnable {

    // Passenger ID
    private final int id;
    // Roller Coaster Car
    private final Car car;
    // Walk in the park
    private boolean walk;
    // Semaphore
    private Semaphore sem;

    /**
     * Constructor.
     *
     * @param id Passenger ID
     * @param car Roller Coaster car
     */
    public Passenger(int id, Car car, Semaphore sem) {
        this.id = id;
        this.car = car;
        this.sem = sem;
    }

    /**
     * Get the passenger ID.
     *
     * @return Passenger ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get true if the passenger is on board and false otherwise.
     *
     * @return True if the passenger is on board and false otherwise
     */
    public boolean isOnBoard() {
        return this.car.isInTheCar(this);
    }

    /**
     * Get true if the passenger will walk in the park and false otherwise.
     *
     * @return True if the passenger will walk in the park and false otherwise
     */
    public boolean isWalk() {
        return this.walk;
    }

    /**
     * The passenger walks in the park.
     */
    public void walk() {
        this.walk = true;
    }

    /**
     * Boarding. Get this passenger in the car.
     */
    public void board() {
        this.car.addPassenger(this);
        
    }

    /**
     * Unboarding. Get this passenger out the car.
     */
    public void unboard() {
        this.car.removePassenger(this);
    }

    @Override
    public void run() {
        // Print passenger
        System.out.println("Passenger " + this.getID());
        // While the car is working
        while (true) {
            // If the passenger isn't on board and car allows boarding
            if (!this.isWalk() && !this.isOnBoard()
                    && this.car.isAllowBoarding()) {
                
                // Try to enter the semaphore
                try {
                    System.out.println("Passenger " + this.getID() + " requesting semaphore");
                    sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Board the car
                this.board();
            }
            // If the passenger is on board and car allows unboarding
            else if (this.isOnBoard() && this.car.isAllowUnboarding()) {
                // Unboard the car
                this.unboard();
                
                // Release the semaphore
                sem.release();
                System.out.println("Passenger " + this.getID() + " releasing semaphore");
            }
            // Walk in the park
            else if (!this.isOnBoard() && this.isWalk()) {
                System.out.println("Passenger " + this.getID() + " is walking.");
                try {
                    TimeUnit.SECONDS.sleep((new Random()).nextInt(5) + 1);
                    System.out.println("Passenger " + this.getID() + " back to roller coaster.");
                    this.walk = false;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Passenger is leaving
            else if (!this.isOnBoard() && !this.car.isWorking()) {
                System.out.println("Passenger " + this.getID() + " is leaving.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Passenger{" + "id=" + id + '}';
    }
}
