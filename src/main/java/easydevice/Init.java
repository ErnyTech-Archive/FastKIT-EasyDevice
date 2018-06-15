package easydevice;

import easydevice.parser.ParseConfig;
import easydevice.util.exception.InitException;
import fastkit.core.util.Device;
import jlang.JLang;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Init {
    public static Path fastkitDir;

    public static void start(Device device, String masterLink) {
        JLang.start("langs");
        EasyDevice.setDevice(device);
        var userHome = System.getProperty("user.home");
        if (!userHome.endsWith(File.separator)) {
            userHome += File.separator;
        }

        Init.fastkitDir = Paths.get(userHome + ".fastkit");

        if(!Files.isDirectory(Init.fastkitDir)) {
            if (Files.exists(Init.fastkitDir)) {
                try {
                    Files.delete(Init.fastkitDir);
                } catch (IOException e) {
                    throw new InitException();
                }
            }

            try {
                Files.createDirectories(Init.fastkitDir);
            } catch (IOException e) {
                throw new InitException();
            }

            try {
                Files.setAttribute(Init.fastkitDir, "\"dos:hidden\"", true, LinkOption.NOFOLLOW_LINKS);
            } catch (UnsupportedOperationException ignored) {
            } catch (IOException e) {
                throw new InitException();
            }
        }

        ParseConfig parseConfig = new ParseConfig();
        try {
            parseConfig.parse(masterLink);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new InitException();
        }
    }
}
