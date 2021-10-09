package com.skht777.markdown.editor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author skht777
 */
public enum LineSeparator {
    CRLF("Windows (CRLF)", "\r\n"),
    LF("Unix (LF)", "\n"),
    CR("Macintosh (CR)", "\r");

    private String name;
    private String separator;

    LineSeparator(String name, String separator) {
        this.name = name;
        this.separator = separator;
    }

    public String getValue() {
        return separator;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Optional<LineSeparator> detectFromString(String str) {
        return Arrays.stream(values()).filter(ls -> str.contains(ls.separator)).findFirst();
    }
}
