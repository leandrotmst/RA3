package com.example.ra3.controllers.analista;


import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.analista.Analista;
import com.example.ra3.persistence.analista.ArquivoAnalista;
import com.example.ra3.controllers.analista.MainAnalistaController;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LoginAnalistaController {

    private Stage stage;            // palco principal (recebido do Application)
    private Scene cenaLogin;        // cena de login
    private Scene cenaCadastro;     // cena de cadastro

    public LoginAnalistaController(Stage stage) {
        this.stage = stage;
        criarTelaLogin();
        criarTelaCadastro();
    }

    public void mostrar() {
        stage.setScene(cenaLogin);
        stage.setTitle("Área de Login - Analista");
        stage.show();
    }

    private void criarTelaLogin() {

        // GridPane para organizar os controles (Exemplo 5 - do meu material)
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label titulo = new Label("Login");
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

        // Evento do botão Login (uso de lambda)
        btnLogin.setOnAction(e -> {
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            Analista analista = ArquivoAnalista.buscarPorEmail(email);
            if (analista != null && analista.getSenha().equals(senha)) {
                lblMensagem.setText("Login bem-sucedido!");
                // Abre a tela principal do analista
                MainAnalistaController main = new MainAnalistaController(stage, analista);
                main.mostrar();
            } else {
                lblMensagem.setText("E-mail ou senha inválidos!");
            }
        });

        btnCadastrar.setOnAction(e -> stage.setScene(cenaCadastro));

        // Botão que volta pro menu principal (MainController)
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

        Label titulo = new Label("Cadastro de Analista");
        titulo.setFont(new Font("Arial", 20));
        grid.add(titulo, 0, 0, 2, 1);

        // Campos
        grid.add(new Label("Nome:"), 0, 1);
        TextField txtNome = new TextField();
        grid.add(txtNome, 1, 1);

        grid.add(new Label("E-mail:"), 0, 2);
        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 2);

        grid.add(new Label("CPF:"), 0, 3);
        TextField txtCpf = new TextField();
        grid.add(txtCpf, 1, 3);

        grid.add(new Label("Data Nascimento (dd/MM/aaaa):"), 0, 4);
        TextField txtData = new TextField();
        txtData.setPromptText("dd/MM/aaaa");
        grid.add(txtData, 1, 4);

        grid.add(new Label("Senha:"), 0, 5);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 5);

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar ao Login");
        Label lblMsg = new Label();

        grid.add(btnSalvar, 0, 6);
        grid.add(btnVoltar, 1, 6);
        grid.add(lblMsg, 0, 7, 2, 1);

        btnSalvar.setOnAction(e -> {
            try {
                String nome = txtNome.getText().trim();
                String email = txtEmail.getText().trim();
                String cpf = txtCpf.getText().trim();
                String senha = txtSenha.getText();

                if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                    lblMsg.setText("Preencha todos os campos!");
                    return;
                }

                // Validação de data no padrão brasileiro
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(txtData.getText(), formatter);

                Analista novo = new Analista(nome, email, cpf, data, senha);
                if (ArquivoAnalista.adicionarAnalista(novo)) {
                    lblMsg.setText("Cadastro realizado! Faça login.");

                    // Limpa os campos após cadastro bem-sucedido
                    txtNome.clear();
                    txtEmail.clear();
                    txtCpf.clear();
                    txtData.clear();
                    txtSenha.clear();
                } else {
                    lblMsg.setText("E-mail já cadastrado!");
                }
            } catch (DateTimeParseException ex) {
                lblMsg.setText("Data inválida! Use dd/MM/aaaa");
            } catch (Exception ex) {
                lblMsg.setText("Erro: " + ex.getMessage());
            }
        });

        btnVoltar.setOnAction(e -> stage.setScene(cenaLogin));

        this.cenaCadastro = new Scene(grid, 1300, 700);
    }
}
