package easydevice.util.exception;

public class CantGetDeviceInfoException extends Exception {
    public CantGetDeviceInfoException() {
        super("FastKIT-EasyDevice can't get device info! Probably phone is in fastboot mode or not plugged!");
    }
}
