package com.skht777.markdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author skht777
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("fxml/main.fxml"));
            primaryStage.setScene(new Scene(loader.load(), 800.0, 620.0));
            loader.<MainController>getController()
                    .init(primaryStage.getScene().getWindow(), getParameters().getUnnamed());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        primaryStage.setTitle("Markdown Editor");
//		primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
