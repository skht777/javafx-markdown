package com.skht777.markdown;

import com.skht777.markdown.editor.TabController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class EditorGroupController implements Initializable {
    @FXML
    private TabPane tabPane;
    private Map<Tab, TabController> tabMap;
    private int countNew = 1;

    ObservableList<Tab> getTabs() {
        return tabPane.getTabs();
    }

    TabController getSelectedTab() {
        return tabMap.getOrDefault(tabPane.getSelectionModel().getSelectedItem(), null);
    }

    Tab createTab() {
        return createTab(null);
    }

    Tab createTab(File file) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/tab.fxml"));
            Tab tab = loader.load();
            TabController controller = loader.getController();
            Optional.ofNullable(file)
                    .ifPresentOrElse(controller.getFileProperty()::setValue,
                            () -> controller.getNameProperty().setValue("New " + countNew++));
            tabMap.put(tab, controller);
            return tab;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabMap = new HashMap<>();
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        getTabs().addListener((ListChangeListener<Tab>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    tabPane.getSelectionModel().select(c.getList().get(c.getList().size() - 1));
                }
                if (c.wasRemoved() && getTabs().isEmpty()) {
                    getTabs().add(createTab());
                }
            }
        });
    }
}
