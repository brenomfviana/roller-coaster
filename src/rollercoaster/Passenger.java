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
    }

    /**
     * Get true if the passenger is on board and false otherwise.
     *
     * @return If the passenger is on board
     */
    public boolean isOnBoard() {
        return onBoard;
    }

    /**
     * Get this passenger in the car.
     */
    public void getInTheCar() {
        this.onBoard = true;
    }

    /**
     * Get this passenger out the car.
     */
    public void getOuTheCar() {
        this.onBoard = false;
    }

    /**
     * Get the standby time.
     *
     * @return Standby time.
     */
    public long getStandbyTime() {
        return standbyTime;
    }

    /**
     * Set the standby time.
     *
     * @param standbyTime New value to standby time
     */
    public void setStandbyTime(long standbyTime) {
        this.standbyTime = standbyTime;
    }

    @Override
    public void run() {
        //
        while(true) {
            //
        }
    }
}
