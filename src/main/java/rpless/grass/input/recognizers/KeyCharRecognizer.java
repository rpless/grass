package rpless.grass.input.recognizers;

import com.jogamp.newt.event.KeyEvent;
import rpless.grass.input.Recognizer;

public class KeyCharRecognizer implements Recognizer<KeyEvent> {
    private char keychar;

    public KeyCharRecognizer(char keychar) {
        this.keychar = keychar;
    }

    @Override
    public boolean isRecognized(KeyEvent event) {
        return event.getKeyChar() == keychar;
    }
}