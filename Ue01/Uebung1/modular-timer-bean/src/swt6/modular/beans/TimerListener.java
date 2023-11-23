package swt6.modular.beans;

import java.util.EventListener;

public interface TimerListener extends EventListener {
    void expired(TimerEvent evt);
}
