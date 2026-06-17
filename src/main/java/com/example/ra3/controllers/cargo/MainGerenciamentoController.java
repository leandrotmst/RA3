package com.example.ra3.controllers.cargo;

import com.example.ra3.controllers.MainController;
import com.example.ra3.controllers.cargo.CargoController;
import com.example.ra3.controllers.cargo.SetorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGerenciamentoController {

    private Stage stage;

    public MainGerenciamentoController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30;");

        Label titulo = new Label("Área de Gerenciamento");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-font-weight: bold;");

        Button btnCargos = new Button("Área de Cargos");
        btnCargos.setPrefWidth(250);
        btnCargos.setOnAction(e -> new CargoController(stage).mostrar());

        Button btnSetores = new Button("Área de Setores");
        btnSetores.setPrefWidth(250);
        btnSetores.setOnAction(e -> new SetorController(stage).mostrar());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(250);
        btnVoltar.setOnAction(e -> new MainController(stage).mostrar());

        layout.getChildren().addAll(titulo, btnCargos, btnSetores, btnVoltar);
        stage.setScene(new Scene(layout, 800, 600));
        stage.setTitle("Gerenciamento");
        stage.show();
    }
}