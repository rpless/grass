package rpless.grass;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import rpless.grass.input.MouseMotionInputAction;
import rpless.grass.input.recognizers.MouseMotionRecognizer;
import rpless.grass.window.SimulationWindow;

public class MouseHandler extends MouseAdapter {

    private SimulationWindow window;
    private Camera camera;
    private boolean recentering = false;

    MouseMotionInputAction motion = new MouseMotionInputAction(new MouseMotionRecognizer());

    public MouseHandler(SimulationWindow window, Camera camera) {
        this.window = window;
        this.camera = camera;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int dx = mouseEvent.getX() - (int) (SimulationWindow.WIDTH / 2);
        int dy = mouseEvent.getY() - (int) (SimulationWindow.HEIGHT / 2);
        if (recentering && dx == 0 && dy == 0) {
            recentering = false;
        } else if (motion.isDetected(mouseEvent)) {
            if (dy != 0) camera.pitch(dy > 0 ? -0.01f : 0.01f);
            if (dx != 0) camera.yaw(dx > 0 ? 0.01f : -0.01f);
            recenter();
        }
    }

    private void recenter() {
        recentering = true;
        window.warpPointer(400, 300);
    }
}