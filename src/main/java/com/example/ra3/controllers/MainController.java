package com.example.ra3.controllers;

import com.example.ra3.controllers.analista.LoginAnalistaController;
import com.example.ra3.controllers.gestor.LoginGestorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainController {

    private Stage stage;

    public MainController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30; -fx-background-color: #f0f0f0;");

        Label titulo = new Label("AlignUp");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-font-weight: bold;");

        // Botão para o módulo Analista
        Button btnAnalista = new Button("Área do Analista - Yumi");
        btnAnalista.setPrefWidth(250);
        btnAnalista.setOnAction(e -> {
            LoginAnalistaController login = new LoginAnalistaController(stage);
            login.mostrar();
        });


        // Botão para o módulo Gestor
        Button btnGestor = new Button("Área do Gestor - Leandro");
        btnGestor.setPrefWidth(250);
        btnGestor.setOnAction(e -> {
            LoginGestorController login = new LoginGestorController(stage);
            login.mostrar();
        });

        Button btnSair = new Button("Sair");
        btnSair.setPrefWidth(250);
        btnSair.setOnAction(e -> stage.close());

        layout.getChildren().addAll(
                titulo,
                btnAnalista,
                btnGestor,
                btnSair
        );

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Menu Principal - AlignUp");
        stage.show();
    }
}