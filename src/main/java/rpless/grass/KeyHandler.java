package rpless.grass;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import rpless.grass.input.KeyInputAction;
import rpless.grass.input.recognizers.KeyCodeRecognizer;
import rpless.grass.input.recognizers.KeyPressRecognizer;
import rpless.grass.window.SimulationWindow;

public class KeyHandler implements KeyListener {
    private SimulationWindow window;
    private Camera camera;

    public KeyHandler(SimulationWindow window, Camera camera) {
        this.window = window;
        this.camera = camera;
    }

    KeyInputAction moveLeft = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_A), new KeyPressRecognizer());
    KeyInputAction moveRight = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_D), new KeyPressRecognizer());
    KeyInputAction moveForward = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_W), new KeyPressRecognizer());
    KeyInputAction moveBack = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_S), new KeyPressRecognizer());
    KeyInputAction closeSimulation = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_ESCAPE), new KeyPressRecognizer());

    @Override
    public void keyPressed(KeyEvent event) {
        if (moveLeft.isDetected(event)) camera.strafeRight(0.15f);
        if (moveRight.isDetected(event)) camera.strafeRight(-0.15f);
        if (moveForward.isDetected(event)) camera.moveForward(0.175f);
        if (moveBack.isDetected(event)) camera.moveForward(-0.15f);
        if (closeSimulation.isDetected(event)) window.stop();
    }

    @Override
    public void keyReleased(KeyEvent event) {}
}