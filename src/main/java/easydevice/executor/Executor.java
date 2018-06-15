package easydevice.executor;

import easydevice.EasyDevice;
import fastkit.core.adb.Mode;
import fastkit.core.executor.Callback;

public abstract class Executor extends fastkit.core.executor.Executor {
    public Executor() {
        super(EasyDevice.getDeviceMode());
    }

    public void run(Callback callback) {
        start(EasyDevice.getDevice(), callback);
    }
}
