package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author skht777
 */
public class EditorTab extends Tab {
    private static int countNew = 1;

    @FXML
    private EditorController editorController;
    private File file;

    public EditorTab() {
        this(null);
    }

    public EditorTab(File file) {
        try {
            FXMLLoader loader = new FXMLLoader(EditorTab.class.getResource("fxml/tab.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            Optional.ofNullable(file).ifPresentOrElse(this::open, () -> setText("New " + countNew++));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFile(File file) {
        this.file = file;
        setText(file.getName());
    }

    public EditorController getEditor() {
        return editorController;
    }

    public boolean hasFile() {
        return Optional.ofNullable(file).isPresent();
    }

    public void open(File file) {
        try {
            editorController.convertMarkdown(CharacterStream.decodeString(file));
            setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        save(file);
    }

    public void save(File file) {
        try {
            Files.write(file.toPath(), editorController.getText().getBytes());
            setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
