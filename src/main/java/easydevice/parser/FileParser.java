package easydevice.parser;

import org.w3c.dom.Element;

public class FileParser {
    private Element fileElement;

    public FileParser(Element fileElement) {
        this.fileElement = fileElement;
    }

    public File getFile() {
        var link = this.fileElement.getElementsByTagName("link").item(0).getTextContent();
        var sha256sum = this.fileElement.getElementsByTagName("sha256sum").item(0).getTextContent();
        return new File(link, sha256sum);
    }
}
