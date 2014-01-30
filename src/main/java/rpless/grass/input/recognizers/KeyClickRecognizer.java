package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;


public class KeyClickRecognizer implements Recognizer<KeyEvent> {
    boolean pressed = false;

    @Override
    public boolean isRecognized(KeyEvent type) {
        if (type.getEventType() == KeyEvent.EVENT_KEY_RELEASED && !type.isAutoRepeat()) {
            pressed = false;
        } else if (type.getEventType() == KeyEvent.EVENT_KEY_PRESSED) {
            pressed = true;
        }
        return pressed;
    }
}