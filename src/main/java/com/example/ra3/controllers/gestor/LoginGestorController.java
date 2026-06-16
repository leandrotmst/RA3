package com.example.ra3.controllers.gestor;

import com.example.ra3.domains.gestor.Gestor;
import com.example.ra3.persistence.gestor.ArquivoGestor;
import com.example.ra3.exceptions.gestor.PersistenceException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginGestorController {
    private Stage stage;
    private TextField txtEmail;
    private PasswordField txtSenha;

    public LoginGestorController(Stage stage) { this.stage = stage; }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label lblTitulo = new Label("Login Gestor");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        txtEmail = new TextField(); txtEmail.setPromptText("Email");
        txtEmail.setMaxWidth(250);

        txtSenha = new PasswordField(); txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(250);

        HBox hbBotoes = new HBox(10);
        hbBotoes.setAlignment(Pos.CENTER);
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setOnAction(e -> handleLogin());

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> mostrarCadastro());

        hbBotoes.getChildren().addAll(btnEntrar, btnCadastrar);

        layout.getChildren().addAll(lblTitulo, txtEmail, txtSenha, hbBotoes);
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin() {
        try {
            Gestor g = ArquivoGestor.buscarPorEmail(txtEmail.getText());
            if (g != null && g.getSenha().equals(txtSenha.getText())) {
                new MainGestorController(stage, g).mostrar();
            } else {
                mostrarErro("Email ou senha incorretos.");
            }
        } catch (PersistenceException e) { mostrarErro(e.getMessage()); }
    }

    private void mostrarCadastro() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label lblCad = new Label("Cadastro de Gestor");
        lblCad.setStyle("-fx-font-size: 18px;");

        TextField n = new TextField(); n.setPromptText("Nome");
        TextField em = new TextField(); em.setPromptText("Email");
        TextField t = new TextField(); t.setPromptText("Telefone");
        PasswordField s = new PasswordField(); s.setPromptText("Senha");

        Button btnOk = new Button("Cadastrar");
        btnOk.setOnAction(e -> {
            try {
                ArquivoGestor.adicionarGestor(new Gestor(n.getText(), em.getText(), t.getText(), s.getText()));
                mostrar();
            } catch (PersistenceException ex) { mostrarErro(ex.getMessage()); }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> mostrar());

        layout.getChildren().addAll(lblCad, n, em, t, s, btnOk, btnVoltar);
        stage.setScene(new Scene(layout, 400, 450));
    }

    private void mostrarErro(String m) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText(m); a.showAndWait();
    }
}
