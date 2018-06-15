package easydevice.parser;

import easydevice.EasyDevice;
import easydevice.util.Download;
import fastkit.core.util.Device;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class Parser {
    public String masterParser(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        var masterParser = new MasterParser(document);
        return masterParser.getLink(EasyDevice.getDeviceModel());
    }

    public void deviceParse(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        var deviceParser = new DeviceParser(document, EasyDevice.getDeviceProduct());
        deviceParser.setEasyDevice();
    }
}
