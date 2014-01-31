package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;

public class KeyPressRecognizer implements Recognizer<KeyEvent> {
    @Override
    public boolean isRecognized(KeyEvent type) {
        return type.getEventType() == KeyEvent.EVENT_KEY_PRESSED;
    }
}
