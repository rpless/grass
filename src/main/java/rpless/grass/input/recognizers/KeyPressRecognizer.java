package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;

/**
 * Created by ryan on 1/30/14.
 */
public class KeyPressRecognizer implements Recognizer<KeyEvent> {
    @Override
    public boolean isRecognized(KeyEvent type) {
        return type.getEventType() == KeyEvent.EVENT_KEY_PRESSED;
    }
}
