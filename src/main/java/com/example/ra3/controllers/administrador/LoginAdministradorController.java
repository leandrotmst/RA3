package com.example.ra3.controllers.administrador;

import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.administrador.Administrador;
import com.example.ra3.persistence.administrador.ArquivoAdministrador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginAdministradorController {

    private Stage stage;
    private Scene cenaLogin;
    private Scene cenaCadastro;

    public LoginAdministradorController(Stage stage) {
        this.stage = stage;
        criarTelaLogin();
        criarTelaCadastro();
    }

    public void mostrar() {
        stage.setScene(cenaLogin);
        stage.setTitle("Area de Login - Administrador");
        stage.show();
    }

    private void criarTelaLogin() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label titulo = new Label("Login do Administrador");
        titulo.setFont(new Font("Arial", 20));
        grid.add(titulo, 0, 0, 2, 1);

        Label lblEmail = new Label("E-mail:");
        grid.add(lblEmail, 0, 1);
        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 1);

        Label lblSenha = new Label("Senha:");
        grid.add(lblSenha, 0, 2);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 2);

        Button btnLogin = new Button("Entrar");
        Button btnCadastrar = new Button("Cadastrar-se");
        Button btnVoltar = new Button("Voltar ao menu");
        Label lblMensagem = new Label();

        grid.add(btnLogin, 0, 3);
        grid.add(btnCadastrar, 1, 3);
        grid.add(btnVoltar, 2, 3);
        grid.add(lblMensagem, 0, 4, 2, 1);

        btnLogin.setOnAction(e -> {
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText();
            Administrador administrador = ArquivoAdministrador.buscarPorEmail(email);

            if (administrador != null && administrador.getSenha().equals(senha)) {
                lblMensagem.setText("Login bem-sucedido!");
                MainAdministradorController main = new MainAdministradorController(stage, administrador);
                main.mostrar();
            } else {
                lblMensagem.setText("E-mail ou senha invalidos!");
            }
        });

        btnCadastrar.setOnAction(e -> stage.setScene(cenaCadastro));

        btnVoltar.setOnAction(e -> {
            MainController main = new MainController(stage);
            main.mostrar();
        });

        this.cenaLogin = new Scene(grid, 1300, 700);
    }

    private void criarTelaCadastro() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label titulo = new Label("Cadastro de Administrador");
        titulo.setFont(new Font("Arial", 20));
        grid.add(titulo, 0, 0, 2, 1);

        grid.add(new Label("Nome:"), 0, 1);
        TextField txtNome = new TextField();
        grid.add(txtNome, 1, 1);

        grid.add(new Label("E-mail:"), 0, 2);
        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 2);

        grid.add(new Label("Senha:"), 0, 3);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 3);

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar ao Login");
        Label lblMsg = new Label();

        grid.add(btnSalvar, 0, 4);
        grid.add(btnVoltar, 1, 4);
        grid.add(lblMsg, 0, 5, 2, 1);

        btnSalvar.setOnAction(e -> {
            try {
                String nome = txtNome.getText().trim();
                String email = txtEmail.getText().trim();
                String senha = txtSenha.getText();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    lblMsg.setText("Preencha todos os campos!");
                    return;
                }

                Administrador novo = new Administrador(nome, email, senha);
                if (ArquivoAdministrador.adicionarAdministrador(novo)) {
                    lblMsg.setText("Cadastro realizado! Faca login.");
                    txtNome.clear();
                    txtEmail.clear();
                    txtSenha.clear();
                } else {
                    lblMsg.setText("E-mail ja cadastrado!");
                }
            } catch (Exception ex) {
                lblMsg.setText("Erro: " + ex.getMessage());
            }
        });

        btnVoltar.setOnAction(e -> stage.setScene(cenaLogin));

        this.cenaCadastro = new Scene(grid, 1300, 700);
    }
}
