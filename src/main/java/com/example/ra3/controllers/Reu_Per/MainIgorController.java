package com.example.ra3.controllers.Reu_Per;

import com.example.ra3.controllers.MainController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainIgorController {

    private Stage stage;
    private Scene cena;

    public MainIgorController(Stage stage) {
        this.stage = stage;
        criarUI();
    }

    public void mostrar() {
        stage.setScene(cena);
        stage.setTitle("Reuniões e Permissões");
        stage.show();
    }

    private void criarUI() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30; -fx-background-color: #f0f0f0;");

        Label titulo = new Label("Reuniões e Permissões");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-font-weight: bold;");

        Button btnReunioes = new Button("Gerenciar Reuniões");
        btnReunioes.setPrefWidth(250);
        btnReunioes.setOnAction(e -> {
            ReuniaoController controller = new ReuniaoController(stage);
            controller.mostrar();
        });

        Button btnPermissoes = new Button("Gerenciar Permissões");
        btnPermissoes.setPrefWidth(250);
        btnPermissoes.setOnAction(e -> {
            PermissaoController controller = new PermissaoController(stage);
            controller.mostrar();
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(250);
        btnVoltar.setOnAction(e -> {
            MainController controller = new MainController(stage);
            controller.mostrar();
        });

        layout.getChildren().addAll(
                titulo,
                btnReunioes,
                btnPermissoes,
                btnVoltar
        );

        cena = new Scene(layout, 800, 600);
    }
}