package easydevice.parser;

public class File {
    private String link;
    private String sha256sum;

    public File(String link, String sha256sum) {
        this.link = link;
        this.sha256sum = sha256sum;
    }

    public String getLink() {
        return this.link;
    }

    public String getSha256sum() {
        return this.sha256sum;
    }

    public String getFileName() {
        var links = this.link.split("/");
        return links[links.length-1];
    }
}
