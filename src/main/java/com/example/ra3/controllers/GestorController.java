package com.example.ra3.controllers;

import com.example.ra3.domains.Gestor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestorController {
    private Stage stage;
    private TextField txtNome;
    private TextField txtEmail;
    private TextField txtTelefone;
    private TableView<Gestor> tabela;

    // Persistent in-memory list for employees
    private static final ObservableList<Gestor> listaGestors = FXCollections.observableArrayList();

    // Keeps track of the employee being edited
    private Gestor gestorEmEdicao = null;

    public GestorController(Stage stage){
        this.stage = stage;
    }

    /**
     * Método responsável por criar os controles (componentes) para tratamento dos dados.
     */
    public void mostrar(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        // GridPane for Labels and Inputs (labels stacked, inputs on the side)
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setPrefWidth(400);

        Label lblEmail = new Label("E-mail:");
        txtEmail = new TextField();
        txtEmail.setPrefWidth(400);

        Label lblTelefone = new Label("Telefone:");
        txtTelefone = new TextField();
        txtTelefone.setPrefWidth(400);

        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblEmail, 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(lblTelefone, 0, 2);
        grid.add(txtTelefone, 1, 2);

        // HBox for Buttons (side by side)
        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(event -> handleBtnGestorSaveOnClick());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(event -> handleBtnGestorDeleteOnClick());

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(event -> handleBtnGestorEditOnClick());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> handleBtnVoltarOnClick());

        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);

        // TableView for added employees
        tabela = new TableView<>();
        tabela.setItems(listaGestors);

        TableColumn<Gestor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<Gestor, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<Gestor, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));

        tabela.getColumns().addAll(colNome, colEmail, colTelefone);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Make the table fill all remaining height
        VBox.setVgrow(tabela, Priority.ALWAYS);

        root.getChildren().addAll(grid, buttonsBox, tabela);

        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    /**
     * Salva ou atualiza os dados do funcionário.
     */
    private void handleBtnGestorSaveOnClick() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vazios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        if (gestorEmEdicao != null) {
            // Atualiza funcionário existente
            gestorEmEdicao.setNome(nome);
            gestorEmEdicao.setEmail(email);
            gestorEmEdicao.setTelefone(telefone);
            tabela.refresh();
            gestorEmEdicao = null;
        } else {
            // Cria novo funcionário
            Gestor novo = new Gestor(nome, email, telefone);
            listaGestors.add(novo);
        }

        txtNome.clear();
        txtEmail.clear();
        txtTelefone.clear();
    }

    /**
     * Carrega os dados do funcionário selecionado nos inputs para edição.
     */
    private void handleBtnGestorEditOnClick() {
        Gestor selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um funcionário na lista para editar.");
            alert.showAndWait();
            return;
        }

        txtNome.setText(selecionado.getNome());
        txtEmail.setText(selecionado.getEmail());
        txtTelefone.setText(selecionado.getTelefone());
        gestorEmEdicao = selecionado;
    }

    /**
     * Exclui o funcionário selecionado.
     */
    private void handleBtnGestorDeleteOnClick() {
        Gestor selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um funcionário na lista para excluir.");
            alert.showAndWait();
            return;
        }

        listaGestors.remove(selecionado);

        if (selecionado == gestorEmEdicao) {
            gestorEmEdicao = null;
            txtNome.clear();
            txtEmail.clear();
            txtTelefone.clear();
        }
    }

    /**
     * Retorna à tela principal.
     */
    private void handleBtnVoltarOnClick() {
        MainController mainController = new MainController(stage);
        mainController.mostrar();
    }
}
