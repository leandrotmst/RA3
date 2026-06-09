package com.example.ra3;

import javafx.application.Application;
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

public class Main extends Application {

    // Lista na memória que alimenta a TableView
    private final ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();
    private TableView<Funcionario> tabela;

    // Elementos do formulário
    private TextField txtNome;
    private TextField txtEmail;
    private TextField txtEquipe;

    // Guarda o funcionário que está selecionado na tabela para Edição/Exclusão
    private Funcionario funcionarioSelecionado;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gerenciamento de Funcionários");

        // --- CONSTRUÇÃO DO FORMULÁRIO (Igual à imagem enviada) ---
        GridPane gridFormulario = new GridPane();
        gridFormulario.setAlignment(Pos.CENTER);
        gridFormulario.setHgap(15);
        gridFormulario.setVgap(15);
        gridFormulario.setPadding(new Insets(20));

        // Labels com fonte maior para combinar com o mockup
        Label lblNome = new Label("Nome:");
        lblNome.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        txtNome = new TextField();
        txtNome.setPrefWidth(300);
        txtNome.setStyle("-fx-background-radius: 15; -fx-background-color: #E0E0E0; -fx-padding: 8;");

        Label lblEmail = new Label("E-mail:");
        lblEmail.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        txtEmail = new TextField();
        txtEmail.setStyle("-fx-background-radius: 15; -fx-background-color: #E0E0E0; -fx-padding: 8;");

        Label lblEquipe = new Label("Equipe:");
        lblEquipe.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        txtEquipe = new TextField();
        txtEquipe.setStyle("-fx-background-radius: 15; -fx-background-color: #E0E0E0; -fx-padding: 8;");

        // Posicionando no Grid (Coluna, Linha)
        gridFormulario.add(lblNome, 0, 0);
        gridFormulario.add(txtNome, 1, 0);
        gridFormulario.add(lblEmail, 0, 1);
        gridFormulario.add(txtEmail, 1, 1);
        gridFormulario.add(lblEquipe, 0, 2);
        gridFormulario.add(txtEquipe, 1, 2);

        // --- BOTÕES (Estilizados como na imagem) ---
        Button btnSalvar = new Button("Salvar");
        Button btnEditar = new Button("Editar");
        Button btnExcluir = new Button("Excluir");

        String estiloBotao = "-fx-background-radius: 20; -fx-background-color: #CCCCCC; "
                + "-fx-font-weight: bold; -fx-padding: 10 30 10 30; -fx-cursor: hand;";
        btnSalvar.setStyle(estiloBotao);
        btnEditar.setStyle(estiloBotao);
        btnExcluir.setStyle(estiloBotao);

        HBox barraBotoes = new HBox(20, btnSalvar, btnEditar, btnExcluir);
        barraBotoes.setAlignment(Pos.CENTER);
        barraBotoes.setPadding(new Insets(10, 0, 20, 0));

        // --- TABELA DE FUNCIONÁRIOS (Abaixo do CRUD) ---
        tabela = new TableView<>();
        tabela.setItems(listaFuncionarios);

        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colNome.setPrefWidth(250);

        TableColumn<Funcionario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colEmail.setPrefWidth(250);

        TableColumn<Funcionario, String> colEquipe = new TableColumn<>("Equipe");
        colEquipe.setCellValueFactory(cellData -> cellData.getValue().equipeProperty());
        colEquipe.setPrefWidth(180);

        tabela.getColumns().addAll(colNome, colEmail, colEquipe);
        tabela.setPrefHeight(250);

        // --- LÓGICA DE EVENTOS (Ações dos botões) ---

        // Ação Salvar (Inserir) [cite: 35]
        btnSalvar.setOnAction(e -> {
            if (!txtNome.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtEquipe.getText().isEmpty()) {
                Funcionario novo = new Funcionario(txtNome.getText(), txtEmail.getText(), txtEquipe.getText());
                listaFuncionarios.add(novo);
                limparCampos();
            } else {
                mostrarAlerta("Erro", "Preencha todos os campos para salvar.");
            }
        });

        // Evento de selecionar linha da tabela (Consulta) [cite: 45, 46]
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novoSelecao) -> {
            if (novoSelecao != null) {
                funcionarioSelecionado = novoSelecao;
                txtNome.setText(novoSelecao.getNome());
                txtEmail.setText(novoSelecao.getEmail());
                txtEquipe.setText(novoSelecao.getEquipe());
            }
        });

        // Ação Editar (Alterar) [cite: 37]
        btnEditar.setOnAction(e -> {
            if (funcionarioSelecionado != null) {
                funcionarioSelecionado.setNome(txtNome.getText());
                funcionarioSelecionado.setEmail(txtEmail.getText());
                funcionarioSelecionado.setEquipe(txtEquipe.getText());
                tabela.refresh(); // Força a tabela a redesenhar os valores alterados
                limparCampos();
            } else {
                mostrarAlerta("Aviso", "Selecione um funcionário na tabela para editar.");
            }
        });

        // Ação Excluir [cite: 38]
        btnExcluir.setOnAction(e -> {
            if (funcionarioSelecionado != null) {
                listaFuncionarios.remove(funcionarioSelecionado);
                limparCampos();
            } else {
                mostrarAlerta("Aviso", "Selecione um funcionário na tabela para excluir.");
            }
        });

        // --- LAYOUT FINAL ---
        VBox layoutPrincipal = new VBox(10);
        layoutPrincipal.setPadding(new Insets(15));
        layoutPrincipal.getChildren().addAll(gridFormulario, barraBotoes, new Separator(), new Label("Lista de Funcionários:"), tabela);

        Scene scene = new Scene(layoutPrincipal, 720, 650);
        stage.setScene(scene);
        stage.show();
    }

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtEquipe.clear();
        tabela.getSelectionModel().clearSelection();
        funcionarioSelecionado = null;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}