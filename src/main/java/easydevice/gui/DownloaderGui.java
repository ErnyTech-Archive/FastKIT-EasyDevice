package easydevice.gui;

import com.jfoenix.controls.JFXProgressBar;
import easydevice.filemanager.FileDownload;
import easydevice.parser.File;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jlang.javafx.Text;

import java.io.IOException;
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
        Platform.startup(() -> {
            var loader = new FXMLLoader(getClass().getResource("download.fxml"));
            Parent parent = null;

            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            var scene = new Scene(parent);
            var link = (Text) loader.getNamespace().get("link");
            var file = (Text) loader.getNamespace().get("file");
            var progressBar = (JFXProgressBar) loader.getNamespace().get("progressBar");
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
        });
        this.fileDownload.waitEnd();

        Platform.runLater(() -> {
            this.stage.hide();
            this.stage.close();
        });
    }

    public String getLink() {
        return this.fileDownload.getLink();
    }

    public Path getPath() {
        return this.fileDownload.getPath();
    }
}
