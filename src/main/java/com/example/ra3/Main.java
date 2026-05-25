package com.example.ra3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Hello World");
        Label lbl1 = new Label("Teste");
        lbl1.setFont(new Font("Arial", 24));
        lbl1.setAlignment(Pos.CENTER);
        Scene scene = new Scene(lbl1, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
