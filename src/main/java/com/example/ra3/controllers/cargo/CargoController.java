package com.example.ra3.controllers.cargo;

import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.MembroSistema;
import com.example.ra3.domains.gestor.Funcionario;
import com.example.ra3.domains.gestor.Gestor;
import com.example.ra3.domains.analista.Analista;
import com.example.ra3.exceptions.gestor.PersistenceException;
import com.example.ra3.persistence.gestor.ArquivoFuncionario;
import com.example.ra3.persistence.gestor.ArquivoGestor;
import com.example.ra3.persistence.analista.ArquivoAnalista;
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
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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
        btnVoltar.setOnAction(event -> new MainGerenciamentoController(stage).mostrar());
        carregarDados();

        root.getChildren().addAll(lblTitulo, btnAtualizar, btnVoltar, tabela);
        stage.setScene(new Scene(root, 1400, 800));
        stage.show();
    }

    private void carregarDados() {
        ObservableList<MembroSistema> membros = FXCollections.observableArrayList();

        try {
            for (Funcionario f : ArquivoFuncionario.lerLista()) {
                membros.add(new MembroSistema(f.getNome(), f.getEmail(), "Funcionário"));
            }
        } catch (PersistenceException e) {
            mostrarErro("Erro ao carregar funcionários: " + e.getMessage());
        }

        try {
            for (Gestor g : ArquivoGestor.lerLista()) {
                membros.add(new MembroSistema(g.getNome(), g.getEmail(), "Gestor"));
            }
        } catch (PersistenceException e) {
            mostrarErro("Erro ao carregar gestores: " + e.getMessage());
        }

        for (Analista a : ArquivoAnalista.lerLista()) {
            membros.add(new MembroSistema(a.getNome(), a.getEmail(), "Analista"));
        }

        tabela.setItems(membros);
    }

    private void mostrarErro(String mensagem) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(mensagem);
        a.showAndWait();
    }
}
