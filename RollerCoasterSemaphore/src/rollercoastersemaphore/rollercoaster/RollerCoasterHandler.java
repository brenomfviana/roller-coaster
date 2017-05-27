/*
 * GNU License.
 */
package rollercoastersemaphore.rollercoaster;

/**
 * Roller Coaster handler.
 *
 * @author Breno & Patrícia
 * @version 27/05/2017
 */
public class RollerCoasterHandler {

    // Roller Coaster car
    private final RollerCoasterCar car;

    /**
     * Contructor.
     *
     * @param car Roller Coaster car.
     */
    public RollerCoasterHandler(RollerCoasterCar car) {
        this.car = car;
    }

    /**
     * Run the Roller Coaster handler.
     */
    public void run() {
        // Run Roller Coaster simulation
        while (true) {
            // Check if the car isn't in operation and if it is empty
            if (!car.isInOperation() && car.isEmpty()) {
                // Closes the car
                System.out.println("The car closed.");
                break;
            }
            // Check whether the car can allow unboarding
            if (car.isStopped() && car.isFull() && !car.isReady()
                    && !car.isAllowBoarding() && !car.isAllowUnboarding()) {
                // Allow unboarding
                car.unload();
            }
            // Check whether the car can allow boarding
            if (car.isInOperation() && car.isStopped() && !car.isReady()
                    && !car.isAllowBoarding() && !car.isAllowUnboarding()) {
                // Allow boarding
                car.load();
            }
            // Check if the car can run
            if (car.isInOperation() && car.isStopped() && car.isFull()
                    && car.isReady() && !car.isAllowUnboarding()) {
                // Run the ride
                car.run();
            }
        }
    }
}
