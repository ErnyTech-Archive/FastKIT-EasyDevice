package easydevice.parser;

import easydevice.EasyDevice;
import fastkit.core.util.Device;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DeviceParser {
    private Document document;
    private Element productElement;

    public DeviceParser(Document document, String deviceProduct) {
        this.document = document;
        this.productElement = getProduct(deviceProduct);
        if (this.productElement == null) {
            throw new RuntimeException("Product device don't found!");
        }
    }

    public void setEasyDevice() {
        var unlockneedcode = getUnlockneedcode();
        String unlockcode;

        if (unlockneedcode) {
            unlockcode = getUnlockcode();
        }

        var twrp = getTwrp();
        var stockRecovery = getStockRecovery();

        EasyDevice.setUnlockneedcode(unlockneedcode);
        EasyDevice.setTwrp(twrp);
        EasyDevice.setStockRecovery(stockRecovery);
    }

    private boolean getUnlockneedcode() {
        return Boolean.parseBoolean(this.productElement.getElementsByTagName("unlockneedcode").item(0).getTextContent());
    }

    private String getUnlockcode() {
        return this.productElement.getElementsByTagName("unlockcode").item(0).getTextContent();
    }

    private File getTwrp() {
        Element twrpElement = (Element) this.productElement.getElementsByTagName("twrp").item(0);
        FileParser fileParser = new FileParser(twrpElement);
        return fileParser.getFile();
    }

    private File getStockRecovery() {
        Element stockRecoveryElement = (Element) this.productElement.getElementsByTagName("stockrecovery").item(0);
        FileParser fileParser = new FileParser(stockRecoveryElement);
        return fileParser.getFile();
    }

    private Element getProduct(String deviceProduct) {
        this.document.normalizeDocument();
        NodeList deviceList = this.document.getElementsByTagName("model");

        for (int i = 0; i < deviceList.getLength(); i++) {
            var product = deviceList.item(i).getAttributes().getNamedItem("product").getNodeValue();
            var model = (org.w3c.dom.Element) deviceList.item(i);

            if (product.equals(deviceProduct)) {
                return model;
            }
        }
        return null;
    }
}
