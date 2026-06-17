package com.example.ra3.controllers.cargo;

import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.gestor.Funcionario;
import com.example.ra3.exceptions.gestor.PersistenceException;
import com.example.ra3.persistence.gestor.ArquivoFuncionario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SetorController {
    private Stage stage;
    private TextField txtBusca;
    private TableView<Funcionario> tabela;
    private ObservableList<Funcionario> todosFuncionarios;

    public SetorController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        Label lblTitulo = new Label("Consulta por Setor");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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
        btnVoltar.setOnAction(event -> new MainGerenciamentoController(stage).mostrar());
        carregarTodos();

        root.getChildren().addAll(lblTitulo, buscaBox, btnVoltar, tabela);
        stage.setScene(new Scene(root, 1400, 800));
        stage.show();
    }

    private void carregarTodos() {
        try {
            ArrayList<Funcionario> lista = ArquivoFuncionario.lerLista();
            todosFuncionarios = FXCollections.observableArrayList(lista);
        } catch (PersistenceException e) {
            todosFuncionarios = FXCollections.observableArrayList();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Erro ao carregar funcionários: " + e.getMessage());
            a.showAndWait();
        }
        tabela.setItems(todosFuncionarios);
    }

    private void handleBuscar() {
        String setor = txtBusca.getText().trim().toLowerCase();

        if (setor.isEmpty()) {
            tabela.setItems(todosFuncionarios);
            return;
        }

        ObservableList<Funcionario> filtrados = FXCollections.observableArrayList();
        for (Funcionario f : todosFuncionarios) {
            if (f.getEquipe().toLowerCase().contains(setor)) {
                filtrados.add(f);
            }
        }
        tabela.setItems(filtrados);
    }
}
