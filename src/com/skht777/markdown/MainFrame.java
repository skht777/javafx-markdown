package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author skht777
 */
public class MainFrame extends TabPane {

    public MainFrame(List<String> args) throws IOException {
        super();
        getTabs().addListener((ListChangeListener<Tab>) c -> {
            getStyleClass().remove("nobar");
            if (c.getList().size() <= 1) getStyleClass().add("nobar");
        });
        addTabs(args.stream().map(File::new).collect(Collectors.toList()));
        if (getTabs().isEmpty()) getTabs().add(new EditorTab());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @FXML
    public void dragFile(DragEvent e) {
        if (e.getDragboard().hasFiles() || e.getDragboard().hasImage())
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }

    @FXML
    public void dropFile(DragEvent e) {
        addTabs(e.getDragboard().getFiles());
        e.setDropCompleted(true);
        e.consume();
    }

    private void addTabs(List<File> files) {
        files.stream().filter(File::isFile).map(EditorTab::new).forEachOrdered(getTabs()::add);
    }

}
