/**
 *
 */
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

    /**
     * @param file    - 解析するファイル
     * @param charset - 文字コード
     * @throws IOException
     */
    public CharacterStream(File file, Charset charset) throws IOException {
        byte[] buff = Files.readAllBytes(file.toPath());
        this.charset = Optional.ofNullable(charset).orElse(detectEncoding(buff));
        decoded = new String(buff, charset);
    }

    /**
     * @param file - 解析するファイル
     * @throws IOException
     */
    public CharacterStream(File file) throws IOException {
        this(file, null);
    }

    /**
     * @param file    - 解析するファイル
     * @param charset - 文字コード
     * @return デコードされた文字列
     */
    public static String decodeString(File file, Charset charset) throws IOException {
        return new CharacterStream(file, charset).getString();
    }

    /**
     * @param file - 解析するファイル
     * @return デコードされた文字列
     * @throws IOException
     */
    public static String decodeString(File file) throws IOException {
        return new CharacterStream(file).getString();
    }

    public String getString() {
        return decoded;
    }

    public Charset getCharset() {
        return charset;
    }

    /**
     * 文字コードの自動判別を行う。
     *
     * @param buff - 文字列のバイトコード
     * @return 判別した文字コード 判別できない場合はUTF-8
     */
    private Charset detectEncoding(byte[] buff) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(buff, 0, buff.length);
        detector.dataEnd();    // データ終わった
        return Charset.forName(Optional.ofNullable(detector.getDetectedCharset()).orElse("UTF-8"));
    }

}
