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
 * @version 20/05/2017
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
        for (int i = 0; i < ((new Random()).nextInt(1) + 2); i++) {
            passengers.add(new Passenger(i + 1));
        }
        // Runs passengers
        passengers.stream().forEach((Passenger p) -> p.start());
        // Simulation
        while (true) {
            // Unload
            if (car.isStopped() && car.isFull()) {
                // Allow unboarding
                car.unload();
            }
            // Check if the car will still work
            if (!car.isWorking()) {
                break;
            }
            // Check if the car is empty
            if (car.isStopped() && car.isEmpty()) {
                // signal
                // Allow boarding
                car.load();
            }
            // Check if the car is ready
            if (car.isStopped() && car.isFull() && !car.isAllowUnboarding()) {
                // wait
                // Run the ride
                car.run();
            }
        }
    }
}
