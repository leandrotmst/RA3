package com.example.ra3.controllers;

import com.example.ra3.domains.Funcionario;
import com.example.ra3.domains.Gestor;
import com.example.ra3.persistence.ArquivoFuncionario;
import com.example.ra3.exceptions.PersistenceException;
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
import java.util.ArrayList;

public class FuncionarioController {
    private Stage stage;
    private Gestor gestorLogado;
    private TextField txtNome, txtEmail, txtEquipe;
    private TableView<Funcionario> tabela;
    private ObservableList<Funcionario> listaFiltrada;

    public FuncionarioController(Stage stage, Gestor gestor) {
        this.stage = stage;
        this.gestorLogado = gestor;
        try {
            this.listaFiltrada = FXCollections.observableArrayList(ArquivoFuncionario.buscarPorGestor(gestor.getEmail()));
        } catch (PersistenceException e) {
            this.listaFiltrada = FXCollections.observableArrayList();
            mostrarErro(e.getMessage());
        }
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        
        Label lblTitulo = new Label("Gestão de Funcionários - Gestor: " + gestorLogado.getNome());
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);
        txtNome = new TextField(); txtEmail = new TextField(); txtEquipe = new TextField();
        grid.add(new Label("Nome:"), 0, 0); grid.add(txtNome, 1, 0);
        grid.add(new Label("E-mail:"), 0, 1); grid.add(txtEmail, 1, 1);
        grid.add(new Label("Equipe:"), 0, 2); grid.add(txtEquipe, 1, 2);

        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar"); btnSalvar.setOnAction(e -> handleSalvar());
        Button btnExcluir = new Button("Excluir"); btnExcluir.setOnAction(e -> handleExcluir());
        Button btnSair = new Button("Sair (Logout)"); btnSair.setOnAction(e -> new LoginController(stage).mostrar());
        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnSair);

        tabela = new TableView<>();
        tabela.setItems(listaFiltrada);
        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        TableColumn<Funcionario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        TableColumn<Funcionario, String> colEquipe = new TableColumn<>("Equipe");
        colEquipe.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEquipe()));
        tabela.getColumns().addAll(colNome, colEmail, colEquipe);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        root.getChildren().addAll(lblTitulo, grid, buttonsBox, tabela);
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("RH System - CRUD Funcionário");
        stage.show();
    }

    private void handleSalvar() {
        try {
            String n = txtNome.getText().trim(), m = txtEmail.getText().trim(), q = txtEquipe.getText().trim();
            if (n.isEmpty() || m.isEmpty() || q.isEmpty()) throw new Exception("Preencha todos os campos.");
            
            listaFiltrada.add(new Funcionario(n, m, q, gestorLogado.getEmail()));
            ArquivoFuncionario.atualizarListaGeral(new ArrayList<>(listaFiltrada), gestorLogado.getEmail());
            
            txtNome.clear(); txtEmail.clear(); txtEquipe.clear();
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void handleExcluir() {
        try {
            Funcionario s = tabela.getSelectionModel().getSelectedItem();
            if (s == null) return;
            listaFiltrada.remove(s);
            ArquivoFuncionario.atualizarListaGeral(new ArrayList<>(listaFiltrada), gestorLogado.getEmail());
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void mostrarErro(String m) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText(m); a.showAndWait();
    }
}
