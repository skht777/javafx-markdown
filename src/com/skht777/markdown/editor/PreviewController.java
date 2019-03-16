package com.skht777.markdown.editor;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author skht777
 */
public class PreviewController implements Initializable {
    @FXML
    private WebView view;
    private WebEngine engine;
    private boolean viewOnReady;
    private StringProperty textProperty;

    void setTextProperty(StringProperty textProperty) {
        this.textProperty = textProperty;
        this.textProperty.addListener((v, o, n) -> convertMarkdown(n));
    }

    private void convertMarkdown(String value) {
        if (viewOnReady) {
            ((JSObject) engine.executeScript("window"))
                    .call("mark", Optional.ofNullable(value).orElseGet(String::new));
        }
    }

    public String getCurrentHTML() {
        if (viewOnReady) {
            String html = (String) engine.executeScript("document.documentElement.outerHTML");
            html = html.replaceAll("<script.*>.*</script>\n", "");
            return html;
        }

        return null;
    }

    public void print(Window parent) {
        Optional.ofNullable(PrinterJob.createPrinterJob()).ifPresent(pjob -> {
            if (pjob.showPrintDialog(parent)) {
                engine.print(pjob);
            }
            pjob.endJob();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = view.getEngine();
        engine.load(getClass().getResource("/resources/web/markdown.html").toExternalForm());
        engine.setOnAlert(e -> {
            if ("command:ready".equals(e.getData())) {
                viewOnReady = true;
                convertMarkdown(textProperty.getValueSafe());
            }
        });
    }
}
