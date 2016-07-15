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

    public EditorTab(File file) {
        super(file.getName());
        load();
    }

    public EditorTab() {
        super("名称未設定 " + countNew++);
        load();
    }

    private void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}