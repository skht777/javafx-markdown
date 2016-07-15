package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        List<EditorTab> tabList = args.stream().map(File::new).filter(File::exists).filter(File::isFile)
                .map(EditorTab::new).collect(Collectors.toList());
        if (tabList.isEmpty()) tabList.add(new EditorTab());
        getTabs().addListener((ListChangeListener<Tab>) c -> {
            getStyleClass().remove("nobar");
            if (c.getList().size() <= 1) getStyleClass().add("nobar");
        });
        getTabs().addAll(tabList);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

}
