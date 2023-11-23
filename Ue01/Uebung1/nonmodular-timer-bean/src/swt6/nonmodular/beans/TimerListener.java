package swt6.nonmodular.beans;

import java.util.EventListener;

public interface TimerListener extends EventListener {
    void expired(TimerEvent evt);
}
