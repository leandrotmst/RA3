package com.example.ra3;

import com.example.ra3.controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new LoginController(stage).mostrar();
    }

    public static void main(String[] args) {
        launch();
    }
}
