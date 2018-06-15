package easydevice.filemanager;

import easydevice.Init;
import easydevice.parser.File;
import javafx.concurrent.Task;

import java.io.*;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CancellationException;

public class FileDownload extends Task<Void> {
    private final int DATALENGTH = 8192;
    private String link;
    private Path path;
    private boolean isPause = false;

    public FileDownload(String link, Path path) {
        this.link = link;
        this.path = path;
    }

    public FileDownload(File file) {
        this.link = file.getLink();
        this.path = Paths.get(Init.fastkitDir.toUri().getPath(), file.getFileName());
    }

    @Override
    public Void call() {
        new Thread(this::downloader).start();
        return null;
    }

    private void downloader() {
        InputStream in = null;
        OutputStream out = null;
        try {
            var url = new URL(this.link);
            var urlconnection = url.openConnection();
            var size = urlconnection.getContentLength();
            in = new BufferedInputStream(urlconnection.getInputStream());
            out = new FileOutputStream(this.path.toFile());
            byte data[] = new byte[this.DATALENGTH];
            Double currSize = 0.0;

            while (true) {
                if (isCancelled()) {
                    throw new CancellationException("Download is deleted");
                }

                if (this.isPause) {
                    continue;
                }

                var cout = in.read(data, 0, this.DATALENGTH);

                if (cout == -1) {
                    break;
                }

                out.write(data, 0, cout);
                currSize = currSize + cout;
                var percentace = (currSize / size) * 100;
                updateProgress(percentace, 100);
            }
        } catch (IOException | CancellationException e) {
            failed();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pause() {
        this.isPause = true;
    }

    public void waitEnd() {
        while (true) {
            if (getProgress() == 1) {
                break;
            }
        }
    }

    public String getLink() {
        return this.link;
    }

    public Path getPath() {
        return this.path;
    }
}
