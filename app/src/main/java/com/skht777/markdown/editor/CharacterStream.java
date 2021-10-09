package com.skht777.markdown.editor;

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
    private final Charset charset;
    private final String decoded;

    public CharacterStream(File file, Charset charset) throws IOException {
        byte[] buff = Files.readAllBytes(file.toPath());
        this.charset = Optional.ofNullable(charset).orElse(detectEncoding(buff));
        decoded = new String(buff, this.charset);
    }

    public CharacterStream(File file) throws IOException {
        this(file, null);
    }

    public static String decodeString(File file, Charset charset) throws IOException {
        return new CharacterStream(file, charset).decoded;
    }

    public static String decodeString(File file) throws IOException {
        return decodeString(file, null);
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
        return Optional.ofNullable(detector.getDetectedCharset()).map(Charset::forName)
                .orElse(Charset.defaultCharset());
    }
}
