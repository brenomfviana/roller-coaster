/*
 * GNU License.
 */
package rollercoaster;

/**
 * This class represents the Roller Coaster passenger.
 *
 * @author Breno Viana
 * @version 17/05/2017
 */
public class Passenger extends Thread {

    // If the passenger is on board
    private boolean onBoard;
    // Standby time
    private long standbyTime;

    /**
     * Constructor.
     */
    public Passenger() {
        this.standbyTime = 0;
        this.onBoard = false;
    }

    /**
     * Get true if the passenger is on board and false otherwise.
     *
     * @return True if the passenger is on board and false otherwise
     */
    public boolean isOnBoard() {
        return this.onBoard;
    }

    /**
     * Boarding. Get this passenger in the car.
     *
     * @param car The Roller Coaster car
     */
    public void board(Car car) {
        // Check if the car isn't full
        if (!car.isFull()) {
            this.onBoard = true;
            car.removePassenger(this);
        }
    }

    /**
     * Landing. Get this passenger out the car.
     *
     * @param car The Roller Coaster car
     */
    public void unboard(Car car) {
        // Check if the car is stopped
        if (car.isStopped()) {
            this.onBoard = false;
            car.removePassenger(this);
        }
    }

    /**
     * Get the standby time.
     *
     * @return Standby time.
     */
    public long getStandbyTime() {
        return this.standbyTime;
    }

    /**
     * Set the standby time.
     *
     * @param standbyTime New value to standby time
     */
    public void setStandbyTime(long standbyTime) {
        this.standbyTime = standbyTime;
    }
}
