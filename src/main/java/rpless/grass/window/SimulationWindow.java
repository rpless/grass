package rpless.grass.window;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import rpless.grass.Simulation;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

/**
 * The {@code SimulationWindow} encapsulates the NEWT window that from JOGL.
 * <p>
 *     It can be started and stopped.
 */
public class SimulationWindow {
    public static final Float WIDTH = 800.0f;
    public static final Float HEIGHT = 600.0f;

    /**
     * The {@code SimulationWindowListener} is a {@link com.jogamp.newt.event.WindowAdapter} that stops the
     * {@link com.jogamp.opengl.util.AnimatorBase} on a window destroy notification.
     */
    private class SimulationWindowListener extends WindowAdapter {
        @Override
        public void windowDestroyNotify(WindowEvent windowEvent) {
            animatorBase.stop();
        }
    }

    private GLWindow window;
    private AnimatorBase animatorBase;

    public SimulationWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL4);
        System.out.println(profile);
        GLCapabilities capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
        window.setTitle("Grass");
        window.setSize(WIDTH.intValue(), HEIGHT.intValue());
        window.setVisible(true);
        window.addWindowListener(new SimulationWindowListener());
    }

    public void addKeyListener(KeyListener keyListener) {
        window.addKeyListener(keyListener);
    }

    public void warpPointer(int i, int i2) {
        window.warpPointer(i, i2);
    }

    public void addGLEventListener(GLEventListener eventListener) {
        window.addGLEventListener(eventListener);
    }

    public void addMouseListener(MouseListener mouseListener) {
        window.addMouseListener(mouseListener);
    }

    /**
     * Start the {@link com.jogamp.opengl.util.AnimatorBase} with the given {@link javax.media.opengl.GLEventListener}.
     */
    public void start() {
        animatorBase = new FPSAnimator(window, 60, true);
        animatorBase.start();
    }

    /**
     * Destroys the window.
     */
    public void stop() {
        window.destroy();
    }
}