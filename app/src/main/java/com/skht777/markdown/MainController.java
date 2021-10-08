package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class MainController implements Initializable {
    @FXML
    private EditorGroupController editorGroupController;
    @FXML
    private MenuBarController menuBarController;

    void init(Window window, List<String> args) {
        menuBarController.setWindow(window);
        editorGroupController.init(args.stream().map(File::new).filter(File::isFile).toList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setEditor(editorGroupController);
    }
}
