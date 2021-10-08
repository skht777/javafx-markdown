package com.skht777.markdown;

import com.skht777.markdown.editor.PreviewController;
import com.skht777.markdown.editor.TextAreaController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class TabController implements Initializable {
    @FXML
    private Tab tab;
    @FXML
    private ObjectProperty<File> fileProperty;
    @FXML
    private PreviewController previewController;
    @FXML
    private TextAreaController textAreaController;

    PreviewController getPreview() {
        return previewController;
    }

    TextAreaController getEditor() {
        return textAreaController;
    }

    StringProperty getNameProperty() {
        return tab.textProperty();
    }

    ObjectProperty<File> getFileProperty() {
        return fileProperty;
    }

    void setFileWithLoad(File file) throws IOException {
        fileProperty.setValue(file);
        loadCurrentFile();
    }

    void loadCurrentFile() throws IOException {
        textAreaController.setStringFromFile(fileProperty.get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileProperty.addListener((v, o, n) -> Optional.ofNullable(n).map(File::getName)
                .ifPresent(tab::setText));
        previewController.setTextProperty(textAreaController.getTextProperty());
    }
}
