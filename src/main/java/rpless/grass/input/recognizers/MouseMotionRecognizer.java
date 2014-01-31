package rpless.grass.input.recognizers;

import com.jogamp.newt.event.MouseEvent;
import rpless.grass.input.Recognizer;

public class MouseMotionRecognizer implements Recognizer<MouseEvent> {
    @Override
    public boolean isRecognized(MouseEvent type) {
        return type.getEventType() == MouseEvent.EVENT_MOUSE_MOVED;
    }
}
