package com.skht777.markdown;

import com.skht777.markdown.editor.CharacterStream;
import com.skht777.markdown.editor.TabController;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class MenuBarController implements Initializable {
    private EditorGroupController editor;
    private Window window;

    void setWindow(Window window) {
        this.window = window;
    }

    void setEditor(EditorGroupController editor) {
        this.editor = editor;
    }

    private void save(File file, String value) {
        try {
            if (Objects.isNull(value)) {
                throw new IOException("Text not exists");
            }
            Files.write(file.toPath(), value.getBytes());
            ObjectProperty<File> property = editor.getSelectedTab().getFileProperty();
            if (!property.isEqualTo(file).get()) {
                property.setValue(file);
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open").show();
            e.printStackTrace();
        }
    }

    @FXML
    private void open() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All types", "*.*"));
        File file = fc.showOpenDialog(window);
        if (Objects.isNull(file)) {
            return;
        }
        try {
            TabController tab = editor.getSelectedTab();
            tab.getEditor().getTextProperty().setValue(CharacterStream.decodeString(file));
            tab.getFileProperty().setValue(file);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to open").show();
            e.printStackTrace();
        }
    }

    @FXML
    private void print() {
        editor.getSelectedTab().getPreview().print(window);
    }

    @FXML
    private void save() {
        TabController tab = editor.getSelectedTab();
        if (tab.getFileProperty().isNotNull().get()) {
            save(tab.getFileProperty().getValue(), tab.getEditor().getTextProperty().getValueSafe());
        } else {
            saveWithName();
        }
    }

    @FXML
    private void saveAsHTML() {
        saveWithName(editor.getSelectedTab().getPreview().getCurrentHTML());
    }

    @FXML
    private void saveWithName() {
        saveWithName(editor.getSelectedTab().getEditor().getTextProperty().getValueSafe());
    }

    private void saveWithName(String content) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All types", "*.*"));
        fc.setInitialFileName(editor.getSelectedTab().getNameProperty().get());
        Optional.ofNullable(fc.showSaveDialog(window)).ifPresent(file -> save(file, content));
    }

    @FXML
    private void addTab() {
        editor.getTabs().add(editor.createTab());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
