package com.example.ra3.controllers.administrador;

import com.example.ra3.domains.administrador.Administrador;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainAdministradorController {

    private Stage stage;
    private Administrador administrador;
    private Scene cena;

    public MainAdministradorController(Stage stage, Administrador administrador) {
        this.stage = stage;
        this.administrador = administrador;
        criarUI();
    }

    public void mostrar() {
        stage.setScene(cena);
        stage.setTitle("Home - " + administrador.getNome());
        stage.show();
    }

    private void criarUI() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Label boasVindas = new Label("Bem-vindo, " + administrador.getNome() + "!");
        boasVindas.setFont(new Font("Arial", 24));

        Button btnGerenciarClientes = new Button("Gerenciar Clientes");
        btnGerenciarClientes.setOnAction(e -> {
            ClienteController clienteController = new ClienteController(stage, administrador);
            clienteController.mostrar();
        });

        Button btnGerenciarGestores = new Button("Gerenciar Gestores");
        btnGerenciarGestores.setOnAction(e -> {
            GestorController gestorController = new GestorController(stage, administrador);
            gestorController.mostrar();
        });

        Button btnMeusDados = new Button("Meus Dados");
        btnMeusDados.setOnAction(e -> {
            AdministradorController administradorController = new AdministradorController(stage, administrador);
            administradorController.mostrar();
        });

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> {
            LoginAdministradorController login = new LoginAdministradorController(stage);
            login.mostrar();
        });

        layout.getChildren().addAll(boasVindas, btnGerenciarGestores, btnGerenciarClientes, btnMeusDados, btnLogout);
        this.cena = new Scene(layout, 1300, 700);
    }
}
