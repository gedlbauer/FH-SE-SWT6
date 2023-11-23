package swt6.modular.beans;

import java.util.EventObject;

public class TimerEvent extends EventObject {
    private int noTicks;
    private int tickCount;

    public TimerEvent(Object source, int tickCount, int noTicks) {
        super(source);
        this.noTicks = noTicks;
        this.tickCount = tickCount;
    }

    public int getNoTicks() {
        return noTicks;
    }

    public int getTickCount() {
        return tickCount;
    }
}
