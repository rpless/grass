package rpless.grass.input;

import com.jogamp.newt.event.KeyEvent;

import java.util.Arrays;
import java.util.Collection;

public class KeyInputAction {
    private Collection<Recognizer<KeyEvent>> recognizers;

    public KeyInputAction(Recognizer<KeyEvent>... recognizers) {
        this.recognizers = Arrays.asList(recognizers);
    }

    public KeyInputAction(Collection<Recognizer<KeyEvent>> recognizers) {
        this.recognizers = recognizers;
    }

    public boolean isDetected(KeyEvent keyEvent) {
        for (Recognizer<KeyEvent> recognizer : recognizers) {
            if (!recognizer.isRecognized(keyEvent)) {
                return false;
            }
        }
        return true;
    }
}