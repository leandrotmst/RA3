package com.example.ra3;

import com.example.ra3.controllers.MainController;
import com.example.ra3.controllers.FuncionarioController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FuncionarioController controller = new FuncionarioController(stage);
        controller.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
