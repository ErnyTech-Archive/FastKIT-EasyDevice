package easydevice.parser;

import easydevice.EasyDevice;
import easydevice.util.Download;
import easydevice.util.exception.CantGetDeviceInfoException;
import easydevice.util.exception.DeviceConfigMissing;
import fastkit.core.util.Device;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ParseConfig {
    public void parse(String masterLink) throws IOException, ParserConfigurationException, SAXException, CantGetDeviceInfoException, DeviceConfigMissing {
        if (EasyDevice.getDeviceModel() == null || EasyDevice.getDeviceProduct() == null) {
            throw new CantGetDeviceInfoException();
        }

        var download = new Download(masterLink);
        download.start();
        Parser parser = new Parser();
        var devicelink = parser.masterParser(download.toString());

        if (devicelink == null) {
            throw new DeviceConfigMissing();
        }

        download = new Download(devicelink);
        download.start();
        parser.deviceParse(download.toString());
    }
}
