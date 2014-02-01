package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;

public class KeyCodeRecognizer implements Recognizer<KeyEvent> {
    private short code;

    public KeyCodeRecognizer(short code) {
        this.code = code;
    }

    @Override
    public boolean isRecognized(KeyEvent event) {
        return event.getKeyCode() == code;
    }
}