package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;

public class KeyReleaseRecognizer implements Recognizer<KeyEvent> {

    @Override
    public boolean isRecognized(KeyEvent type) {
        return type.getEventType() == KeyEvent.EVENT_KEY_RELEASED && !type.isAutoRepeat();
    }
}