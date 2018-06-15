package easydevice.filemanager;

import easydevice.Init;
import easydevice.gui.DownloaderGui;
import easydevice.parser.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFile {
    private File file;

    public GetFile(File file) {
        this.file = file;
    }

    public Path get()  {
        Path path = Paths.get(Init.fastkitDir.toUri().getPath(), this.file.getFileName());

        if (!Files.exists(path)) {
            return downloadFile(this.file);
        }

        var sha256sum = new Sha256sum(path);

        try {
            if (!sha256sum.check(this.file)) {
                return downloadFile(this.file);
            }
        } catch (IOException e) {
            return downloadFile(this.file);
        }

        return path;
    }

    private Path downloadFile(File file)  {
        while (true) {
            var fileDownloader = new DownloaderGui(file);
            fileDownloader.start();
            var sha256sum = new Sha256sum(fileDownloader.getPath());

            try {
                if (sha256sum.check(file)) {
                    return fileDownloader.getPath();
                }
            } catch (IOException e) {
                continue;
            }
        }
    }
}
