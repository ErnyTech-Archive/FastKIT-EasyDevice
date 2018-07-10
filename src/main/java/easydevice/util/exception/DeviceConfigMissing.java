package easydevice.util.exception;

import easydevice.EasyDevice;

public class DeviceConfigMissing extends Exception {
    public DeviceConfigMissing() {
        super("Device: " + EasyDevice.getDeviceModel() + ":" + EasyDevice.getDeviceProduct() + " missing in device configs");
    }
}
