package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author skht777
 */
public class EditorTab extends Tab {
    private static int countNew = 1;
    private String name;

    @FXML
    private EditorController editorController;
    private File file;

    private EditorTab(String name) {
        super(name);
        this.name = name;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/tab.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorTab(File file) {
        this(file.getName());
        this.file = file;
        try {
            CharacterStream stream = new CharacterStream(file);
            editorController.setText(stream.getString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorTab() {
        this("New " + countNew++);
    }

    public void print() {
        editorController.print();
    }

    public boolean hasFile() {
        return Optional.ofNullable(file).isPresent();
    }

    public void saveWithName(Window parent) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName(name);
        Optional.ofNullable(fc.showSaveDialog(parent)).ifPresent(f -> {
            file = f;
            save();
        });
    }

    public void save() {
        editorController.save(file);
        name = file.getName();
        setText(name);
    }
}
