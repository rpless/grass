package rpless.grass.input;

import com.jogamp.newt.event.MouseEvent;

import java.util.Arrays;
import java.util.Collection;

public class MouseMotionInputAction {
    private Collection<Recognizer<MouseEvent>> recognizers;
    private int x, y;

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
}