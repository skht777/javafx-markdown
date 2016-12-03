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
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            tabPane.getStyleClass().remove("nobar");
            if (c.getList().size() <= 2) tabPane.getStyleClass().add("nobar");
        });
        newTab(args.stream().map(File::new).toArray(File[]::new));
        if (tabPane.getTabs().size() == 1) tabPane.getTabs().add(new EditorTab());
        tabPane.getSelectionModel().selectFirst();
    }

    @FXML
    public void dragOver(DragEvent e) {
        if (e.getDragboard().hasFiles() || e.getDragboard().hasImage())
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }

    @FXML
    public void dragDropped(DragEvent e) {
        if (e.getDragboard().hasFiles()) newTab(e.getDragboard().getFiles().stream().toArray(File[]::new));
        e.setDropCompleted(true);
        e.consume();
    }

    @FXML
    public void print() {

    }

    @FXML
    public void save() {
        EditorTab selected = (EditorTab) tabPane.getSelectionModel().getSelectedItem();
        if (selected.hasFile()) selected.save();
        else saveWithName();
    }

    @FXML
    public void saveWithName() {
        ((EditorTab) tabPane.getSelectionModel().getSelectedItem()).saveWithName(getScene().getWindow());
    }

    @FXML
    public void newTab() {
        newTab(new File[0]);
    }

    private void newTab(File... files) {
        int num = tabPane.getTabs().size() - 1;
        if (files.length != 0) {
            Arrays.stream(files).filter(File::isFile).map(EditorTab::new).forEachOrdered(f -> tabPane.getTabs().add(num, f));
        } else {
            tabPane.getTabs().add(num, new EditorTab());
        }
    }

}
