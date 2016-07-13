/**
 *
 */
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
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Main.fxml"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Markdown Editor");
//		primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
