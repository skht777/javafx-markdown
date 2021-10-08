package com.skht777.markdown.editor;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class TextAreaController implements Initializable {
    @FXML
    private TextArea textArea;

    public StringProperty getTextProperty() {
        return textArea.textProperty();
    }

    public void setStringFromFile(File file) throws IOException {
        getTextProperty().setValue(CharacterStream.decodeString(file));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
