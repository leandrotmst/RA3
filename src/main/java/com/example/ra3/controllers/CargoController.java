package com.example.ra3.controllers;

import com.example.ra3.domains.Funcionario;
import com.example.ra3.domains.Gestor;
import com.example.ra3.domains.MembroSistema;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CargoController {
    private Stage stage;
    private TableView<MembroSistema> tabela;

    public CargoController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        Label lblTitulo = new Label("Lista de Membros por Cargo");

        tabela = new TableView<>();

        TableColumn<MembroSistema, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));

        TableColumn<MembroSistema, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));

        TableColumn<MembroSistema, String> colCargo = new TableColumn<>("Cargo");
        colCargo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCargo()));

        tabela.getColumns().addAll(colNome, colEmail, colCargo);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Button btnAtualizar = new Button("Atualizar Lista");
        btnAtualizar.setOnAction(event -> carregarDados());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> {
            MainController mainController = new MainController(stage);
            mainController.mostrar();
        });

        carregarDados();

        root.getChildren().addAll(lblTitulo, btnAtualizar, btnVoltar, tabela);
        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    private void carregarDados() {
        ObservableList<MembroSistema> membros = FXCollections.observableArrayList();

        for (Funcionario f : FuncionarioController.listaFuncionarios) {
            membros.add(new MembroSistema(f.getNome(), f.getEmail(), "Funcionário"));
        }

        for (Gestor g : GestorController.listaGestors) {
            membros.add(new MembroSistema(g.getNome(), g.getEmail(), "Gestor"));
        }

        tabela.setItems(membros);
    }
}