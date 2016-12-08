/**
 *
 */
package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author skht777
 */
public class EditorController implements Initializable {

    @FXML
    private WebView view;
    @FXML
    private TextArea text;
    private List<Consumer<JSObject>> runLater;
    private JSObject window;

    @FXML
    public void convertMarkdown() {
        Consumer<JSObject> consumer = js -> js.call("mark", text.getText());
        if (Optional.ofNullable(window).isPresent()) consumer.accept(window);
        else runLater.add(consumer);
    }

    public void setText(String value) {
        text.setText(value);
        convertMarkdown();
    }

    public void print(PrinterJob job) {
        view.getEngine().print(job);
    }

    public void save(File file) {
        try {
            Files.write(file.toPath(), text.getText().getBytes());
        } catch (IOException e) {
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runLater = new ArrayList<>();
        view.getEngine().load(getClass().getResource("/resources/web/markdown.html").toExternalForm());
        view.getEngine().setOnAlert(e -> {
            if ("command:ready".equals(e.getData())) {
                window = (JSObject) view.getEngine().executeScript("window");
                runLater.forEach(c -> c.accept(window));
                runLater.clear();
            }
        });
    }

}
