/*
 * GNU License.
 */
package rollercoaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Roller Coaster handler.
 *
 * @author Breno Viana
 * @version 25/05/2017
 */
public class Handler {

    // Roller Coaster car
    private static Car car = Car.getInstance();

    /**
     * Run.
     */
    public static void run() {
        // Passengers
        List<Passenger> passengers = new ArrayList<>();
        // Creates the passengers
        for (int i = 0; i < ((new Random()).nextInt(1) + 5); i++) {
            passengers.add(new Passenger(i + 1, car));
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
            if (car.isStopped() && car.isFull() && !car.isReady()
                    && !car.isAllowBoarding() && !car.isAllowUnboarding()) {
                // Allow unboarding
                car.unload();
            }
            // Load
            if (car.isWorking() && car.isStopped() && car.isEmpty()
                    && !car.isReady() && !car.isAllowBoarding()
                    && !car.isAllowUnboarding()) {
                // Allow boarding
                car.load();
            }
            // Run
            if (car.isWorking() && car.isStopped() && car.isFull()
                    && car.isReady() && !car.isAllowUnboarding()
                    && !car.isAllowBoarding()) {
                // Run the ride
                car.run();
            }
        }
    }
}
