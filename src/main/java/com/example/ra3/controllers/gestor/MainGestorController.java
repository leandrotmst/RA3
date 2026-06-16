package com.example.ra3.controllers.gestor;

import com.example.ra3.domains.gestor.Gestor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGestorController {

    private Stage stage;
    private Gestor gestor;
    private Scene cena;

    public MainGestorController(Stage stage, Gestor gestor) {
        this.stage = stage;
        this.gestor = gestor;
        criarUI();
    }

    public void mostrar() {
        stage.setScene(cena);
        stage.setTitle("Home - " + gestor.getNome());
        stage.show();
    }

    private void criarUI() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Label boasVindas = new Label("Bem-vindo, " + gestor.getNome() + "!");
        boasVindas.setFont(new Font("Arial", 24));

        Button btnGerenciarFuncionarios = new Button("Gerenciar Funcionários");
        btnGerenciarFuncionarios.setOnAction(e -> {
            FuncionarioController funcController = new FuncionarioController(stage, gestor);
            funcController.mostrar();
        });

        Button btnMeusDados = new Button("Meus Dados");
        btnMeusDados.setOnAction(e -> {
            GestorController gestorCtrl = new GestorController(stage, gestor);
            gestorCtrl.mostrar();
        });

        Button btnReunioes = new Button("Gerenciar Reuniões");

        btnReunioes.setOnAction(e -> {
            ReuniaoController reuniaoCtrl =
                    new ReuniaoController(stage, gestor);
            reuniaoCtrl.mostrar();
        });

        Button btnPermissoes = new Button("Gerenciar Permissões");

        btnPermissoes.setOnAction(e -> {
            PermissaoController permissaoCtrl =
                    new PermissaoController(stage, gestor);
            permissaoCtrl.mostrar();
        });

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> {
            LoginGestorController login = new LoginGestorController(stage);
            login.mostrar();
        });

        layout.getChildren().addAll(
                boasVindas,
                btnGerenciarFuncionarios,
                btnMeusDados,
                btnReunioes,
                btnPermissoes,
                btnLogout
        );
        this.cena = new Scene(layout, 800, 600);
    }
}
