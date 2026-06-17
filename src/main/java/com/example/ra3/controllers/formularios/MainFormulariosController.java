package com.example.ra3.controllers.formularios;

import com.example.ra3.controllers.MainController;
import com.example.ra3.controllers.formularios.RespostaController;
import com.example.ra3.controllers.formularios.ReviewController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainFormulariosController {
    private Stage stage;

    public MainFormulariosController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30;");

        Label titulo = new Label("Área de Formulários");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-font-weight: bold;");

        Button btnRespostas = new Button("Formulário Semanal do Funcionário");
        btnRespostas.setPrefWidth(250);
        btnRespostas.setOnAction(e -> new RespostaController(stage).mostrar());

        Button btnReviews = new Button("Review de Soluções do Gestor");
        btnReviews.setPrefWidth(250);
        btnReviews.setOnAction(e -> new ReviewController(stage).mostrar());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(250);
        btnVoltar.setOnAction(e -> new MainController(stage).mostrar());

        layout.getChildren().addAll(titulo, btnRespostas, btnReviews, btnVoltar);
        stage.setScene(new Scene(layout, 800, 600));
        stage.setTitle("Gerenciamento");
        stage.show();
    }
}
