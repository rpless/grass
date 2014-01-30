package rpless.grass.input;

import com.jogamp.newt.event.InputEvent;

public interface Recognizer<T extends InputEvent> {
    public boolean isRecognized(T type);
}