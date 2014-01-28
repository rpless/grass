package rpless.grass.window;

import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

public class SimulationWindow {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private class SimulationWindowListener extends WindowAdapter {
        @Override
        public void windowDestroyNotify(WindowEvent windowEvent) {
            animatorBase.stop();
        }
    }

    private GLWindow window;
    private AnimatorBase animatorBase;

    public SimulationWindow() {
        GLProfile profile = GLProfile.getDefault();
        System.out.println(profile);
        GLCapabilities capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
        window.setTitle("Grass");
        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
        window.addWindowListener(new SimulationWindowListener());
    }

    public void addKeyListener(KeyListener keyListener) {
        window.addKeyListener(keyListener);
    }

    public void start(GLEventListener eventListener) {
        window.addGLEventListener(eventListener);
        animatorBase = new FPSAnimator(window, 60, true);
        animatorBase.start();
    }

    public void stop() {
        window.destroy();
    }
}