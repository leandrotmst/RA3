package com.example.ra3.controllers.analista;

import com.example.ra3.controllers.analista.LoginAnalistaController;
import com.example.ra3.controllers.analista.SolucaoController;
import com.example.ra3.controllers.analista.AnalistaController;
import com.example.ra3.domains.analista.Analista;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Tela principal após o login - Exibe um menu com botões para acessar o CRUD de soluções e os dados do analista

public class MainAnalistaController {

    private Stage stage;
    private Analista analista;  // analista logado
    private Scene cena;

    public MainAnalistaController(Stage stage, Analista analista) {
        this.stage = stage;
        this.analista = analista;
        criarUI();
    }

    public void mostrar() {
        stage.setScene(cena);
        stage.setTitle("Home - " + analista.getNome());
        stage.show();
    }

    private void criarUI() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Label boasVindas = new Label("Bem-vindo, " + analista.getNome() + "!");
        boasVindas.setFont(new Font("Arial", 24));

        Button btnGerenciarSolucoes = new Button("Gerenciar Soluções");
        // Ao clicar, cria o controlador de soluções e mostra a tela
        btnGerenciarSolucoes.setOnAction(e -> {
            SolucaoController solController = new SolucaoController(stage, analista);
            solController.mostrar();
        });

        Button btnMeusDados = new Button("Meus Dados");
        btnMeusDados.setOnAction(e -> {
            AnalistaController analController = new AnalistaController(stage, analista);
            analController.mostrar();
        });

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> {
            LoginAnalistaController login = new LoginAnalistaController(stage);
            login.mostrar();
        });

        layout.getChildren().addAll(boasVindas, btnGerenciarSolucoes, btnMeusDados, btnLogout);
        this.cena = new Scene(layout, 1300, 700);
    }
}