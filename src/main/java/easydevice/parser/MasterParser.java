package easydevice.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MasterParser {
    private NodeList devices;
    private Document document;

    public MasterParser(Document document) {
        this.document = document;
    }

    public String getLink(String deviceModel) {
        this.document.normalizeDocument();
        NodeList deviceList = this.document.getElementsByTagName("device");

        for (int i = 0; i < deviceList.getLength(); i++) {
            var model = deviceList.item(i).getAttributes().getNamedItem("model").getNodeValue();
            var device = (org.w3c.dom.Element) deviceList.item(i);

            if (model.equals(deviceModel)) {
                return device.getElementsByTagName("config").item(0).getTextContent();
            }
        }
        return null;
    }
}
