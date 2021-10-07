package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
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
        args.stream().map(File::new).filter(File::isFile)
                .map(editorGroupController::createTab)
                .forEach(editorGroupController.getTabs()::add);
        if (editorGroupController.getTabs().isEmpty()) {
            editorGroupController.getTabs().add(editorGroupController.createTab());
        }
    }

    @FXML
    private void dragOver(DragEvent e) {
        if (e.getDragboard().hasFiles() || e.getDragboard().hasImage()) {
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        e.consume();
    }

    @FXML
    private void dragDropped(DragEvent e) {
        if (e.getDragboard().hasFiles()) {
            e.getDragboard().getFiles().stream()
                    .map(editorGroupController::createTab)
                    .forEach(editorGroupController.getTabs()::add);
        }
        e.setDropCompleted(true);
        e.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setEditor(editorGroupController);
    }
}
