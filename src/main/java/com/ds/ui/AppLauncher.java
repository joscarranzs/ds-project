package com.ds.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Minimal JavaFX launcher used for quick UI verification during development.
 */
public class AppLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setCenter(new Label("Delivery System UI - AppLauncher"));

        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("DS Project - UI Preview");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
