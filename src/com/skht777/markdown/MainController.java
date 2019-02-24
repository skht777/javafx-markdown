package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author skht777
 */
public class MainController implements Initializable {
    @FXML
    private MenuBar menu;
    @FXML
    private TabPane tabPane;

    public static BorderPane prepare(List<String> args) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("fxml/main.fxml"));
        BorderPane root = loader.load();
        MainController controller = loader.getController();
        List<File> files = args.stream().map(File::new).filter(File::isFile).collect(Collectors.toList());
        controller.addTabs(files);
        if (files.isEmpty()) {
            controller.addTab();
        }

        return root;
    }

    private EditorTab getSelectedTab() {
        return (EditorTab) tabPane.getSelectionModel().getSelectedItem();
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
            addTabs(e.getDragboard().getFiles());
        }
        e.setDropCompleted(true);
        e.consume();
    }

    @FXML
    private void open() {
        EditorTab tab = getSelectedTab();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All types", "*.*"));
        fc.setInitialFileName(tab.getText());
        Optional.ofNullable(fc.showOpenDialog(tabPane.getScene().getWindow())).ifPresent(tab::open);
    }

    @FXML
    private void print() {
        getSelectedTab().getEditor().print(tabPane.getScene().getWindow());
    }

    @FXML
    private void save() {
        Optional.of(getSelectedTab()).filter(EditorTab::hasFile)
                .ifPresentOrElse(EditorTab::save, this::saveWithName);
    }

    @FXML
    private void saveWithName() {
        EditorTab tab = getSelectedTab();
        FileChooser fc = new FileChooser();
        fc.setInitialFileName(tab.getText());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All types", "*.*"));
        Optional.ofNullable(fc.showSaveDialog(tabPane.getScene().getWindow())).ifPresent(tab::save);
    }

    @FXML
    private void addTab() {
        addTab(new EditorTab());
    }

    private void addTab(Tab t) {
        tabPane.getTabs().add(t);
        tabPane.getSelectionModel().select(t);
    }

    private void addTabs(List<File> files) {
        files.stream().map(EditorTab::new).forEachOrdered(this::addTab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menu.setUseSystemMenuBar(true);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            if (tabPane.getTabs().size() < 2) {
                tabPane.getStyleClass().add("nobar");
            } else {
                tabPane.getStyleClass().remove("nobar");
            }
        });
    }
}
