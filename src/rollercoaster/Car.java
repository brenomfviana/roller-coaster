/*
 * GNU License.
 */
package rollercoaster;

/**
 * This class represents the Roller Coaster car.
 *
 * @author Breno Viana
 * @version 17/05/2017
 */
public class Car extends Thread {

    // Singleton
    private static Car instance = new Car();

    // Maximum number of rides
    private int maximumNumberOfRides;
    // Car capacity
    private int capacity;
}
