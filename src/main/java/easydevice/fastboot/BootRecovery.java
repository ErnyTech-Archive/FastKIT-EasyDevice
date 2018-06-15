package easydevice.fastboot;

import easydevice.EasyDevice;
import easydevice.filemanager.GetFile;
import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.adb.WaitBoot;
import fastkit.core.adb.autoreboot.AutoReboot;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class BootRecovery implements GenericApi {
    private Logger logger = new Logger();
    private fastkit.core.fastboot.BootRecovery bootRecovery;

    public BootRecovery(Recovery recovery) {
        switch (recovery) {
            case twrp: {
                var file = EasyDevice.getTwrp();
                var recoveryFile = new GetFile(file).get().toFile();
                this.bootRecovery = new fastkit.core.fastboot.BootRecovery(recoveryFile);
                break;
            }
            case stock: {
                var file = EasyDevice.getStockRecovery();
                var recoveryFile = new GetFile(file).get().toFile();
                this.bootRecovery = new fastkit.core.fastboot.BootRecovery(recoveryFile);
                break;
            }
        }
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        var autoReboot = new AutoReboot(Mode.fastboot, EasyDevice.getDeviceMode());
        autoReboot.exec();
        this.logger.add(autoReboot);
        this.bootRecovery.exec();
        this.logger.add(bootRecovery);
        var waitRecovery = new WaitBoot(Mode.recovery);
        waitRecovery.exec();
        this.logger.add(waitRecovery);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
