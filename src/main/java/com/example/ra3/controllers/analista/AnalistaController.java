package com.example.ra3.controllers.analista;

import com.example.ra3.domains.analista.Analista;
import com.example.ra3.persistence.analista.ArquivoAnalista;
import com.example.ra3.persistence.analista.ArquivoSolucao;
import com.example.ra3.controllers.analista.MainAnalistaController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Controlador para gerenciar os dados do analista (CRUD)

public class AnalistaController {

    private Stage stage;
    private Analista analista;
    private Scene cenaVisualizacao;
    private Scene cenaEdicao;

    public AnalistaController(Stage stage, Analista analista) {
        this.stage = stage;
        this.analista = analista;
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

        Label titulo = new Label("Dados do Analista");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Exibe os dados do analista
        Label lblNome = new Label("Nome: " + analista.getNome());
        Label lblEmail = new Label("E-mail: " + analista.getEmail());
        Label lblCpf = new Label("CPF: " + analista.getCpf());
        Label lblData = new Label("Data Nascimento: " + analista.getDataNascimentoFormatada());

        Button btnEditar = new Button("Editar");
        Button btnExcluir = new Button("Excluir Conta");
        Button btnVoltar = new Button("Voltar");

        btnEditar.setOnAction(e -> stage.setScene(cenaEdicao));
        btnExcluir.setOnAction(e -> mostrarModalExclusao());
        btnVoltar.setOnAction(e -> {
            MainAnalistaController main = new MainAnalistaController(stage, analista);
            main.mostrar();
        });

        layout.getChildren().addAll(titulo, lblNome, lblEmail, lblCpf, lblData, btnEditar, btnExcluir, btnVoltar);
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
        TextField txtNome = new TextField(analista.getNome());
        grid.add(txtNome, 1, 1);

        grid.add(new Label("CPF:"), 0, 2);
        TextField txtCpf = new TextField(analista.getCpf());
        grid.add(txtCpf, 1, 2);

        grid.add(new Label("Data Nascimento (dd/MM/aaaa):"), 0, 3);
        TextField txtData = new TextField(analista.getDataNascimentoFormatada());
        grid.add(txtData, 1, 3);

        grid.add(new Label("Nova Senha:"), 0, 4);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 4);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        Label lblMsg = new Label();
        grid.add(btnSalvar, 0, 5);
        grid.add(btnCancelar, 1, 5);
        grid.add(lblMsg, 0, 6, 2, 1);

        btnSalvar.setOnAction(e -> {
            try {
                String novoNome = txtNome.getText().trim();
                String novoCpf = txtCpf.getText().trim();
                String novaDataStr = txtData.getText().trim();
                String novaSenha = txtSenha.getText().trim();

                if (novoNome.isEmpty() || novoCpf.isEmpty() || novaDataStr.isEmpty()) {
                    lblMsg.setText("Nome, CPF e Data são obrigatórios.");
                    return;
                }

                // Validação de data
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate novaData = LocalDate.parse(novaDataStr, formatter);

                // Atualiza os atributos do analista
                analista.setNome(novoNome);
                analista.setCpf(novoCpf);
                analista.setDataNascimento(novaData);
                if (!novaSenha.isEmpty()) {
                    analista.setSenha(novaSenha);
                }

                if (ArquivoAnalista.atualizarAnalista(analista)) {
                    lblMsg.setText("Dados atualizados com sucesso!");

                    // Recria a tela de visualização para mostrar os dados atualizados
                    criarTelaVisualizacao();
                    stage.setScene(cenaVisualizacao);
                } else {
                    lblMsg.setText("Erro ao atualizar.");
                }
            } catch (DateTimeParseException ex) {
                lblMsg.setText("Data inválida! Use dd/MM/aaaa");
            }
        });

        btnCancelar.setOnAction(e -> stage.setScene(cenaVisualizacao));

        this.cenaEdicao = new Scene(grid, 1300, 700);
    }

    //Modal de confirmação de exclusão da conta - Segue padrão do Exemplo 3 do meu material
    private void mostrarModalExclusao() {
        Stage modal = new Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle("Confirmar exclusão");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label("Tem certeza que deseja excluir sua conta?\nTodas as suas soluções serão perdidas.");
        msg.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnNao = new Button("Não");

        // Ação do botão Sim: primeiro remove as soluções associadas, depois o analista
        btnSim.setOnAction(e -> {
            System.out.println("Excluindo analista: " + analista.getEmail());
            System.out.println("Soluções antes da exclusão: " + ArquivoSolucao.buscarPorAnalista(analista.getEmail()).size());

            // Exclui todas as soluções do analista
            ArquivoSolucao.excluirSolucoesDoAnalista(analista.getEmail());

            System.out.println("Soluções depois da exclusão: " + ArquivoSolucao.buscarPorAnalista(analista.getEmail()).size());

            // Exclui o analista
            ArquivoAnalista.excluirAnalista(analista.getEmail());

            modal.close();
            LoginAnalistaController login = new LoginAnalistaController(stage);
            login.mostrar();
        });

        btnNao.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnSim, btnNao);
        Scene scene = new Scene(layout, 500, 300);
        modal.setScene(scene);
        modal.showAndWait();    // aguarda o usuário decidir
    }
}