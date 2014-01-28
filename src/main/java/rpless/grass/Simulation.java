package rpless.grass;

import rpless.grass.window.SimulationWindow;

/**
 * The {@code Simulation} is the thread that runs the simulation.
 * <p>
 *     It creates the window and starts the GLEventListener.
 */
public class Simulation extends Thread {

    public void run() {
        SimulationWindow window = new SimulationWindow();
        window.start(new SimulationRenderer());
    }
}