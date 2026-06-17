package com.example.ra3.controllers.Reu_Per;

import com.example.ra3.domains.Reu_Per.Permissao;
import com.example.ra3.exceptions.gestor.PersistenceException;
import com.example.ra3.persistence.Reu_Per.ArquivoPermissao;
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

public class PermissaoController {

    private Stage stage;
    private TableView<Permissao> tabela;
    private ObservableList<Permissao> listaPermissoes;

    private TextField txtNome;
    private ComboBox<String> cbNivel;
    private ComboBox<String> cbModulo;

    public PermissaoController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titulo = new Label("Solicitação de Permissões");
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
        stage.setTitle("Solicitação Permissões");
        stage.show();
    }

    private void criarFormulario() {
        txtNome = new TextField();
        txtNome.setPromptText("Ex: Editar funcionários");

        cbNivel = new ComboBox<>();
        cbNivel.getItems().addAll("Baixo", "Médio", "Alto", "Administrador");

        cbModulo = new ComboBox<>();
        cbModulo.getItems().addAll("Funcionários", "Reuniões", "Soluções", "Formulários", "Sistema");
    }

    private GridPane montarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(txtNome, 1, 0);

        grid.add(new Label("Nível:"), 0, 1);
        grid.add(cbNivel, 1, 1);

        grid.add(new Label("Módulo:"), 0, 2);
        grid.add(cbModulo, 1, 2);

        return grid;
    }

    private void criarTabela() {
        tabela = new TableView<>();

        TableColumn<Permissao, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getNome()));

        TableColumn<Permissao, String> colNivel = new TableColumn<>("Nível");
        colNivel.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getNivel()));

        TableColumn<Permissao, String> colModulo = new TableColumn<>("Módulo");
        colModulo.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getModulo()));

        tabela.getColumns().addAll(colNome, colNivel, colModulo);
        tabela.setPrefHeight(300);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                txtNome.setText(selecionado.getNome());
                cbNivel.setValue(selecionado.getNivel());
                cbModulo.setValue(selecionado.getModulo());
            }
        });
    }

    private void carregarTabela() {
        try {
            listaPermissoes = FXCollections.observableArrayList(
                    ArquivoPermissao.lerLista()
            );
            tabela.setItems(listaPermissoes);
        } catch (PersistenceException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void cadastrar() {
        try {
            validarCampos();

            Permissao permissao = new Permissao(
                    txtNome.getText().trim(),
                    cbNivel.getValue(),
                    cbModulo.getValue()
            );

            ArquivoPermissao.adicionar(permissao);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException | IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void atualizar() {
        Permissao selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarErro("Selecione uma permissão para atualizar.");
            return;
        }

        try {
            validarCampos();

            Permissao nova = new Permissao(
                    txtNome.getText().trim(),
                    cbNivel.getValue(),
                    cbModulo.getValue()
            );

            ArquivoPermissao.atualizar(selecionada, nova);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException | IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void excluir() {
        Permissao selecionada = tabela.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarErro("Selecione uma permissão para excluir.");
            return;
        }

        try {
            ArquivoPermissao.excluir(selecionada);
            carregarTabela();
            limparCampos();

        } catch (PersistenceException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        cbNivel.setValue(null);
        cbModulo.setValue(null);
        tabela.getSelectionModel().clearSelection();
    }

    private void validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da permissão é obrigatório.");
        }

        if (cbNivel.getValue() == null) {
            throw new IllegalArgumentException("Selecione o nível da permissão.");
        }

        if (cbModulo.getValue() == null) {
            throw new IllegalArgumentException("Selecione o módulo da permissão.");
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