/*
 * GNU License.
 */
package rollercoaster;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Roller Coaster passenger.
 *
 * @author Breno Viana
 * @version 20/05/2017
 */
public class Passenger implements Runnable {

    // Passenger ID
    private final int id;
    // Roller Coaster Car
    private final Car car;

    /**
     * Constructor.
     *
     * @param id Passenger ID
     * @param car Roller Coaster car
     */
    public Passenger(int id, Car car) {
        this.id = id;
        this.car = car;
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
     * Boarding. Get this passenger in the car.
     */
    public void board() {
        this.car.addPassenger(this);
        System.out.println("Passenger " + this.getID() + " is on board.");
    }

    /**
     * Unboarding. Get this passenger out the car.
     */
    public void unboard() {
        this.car.removePassenger(this);
        System.out.println("Passenger " + this.getID() + " disembarked.");
    }

    @Override
    public void run() {
        // Print passenger
        System.out.println("Passenger " + this.getID());
        // While the car is working
        while (true) {
            // If the passenger isn't on board and car allows boarding
            if (!this.isOnBoard() && this.car.isAllowBoarding()) {
                this.board();
            }
            // If the passenger is on board and car allows unboarding
            if (this.isOnBoard() && this.car.isAllowUnboarding()) {
                this.unboard();
            }
            // 
            if (!this.isOnBoard() && this.car.isMoving()) {
                System.out.println("Passenger " + this.getID() + " is waiting.");
                try {
                    TimeUnit.SECONDS.sleep((new Random()).nextInt(3) + 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
