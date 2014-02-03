package rpless.grass.input;

import com.jogamp.newt.event.MouseEvent;

import java.util.Arrays;
import java.util.Collection;

public class MouseMotionInputAction {
    private Collection<Recognizer<MouseEvent>> recognizers;
    private int x, y, deltaX, deltaY;

    public MouseMotionInputAction(Recognizer<MouseEvent>... recognizers) {
        this.recognizers = Arrays.asList(recognizers);
    }

    public MouseMotionInputAction(Collection<Recognizer<MouseEvent>> recognizers) {
        this.recognizers = recognizers;
    }

    public boolean isDetected(MouseEvent event) {
        for (Recognizer<MouseEvent> recognizer : recognizers) {
            if (!recognizer.isRecognized(event)) {
                return false;
            }
        }
        deltaX = event.getX() - getX();
        deltaY = event.getY() - getY();
        System.out.println("(" + event.getX() + "," + event.getY() + ") Delta: (" + deltaX + "," + deltaY + ")");
        x = event.getX();
        y = event.getY();
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}