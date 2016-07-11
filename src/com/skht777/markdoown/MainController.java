/**
 *
 */
package com.skht777.markdoown;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class MainController implements Initializable {

    @FXML
    WebView view;
    @FXML
    TextArea text;


    @FXML
    public void convertMarkdown() {
        if (view.getEngine().getLoadWorker().getState() != Worker.State.SUCCEEDED) return;
        JSObject js = (JSObject) view.getEngine().executeScript("window");
        js.call("mark", text.getText());
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
    }

}
