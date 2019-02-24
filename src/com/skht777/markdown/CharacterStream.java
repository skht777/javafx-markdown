package com.skht777.markdown;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author skht777
 */
public class CharacterStream {
    private Charset charset;
    private String decoded;

    public CharacterStream(File file, Charset charset) throws IOException {
        byte[] buff = Files.readAllBytes(file.toPath());
        this.charset = Optional.ofNullable(charset).orElse(detectEncoding(buff));
        decoded = new String(buff, this.charset);
    }

    public CharacterStream(File file) throws IOException {
        this(file, null);
    }

    public static String decodeString(File file, Charset charset) throws IOException {
        return new CharacterStream(file, charset).getString();
    }

    public static String decodeString(File file) throws IOException {
        return new CharacterStream(file).getString();
    }

    public String getString() {
        return decoded;
    }

    public Charset getCharset() {
        return charset;
    }

    private Charset detectEncoding(byte[] buff) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(buff, 0, buff.length);
        detector.dataEnd();
        return Charset.forName(Optional.ofNullable(detector.getDetectedCharset()).orElse("UTF-8"));
    }
}
