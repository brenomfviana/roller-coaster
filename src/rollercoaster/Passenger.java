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
     * @throws java.lang.Exception The car is full
     */
    public void board(Car car) throws Exception {
        // Check if the car isn't full and if this passenger isn't in the car
        if (!car.isFull() || !car.getPassengers().contains(this)) {
            this.onBoard = true;
            car.removePassenger(this);
        }
    }

    /**
     * Unboarding. Get this passenger out the car.
     *
     * @param car The Roller Coaster car
     * @throws java.lang.Exception The car is alredy empty
     */
    public void unboard(Car car) throws Exception {
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
