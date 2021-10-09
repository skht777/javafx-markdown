package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class EditorGroupController implements Initializable {
    @FXML
    private TabPane tabPane;
    private TabManager<TabController> tabManager;

    void init(List<File> files) {
        files.forEach(this::addTabSafe);
        if (tabPane.getTabs().isEmpty()) {
            addEmptyTab();
        }
    }

    TabController getSelectedTab() {
        return tabManager.getSelectedTab();
    }

    private TabController loadTab() {
        try {
            return tabManager.loadTab(getClass().getResource("fxml/tab.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void addEmptyTab() {
        loadTab().init();
    }

    void addTabSafe(File file) {
        try {
            addTab(file);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while").show();
            e.printStackTrace();
        }
    }

    void addTab(File file) throws IOException {
        loadTab().setFileWithLoad(file);
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
            e.getDragboard().getFiles().forEach(this::addTabSafe);
        }
        e.setDropCompleted(true);
        e.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabManager = new TabManager<>(tabPane);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    tabPane.getSelectionModel().select(c.getList().get(c.getList().size() - 1));
                }
                if (c.wasRemoved() && tabPane.getTabs().isEmpty()) {
                    addEmptyTab();
                }
            }
        });
    }
}
