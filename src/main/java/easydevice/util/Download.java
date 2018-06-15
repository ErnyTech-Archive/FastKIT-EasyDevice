package easydevice.util;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Download {
    private String link;
    private Scanner scanner;

    public Download(String link) {
        this.link = link;
    }

    public void start() throws IOException {
        var url = new URL(this.link);
        var inputStream = url.openStream();
        var scanner = new Scanner(inputStream,
                StandardCharsets.UTF_8.toString());
        scanner.useDelimiter("\\A");
        this.scanner = scanner;
    }

    public String toString() {
        return this.scanner.hasNext() ? this.scanner.next() : "";
    }
}
