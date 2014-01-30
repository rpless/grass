package rpless.grass.input;

import com.jogamp.newt.event.KeyEvent;

import java.util.Arrays;
import java.util.Collection;

public class KeyInputAction {
    private Collection<Recognizer<KeyEvent>> recognizers;
    private boolean detected = false;

    public KeyInputAction(Recognizer<KeyEvent>... recognizers) {
        this.recognizers = Arrays.asList(recognizers);
    }

    public KeyInputAction(Collection<Recognizer<KeyEvent>> recognizers) {
        this.recognizers = recognizers;
    }

    public boolean isDetected() {
        return detected;
    }

    public void applyRecognizers(KeyEvent keyEvent) {
        for (Recognizer<KeyEvent> recognizer : recognizers) {
            if (!recognizer.isRecognized(keyEvent)) {
                detected = false;
                return;
            }
        }
        detected = true;
    }
}