package easydevice.fastboot;

import easydevice.EasyDevice;
import fastkit.core.GenericApi;
import fastkit.core.fastboot.LockState;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class SetLock implements GenericApi {
    private fastkit.core.fastboot.SetLock setLock;
    private Logger logger = new Logger();

    public SetLock(LockState lockState) {
        if (EasyDevice.getUnlockneedcode()) {
            this.setLock = new fastkit.core.fastboot.SetLock(lockState, EasyDevice.getUnlockcode());
        } else {
            this.setLock = new fastkit.core.fastboot.SetLock(lockState);
        }
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.setLock.exec();
        this.logger.add(this.setLock);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
