package com.example.ra3.controllers.Reu_Per;

import com.example.ra3.domains.Reu_Per.Reuniao;
import com.example.ra3.exceptions.gestor.PersistenceException;
import com.example.ra3.persistence.Reu_Per.ArquivoReuniao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReuniaoController {

    private Stage stage;
    private TableView<Reuniao> tabela;
    private ObservableList<Reuniao> listaReunioes;

    private TextField txtTitulo;
    private TextField txtData;
    private TextField txtHorario;

    public ReuniaoController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titulo = new Label("Agende suas Reuniões");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        criarFormulario();
        criarTabela();

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> cadastrar());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizar());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluir());

        Button btnLimpar = new Button("Limpar");
        btnLimpar.setOnAction(e -> limparCampos());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> new MainIgorController(stage).mostrar());

        HBox botoes = new HBox(10, btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar);
        botoes.setAlignment(Pos.CENTER);

        GridPane formulario = montarFormulario();

        root.getChildren().addAll(titulo, formulario, botoes, tabela);

        carregarTabela();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Reuniões");
        stage.show();
    }

    private void criarFormulario() {
        txtTitulo = new TextField();
        txtData = new TextField();
        txtHorario = new TextField();

        txtTitulo.setPromptText("Ex: Reunião de feedback");
        txtData.setPromptText("Ex: 20/06/2026");
        txtHorario.setPromptText("Ex: 14:00");
    }

    private GridPane montarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Título:"), 0, 0);
        grid.add(txtTitulo, 1, 0);

        grid.add(new Label("Data:"), 0, 1);
        grid.add(txtData, 1, 1);

        grid.add(new Label("Horário:"), 0, 2);
        grid.add(txtHorario, 1, 2);

        return grid;
    }

    private void criarTabela() {
        tabela = new TableView<>();

        TableColumn<Reuniao, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitulo()));

        TableColumn<Reuniao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getData()));

        TableColumn<Reuniao, String> colHorario = new TableColumn<>("Horário");
        colHorario.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getHorario()));

        tabela.getColumns().addAll(colTitulo, colData, colHorario);
        tabela.setPrefHeight(300);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                txtTitulo.setText(selecionado.getTitulo());
                txtData.setText(selecionado.getData());
                txtHorario.setText(selecionado.getHorario());
            }
        });
    }

    private void carregarTabela() {
        try {
            listaReunioes = FXCollections.observableArrayList(
                    ArquivoReuniao.lerLista()
            );
            tabela.setItems(listaReunioes);
        } catch (PersistenceException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void cadastrar() {
        try {
            validarCampos();

            Reuniao reuniao = new Reuniao(
                    txtTitulo.getText().trim(),
                    txtData.getText().trim(),
                    txtHorario.getText().trim()
            );

            ArquivoReuniao.adicionar(reuniao);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException | IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void atualizar() {
        Reuniao selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarErro("Selecione uma reunião para atualizar.");
            return;
        }

        try {
            validarCampos();

            Reuniao nova = new Reuniao(
                    txtTitulo.getText().trim(),
                    txtData.getText().trim(),
                    txtHorario.getText().trim()
            );

            ArquivoReuniao.atualizar(selecionada, nova);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException | IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void excluir() {
        Reuniao selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarErro("Selecione uma reunião para excluir.");
            return;
        }

        try {
            ArquivoReuniao.excluir(selecionada);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtData.clear();
        txtHorario.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        if (txtData.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("A data é obrigatória.");
        }

        if (!txtData.getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("A data deve estar no formato DD/MM/AAAA.");
        }

        if (txtHorario.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O horário é obrigatório.");
        }

        if (!txtHorario.getText().matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("O horário deve estar no formato HH:MM.");
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}