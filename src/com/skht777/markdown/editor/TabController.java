package com.skht777.markdown.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class TabController implements Initializable {
    private ObjectProperty<File> fileProperty;
    @FXML
    private StringProperty nameProperty;
    @FXML
    private PreviewController previewController;
    @FXML
    private TextAreaController textAreaController;

    public PreviewController getPreview() {
        return previewController;
    }

    public TextAreaController getEditor() {
        return textAreaController;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public ObjectProperty<File> getFileProperty() {
        return fileProperty;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileProperty = new SimpleObjectProperty<>();
        fileProperty.addListener((v, o, n) -> Optional.ofNullable(n).map(File::getName)
                .ifPresent(nameProperty::setValue));
        getPreview().setTextProperty(getEditor().getTextProperty());
    }
}
