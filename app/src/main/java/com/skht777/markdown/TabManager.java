package com.skht777.markdown;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author skht777
 */
public class TabManager<T> {
    private final TabPane tabPane;
    private final Map<Tab, T> tabMap;

    public TabManager(TabPane tabPane) {
        this.tabPane = tabPane;
        tabMap = new HashMap<>();
        tabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    c.getList().forEach(tabMap::remove);
                }
            }
        });
    }

    public T loadTab(URL location) throws IOException {
        FXMLLoader loader = new FXMLLoader(location);
        Tab tab = loader.load();
        T controller = loader.getController();
        tabMap.put(tab, controller);
        tabPane.getTabs().add(tab);

        return controller;
    }

    public T getSelectedTab() {
        return tabMap.getOrDefault(tabPane.getSelectionModel().getSelectedItem(), null);
    }
}
