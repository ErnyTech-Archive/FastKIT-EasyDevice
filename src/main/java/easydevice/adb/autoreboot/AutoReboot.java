package easydevice.adb.autoreboot;

import easydevice.EasyDevice;
import easydevice.fastboot.Recovery;
import easydevice.filemanager.GetFile;
import fastkit.core.adb.Mode;

import java.io.File;

public class AutoReboot extends fastkit.core.adb.autoreboot.AutoReboot {

    public AutoReboot(Mode toMode) {
        super(toMode, EasyDevice.getDeviceMode(), getRecovery(Recovery.twrp));
    }

    public AutoReboot(Mode toMode, Recovery recovery) {
        super(toMode, EasyDevice.getDeviceMode(), getRecovery(recovery));
    }

    private static File getRecovery(Recovery recovery) {
        switch (recovery) {
            case twrp: {
                var file = EasyDevice.getTwrp();
                return new GetFile(file).get().toFile();
            }

            case stock: {
                var file = EasyDevice.getStockRecovery();
                return new GetFile(file).get().toFile();
            }

            default: {
                return null;
            }
        }
    }
}
