package com.example.ra3.controllers;

import com.example.ra3.domains.Funcionario;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetorController {
    private Stage stage;
    private TextField txtBusca;
    private TableView<Funcionario> tabela;

    public SetorController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        Label lblTitulo = new Label("Consulta por Setor");

        HBox buscaBox = new HBox(10);
        Label lblBusca = new Label("Setor:");
        txtBusca = new TextField();
        txtBusca.setPromptText("Ex: TI, RH, Financeiro...");
        txtBusca.setPrefWidth(300);

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(event -> handleBuscar());

        buscaBox.getChildren().addAll(lblBusca, txtBusca, btnBuscar);

        tabela = new TableView<>();

        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));

        TableColumn<Funcionario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));

        TableColumn<Funcionario, String> colSetor = new TableColumn<>("Setor");
        colSetor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEquipe()));

        tabela.getColumns().addAll(colNome, colEmail, colSetor);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> {
            MainController mainController = new MainController(stage);
            mainController.mostrar();
        });

        root.getChildren().addAll(lblTitulo, buscaBox, btnVoltar, tabela);
        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    private void handleBuscar() {
        String setor = txtBusca.getText().trim().toLowerCase();

        if (setor.isEmpty()) {
            tabela.setItems(FuncionarioController.listaFuncionarios);
            return;
        }

        ObservableList<Funcionario> filtrados = FXCollections.observableArrayList();
        for (Funcionario f : FuncionarioController.listaFuncionarios) {
            if (f.getEquipe().toLowerCase().contains(setor)) {
                filtrados.add(f);
            }
        }
        tabela.setItems(filtrados);
    }
}