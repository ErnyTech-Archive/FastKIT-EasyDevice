package easydevice.gui;

import com.jfoenix.controls.JFXProgressBar;
import easydevice.filemanager.FileDownload;
import easydevice.parser.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jlang.javafx.Text;
import jmfx.JmfxLoader;
import jmfx.JmfxRun;
import jmfx.JmfxWaitClose;
import jmfx.util.exception.FxIdMissingException;

import java.nio.file.Path;

public class DownloaderGui {
    private volatile FileDownload fileDownload;
    private Stage stage;

    public DownloaderGui(String link, Path path) {
        this.fileDownload = new FileDownload(link, path);
    }

    public DownloaderGui(File file) {
        this.fileDownload = new FileDownload(file);
    }

    public void start() {
        var loader = new JmfxLoader("download.fxml");
        loader.load();

        var jmfxRun = new JmfxRun<Boolean>();
        var success = jmfxRun.invoke(args -> {
            var scene = new Scene(loader.getParent());

            Text link, file;
            JFXProgressBar progressBar;

            try {
                link = loader.getId("link");
                file = loader.getId("file");
                progressBar = loader.getId("progressBar");
            } catch (FxIdMissingException e) {
                e.printStackTrace();
                return false;
            }

            link.setText(link.getText() + this.fileDownload.getLink());
            file.setText(file.getText() + this.fileDownload.getPath().toAbsolutePath().toString());
            this.stage = new Stage();
            this.stage.setScene(scene);
            this.stage.setMinHeight(148);
            this.stage.setMinWidth(630);
            this.stage.setMaxHeight(280);
            this.stage.show();
            this.fileDownload.call();
            progressBar.progressProperty().bind(this.fileDownload.progressProperty());
            return true;
        });

        if (!success) {
            return;
        }

        new JmfxWaitClose(this.stage).close(this.fileDownload);
        System.out.println("finish");
    }

    public String getLink() {
        return this.fileDownload.getLink();
    }

    public Path getPath() {
        return this.fileDownload.getPath();
    }
}
