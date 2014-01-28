package rpless.grass;

import rpless.grass.gl.SimulationRenderer;
import rpless.grass.window.SimulationWindow;

public class Simulation extends Thread {

    public void run() {
        SimulationWindow window = new SimulationWindow();
        window.start(new SimulationRenderer());
    }
}