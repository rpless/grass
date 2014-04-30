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
        boolean isMatched = recognizers.stream().allMatch(recognizer -> recognizer.isRecognized(event));
        if (isMatched) {
            x = event.getX();
            y = event.getY();
        }
        return isMatched;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}