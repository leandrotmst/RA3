package com.example.ra3.controllers.administrador;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdministradorController {

    private Stage stage;
    private Administrador administrador;
    private Scene cenaVisualizacao;
    private Scene cenaEdicao;

    public AdministradorController(Stage stage, Administrador administrador) {
        this.stage = stage;
        this.administrador = administrador;
        criarTelaVisualizacao();
        criarTelaEdicao();
    }

    public void mostrar() {
        stage.setScene(cenaVisualizacao);
        stage.setTitle("Meus Dados");
        stage.show();
    }

    private void criarTelaVisualizacao() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Dados do Administrador");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblNome = new Label("Nome: " + administrador.getNome());
        Label lblEmail = new Label("E-mail: " + administrador.getEmail());

        Button btnEditar = new Button("Editar");
        Button btnExcluir = new Button("Excluir Conta");
        Button btnVoltar = new Button("Voltar");

        btnEditar.setOnAction(e -> stage.setScene(cenaEdicao));
        btnExcluir.setOnAction(e -> mostrarModalExclusao());
        btnVoltar.setOnAction(e -> {
            MainAdministradorController main = new MainAdministradorController(stage, administrador);
            main.mostrar();
        });

        layout.getChildren().addAll(titulo, lblNome, lblEmail, btnEditar, btnExcluir, btnVoltar);
        this.cenaVisualizacao = new Scene(layout, 1300, 700);
    }

    private void criarTelaEdicao() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label titulo = new Label("Editar Dados");
        titulo.setStyle("-fx-font-size: 18px;");
        grid.add(titulo, 0, 0, 2, 1);

        grid.add(new Label("Nome:"), 0, 1);
        TextField txtNome = new TextField(administrador.getNome());
        grid.add(txtNome, 1, 1);

        grid.add(new Label("Nova Senha:"), 0, 2);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 2);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        Label lblMsg = new Label();
        grid.add(btnSalvar, 0, 3);
        grid.add(btnCancelar, 1, 3);
        grid.add(lblMsg, 0, 4, 2, 1);

        btnSalvar.setOnAction(e -> {
            String novoNome = txtNome.getText().trim();
            String novaSenha = txtSenha.getText().trim();

            if (novoNome.isEmpty()) {
                lblMsg.setText("Nome e obrigatorio.");
                return;
            }

            administrador.setNome(novoNome);
            if (!novaSenha.isEmpty()) {
                administrador.setSenha(novaSenha);
            }

            if (ArquivoAdministrador.atualizarAdministrador(administrador)) {
                lblMsg.setText("Dados atualizados com sucesso!");
                criarTelaVisualizacao();
                criarTelaEdicao();
                stage.setScene(cenaVisualizacao);
            } else {
                lblMsg.setText("Erro ao atualizar.");
            }
        });

        btnCancelar.setOnAction(e -> stage.setScene(cenaVisualizacao));

        this.cenaEdicao = new Scene(grid, 1300, 700);
    }

    private void mostrarModalExclusao() {
        Stage modal = new Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle("Confirmar exclusao");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label("Tem certeza que deseja excluir sua conta?");
        msg.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnNao = new Button("Nao");

        btnSim.setOnAction(e -> {
            ArquivoAdministrador.excluirAdministrador(administrador.getEmail());

            modal.close();
            LoginAdministradorController login = new LoginAdministradorController(stage);
            login.mostrar();
        });

        btnNao.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnSim, btnNao);
        Scene scene = new Scene(layout, 500, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
