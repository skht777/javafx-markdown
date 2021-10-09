package com.skht777.markdown.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class TextAreaController implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private ObjectProperty<Charset> encodingProperty;
    @FXML
    private ObjectProperty<LineSeparator> lineSeparatorProperty;

    public StringProperty getTextProperty() {
        return textArea.textProperty();
    }

    public ObjectProperty<Charset> getEncodingProperty() {
        return encodingProperty;
    }

    public ObjectProperty<LineSeparator> getLineSeparatorProperty() {
        return lineSeparatorProperty;
    }

    public void setStringFromFile(File file) throws IOException {
        var cs = new CharacterStream(file);
        textArea.textProperty().setValue(cs.getString());
        encodingProperty.setValue(cs.getCharset());
        lineSeparatorProperty.setValue(LineSeparator.detectFromString(cs.getString()).orElse(LineSeparator.LF));
    }

    public void init() {
        encodingProperty.setValue(Charset.defaultCharset());
        lineSeparatorProperty.setValue(LineSeparator.LF);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
