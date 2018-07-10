package easydevice;

import easydevice.parser.File;
import easydevice.util.exception.CantGetDeviceInfoException;
import easydevice.util.exception.DeviceConfigMissing;
import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.util.Device;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class EasyDevice {
    private static Device device;
    private static boolean unlockneedcode = false;
    private static File twrp;
    private static File stockRecovery;
    private static String unlockcode;
    private static final String masterLink = "https://raw.githubusercontent.com/ErnyTech/Fastkit-DevicesConfig/master/master.xml";

    public static void start(Device device, String masterLink) throws CantGetDeviceInfoException, DeviceConfigMissing {
        Init.start(device, masterLink);
    }

    public static void start(Device device) throws CantGetDeviceInfoException, DeviceConfigMissing {
        start(device, EasyDevice.masterLink);
    }

    public static void setDevice(Device device) {
        EasyDevice.device = device;
    }

    public static void setUnlockneedcode(boolean unlockneedcode) {
        EasyDevice.unlockneedcode = unlockneedcode;
    }

    public static void setUnlockcode(String unlockcode) {
        EasyDevice.unlockcode = unlockcode;
    }

    public static void setTwrp(File twrp) {
        EasyDevice.twrp = twrp;
    }

    public static void setStockRecovery(File stockRecovery) {
        EasyDevice.stockRecovery = stockRecovery;
    }

    public static Device getDevice() {
        return EasyDevice.device;
    }

    public static Mode getDeviceMode() {
        update();
        return EasyDevice.getDevice().getDeviceMode();
    }

    public static String getDeviceSerial() {
        return EasyDevice.getDevice().getDeviceSerial();
    }

    public static String getDeviceModel() {
        update();
        return EasyDevice.getDevice().getDeviceModel();
    }

    public static String getDeviceProduct() {
        update();
        return EasyDevice.getDevice().getDeviceProduct();
    }
    public static String getUnlockcode() {
        return EasyDevice.unlockcode;
    }

    public static boolean getUnlockneedcode() {
        return EasyDevice.unlockneedcode;
    }

    public static File getTwrp() {
        return EasyDevice.twrp;
    }

    public static File getStockRecovery() {
        return EasyDevice.stockRecovery;
    }

    private static void update() {
        var deviceAdb = updateDeviceAdb();
        var deviceFastboot = updateDeviceFastboot();

        if (deviceAdb != null && deviceFastboot != null) {
            return;
        }

        if (deviceAdb != null) {
            EasyDevice.setDevice(deviceAdb);
        }

        if (deviceFastboot != null) {
            EasyDevice.setDevice(deviceFastboot);
        }
    }

    private static Device updateDeviceAdb() {
        var getDevices = new GetDevices();

        try {
            getDevices.exec();
        } catch (InterruptedException | IOException | CommandErrorException e) {
            return null;
        }

        var devices = getDevices.get();
        for (Device device : devices) {
            if (device.getDeviceSerial().equals(EasyDevice.getDeviceSerial())) {
                var mode = device.getDeviceMode();
                var serial = EasyDevice.getDeviceSerial();
                String model = EasyDevice.getDevice().getDeviceModel();
                String product = EasyDevice.getDevice().getDeviceProduct();

                if (model == null) {
                    model = device.getDeviceModel();
                }

                if (product == null) {
                    product = device.getDeviceProduct();
                }

                return new Device(mode, serial, model, product);
            }
        }
        return null;
    }

    private static Device updateDeviceFastboot() {
        var getDevices = new fastkit.core.fastboot.GetDevices();

        try {
            getDevices.exec();
        } catch (InterruptedException | IOException | CommandErrorException e) {
            return null;
        }

        var devices = getDevices.get();
        for (Device device : devices) {
            if (device.getDeviceSerial().equals(EasyDevice.getDeviceSerial())) {
                var mode = device.getDeviceMode();
                var serial = EasyDevice.getDeviceSerial();
                String model = EasyDevice.getDevice().getDeviceModel();
                String product = EasyDevice.getDevice().getDeviceProduct();

                if (model == null) {
                    model = device.getDeviceModel();
                }

                if (product == null) {
                    product = device.getDeviceProduct();
                }

                return new Device(mode, serial, model, product);
            }
        }
        return null;
    }
}
