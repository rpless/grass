package rpless.grass;

import rpless.grass.window.SimulationWindow;

public class Main {
    public static void main(String... args) {
        new Thread() {
            public void run() {
                SimulationWindow window = new SimulationWindow();
                window.start(new Simulation());
            }
        }.start();
    }
}