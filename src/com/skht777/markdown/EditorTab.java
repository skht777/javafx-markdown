package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;

/**
 * @author skht777
 */
public class EditorTab extends Tab {

    private static int countNew = 1;

    @FXML
    private EditorController editorController;

    private EditorTab(String name) {
        super(name);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
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
        try {
            CharacterStream stream = new CharacterStream(file);
            editorController.setText(stream.getString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorTab() {
        this("名称未設定 " + countNew++);
    }

}