/**
 *
 */
package com.skht777.markdown;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class EditorController implements Initializable {

    @FXML
    private WebView view;
    @FXML
    private TextArea text;
    private JSObject window;

    @FXML
    public void convertMarkdown() {
        Optional.ofNullable(window).ifPresent(js -> js.call("mark", text.getText()));
    }

    @FXML
    public void dragFile(DragEvent e) {
        if (e.getDragboard().hasFiles()) e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }

    @FXML
    public void dropFile(DragEvent e) {
        boolean status = false;
        try {
            text.setText(CharacterStream.decodeString(e.getDragboard().getFiles().get(0)));
            convertMarkdown();
            status = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        e.setDropCompleted(status);
        e.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view.getEngine().load(getClass().getResource("/resources/web/markdown.html").toExternalForm());
        view.getEngine().setOnAlert(e -> {
            if ("command:ready".equals(e.getData())) window = (JSObject) view.getEngine().executeScript("window");
        });
    }

}
