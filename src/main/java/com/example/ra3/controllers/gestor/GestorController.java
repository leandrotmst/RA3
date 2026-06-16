package com.example.ra3.controllers.gestor;

import com.example.ra3.domains.gestor.Gestor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestorController {
    private Stage stage;
    private Gestor gestor;

    public GestorController(Stage stage, Gestor gestor) {
        this.stage = stage;
        this.gestor = gestor;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Meus Dados");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nome = new Label("Nome: " + gestor.getNome());
        Label email = new Label("Email: " + gestor.getEmail());
        Label telefone = new Label("Telefone: " + gestor.getTelefone());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> new MainGestorController(stage, gestor).mostrar());

        root.getChildren().addAll(titulo, nome, email, telefone, btnVoltar);
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}
