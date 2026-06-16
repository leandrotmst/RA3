package com.example.ra3.controllers;

import com.example.ra3.domains.Funcionario;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FuncionarioController {
    private Stage stage;
    private TextField txtNome;
    private TextField txtEmail;
    private TextField txtEquipe;
    private TableView<Funcionario> tabela;

    static final ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

    private Funcionario funcionarioEmEdicao = null;

    public FuncionarioController(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setPrefWidth(400);

        Label lblEmail = new Label("E-mail:");
        txtEmail = new TextField();
        txtEmail.setPrefWidth(400);

        Label lblEquipe = new Label("Equipe:");
        txtEquipe = new TextField();
        txtEquipe.setPrefWidth(400);

        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(lblEmail, 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(lblEquipe, 0, 2);
        grid.add(txtEquipe, 1, 2);

        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(event -> handleBtnFuncionarioSaveOnClick());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(event -> handleBtnFuncionarioDeleteOnClick());

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(event -> handleBtnFuncionarioEditOnClick());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> handleBtnVoltarOnClick());

        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);

        tabela = new TableView<>();
        tabela.setItems(listaFuncionarios);

        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<Funcionario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<Funcionario, String> colEquipe = new TableColumn<>("Equipe");
        colEquipe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipe()));

        tabela.getColumns().addAll(colNome, colEmail, colEquipe);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        root.getChildren().addAll(grid, buttonsBox, tabela);

        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    private void handleBtnFuncionarioSaveOnClick() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String equipe = txtEquipe.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || equipe.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vazios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        if (funcionarioEmEdicao != null) {
            funcionarioEmEdicao.setNome(nome);
            funcionarioEmEdicao.setEmail(email);
            funcionarioEmEdicao.setEquipe(equipe);
            tabela.refresh();
            funcionarioEmEdicao = null;
        } else {
            listaFuncionarios.add(new Funcionario(nome, email, equipe));
        }

        txtNome.clear();
        txtEmail.clear();
        txtEquipe.clear();
    }

    private void handleBtnFuncionarioEditOnClick() {
        Funcionario selecionado = tabela.getSelectionModel().getSelectedItem();
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
        txtEquipe.setText(selecionado.getEquipe());
        funcionarioEmEdicao = selecionado;
    }

    private void handleBtnFuncionarioDeleteOnClick() {
        Funcionario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um funcionário na lista para excluir.");
            alert.showAndWait();
            return;
        }

        listaFuncionarios.remove(selecionado);

        if (selecionado == funcionarioEmEdicao) {
            funcionarioEmEdicao = null;
            txtNome.clear();
            txtEmail.clear();
            txtEquipe.clear();
        }
    }

    private void handleBtnVoltarOnClick() {
        MainController mainController = new MainController(stage);
        mainController.mostrar();
    }
}