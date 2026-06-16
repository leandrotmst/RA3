package com.example.ra3;

import com.example.ra3.controllers.MainController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainController controller = new MainController(stage);
        controller.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
