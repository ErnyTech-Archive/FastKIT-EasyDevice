package easydevice.fastboot;

import easydevice.EasyDevice;
import easydevice.adb.autoreboot.AutoReboot;
import easydevice.filemanager.GetFile;
import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class FlashRecovery implements GenericApi {
    private Recovery recovery;
    private Logger logger = new Logger();
    private fastkit.core.fastboot.FlashRecovery flashRecovery;

    public FlashRecovery(Recovery recovery) {
        this.recovery = recovery;
        switch (recovery) {
            case twrp: {
                var file = EasyDevice.getTwrp();
                var recoveryFile = new GetFile(file).get().toFile();
                this.flashRecovery = new fastkit.core.fastboot.FlashRecovery(recoveryFile);
                break;
            }
            case stock: {
                var file = EasyDevice.getStockRecovery();
                var recoveryFile = new GetFile(file).get().toFile();
                this.flashRecovery = new fastkit.core.fastboot.FlashRecovery(recoveryFile);
                break;
            }
        }
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        var autoReboot = new AutoReboot(Mode.fastboot);
        autoReboot.exec();
        this.logger.add(autoReboot);
        this.flashRecovery.exec();
        this.logger.add(flashRecovery);
        var bootRecovery = new BootRecovery(this.recovery);
        bootRecovery.exec();
        this.logger.add(bootRecovery);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
