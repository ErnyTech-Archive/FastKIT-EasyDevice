package easydevice.filemanager;

import easydevice.parser.File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256sum {
    private Path path;

    public Sha256sum(Path path) {
        this.path = path;
    }

    public String get() throws IOException {
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No sha256");
        }
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.path.toFile()));
        while ((count = bis.read(buffer)) > 0) {
            digest.update(buffer, 0, count);
        }
        bis.close();

        byte[] hash = digest.digest();

        return bytesToHex(hash);
    }

    public boolean check(File file) throws IOException {
        return file.getSha256sum().equals(get());
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
