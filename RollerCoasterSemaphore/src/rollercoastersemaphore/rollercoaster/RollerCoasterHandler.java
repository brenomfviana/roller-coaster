/*
 * GNU License.
 */
package rollercoastersemaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Roller Coaster handler.
 *
 * @author Breno Viana
 */
public class Handler {

    // Roller Coaster car
    private static Car car = Car.getInstance();
    // Semaphore
    private static Semaphore sem = new Semaphore(1,true);

    /**
     * Run.
     */
    public static void run() {
        // Passengers
        List<Passenger> passengers = new ArrayList<>();
        // Creates the passengers
        for (int i = 0; i < ((new Random()).nextInt(1) + 2); i++) {
            passengers.add(new Passenger(i + 1, car, sem));
        }
        // Runs passengers
        passengers.stream().map((passenger) -> new Thread(passenger)).forEach((t) -> {
            t.start();
        });
        // Simulation
        while (true) {
            // Close car
            if (!car.isWorking() && car.isEmpty()) {
                System.out.println("The car closed.");
                break;
            }
            // Unload
            else if (car.isStopped() && car.isFull() && !car.isReady()
                    && !car.isAllowBoarding() && !car.isAllowUnboarding()) {
                // Allow unboarding
                car.unload();
            }
            // Load
            else if (car.isWorking() && car.isStopped() && !car.isReady()
                    && !car.isAllowBoarding() && !car.isAllowUnboarding()) {
                // Allow boarding
                car.load();
            }
            // Run
            else if (car.isWorking() && car.isStopped() && car.isFull()
                    && car.isReady() && !car.isAllowUnboarding()) {
                // Run the ride
                car.run();
            }
        }
    }
}
