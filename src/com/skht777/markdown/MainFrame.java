package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author skht777
 */
public class MainFrame extends VBox {

    @FXML
    private MenuBar menu;
    @FXML
    private TabPane tabPane;

    public MainFrame(List<String> args) throws IOException {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        menu.setUseSystemMenuBar(true);
        tabPane.getStyleClass().add("nobar");
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            if (c.getList().size() > 2) tabPane.getStyleClass().remove("nobar");
            else tabPane.getStyleClass().add("nobar");
        });

        File[] files = args.stream().map(File::new).toArray(File[]::new);
        if (Arrays.asList(files).isEmpty()) newTab();
        else newTab(files);
    }

    @FXML
    private void dragOver(DragEvent e) {
        if (e.getDragboard().hasFiles() || e.getDragboard().hasImage())
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }

    @FXML
    private void dragDropped(DragEvent e) {
        if (e.getDragboard().hasFiles()) newTab(e.getDragboard().getFiles().stream().toArray(File[]::new));
        e.setDropCompleted(true);
        e.consume();
    }

    @FXML
    private void print() {

    }

    @FXML
    private void save() {
        EditorTab selected = (EditorTab) tabPane.getSelectionModel().getSelectedItem();
        if (selected.hasFile()) selected.save();
        else saveWithName();
    }

    @FXML
    private void saveWithName() {
        ((EditorTab) tabPane.getSelectionModel().getSelectedItem()).saveWithName(getScene().getWindow());
    }

    @FXML
    private void newTab() {
        newTab(new EditorTab());
    }

    private void newTab(Tab t) {
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, t);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }

    private void newTab(File[] files) {
        Arrays.stream(files).filter(File::isFile).map(EditorTab::new).forEachOrdered(this::newTab);
    }

}
