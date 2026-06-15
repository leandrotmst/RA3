package com.example.ra3.controllers;

import com.example.ra3.domains.Funcionario;
import com.example.ra3.persistence.ArquivoFuncionario;
import com.example.ra3.exceptions.FuncionarioException;
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

public class FuncionarioController {
    private Stage stage;
    private TextField txtNome, txtEmail, txtEquipe;
    private TableView<Funcionario> tabela;
    private static final ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

    static {
        try {
            listaFuncionarios.addAll(ArquivoFuncionario.lerLista());
        } catch (FuncionarioException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
    }

    private Funcionario funcionarioEmEdicao = null;

    public FuncionarioController(Stage stage){ this.stage = stage; }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);
        txtNome = new TextField(); txtEmail = new TextField(); txtEquipe = new TextField();
        grid.add(new Label("Nome:"), 0, 0); grid.add(txtNome, 1, 0);
        grid.add(new Label("E-mail:"), 0, 1); grid.add(txtEmail, 1, 1);
        grid.add(new Label("Equipe:"), 0, 2); grid.add(txtEquipe, 1, 2);
        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar"); btnSalvar.setOnAction(e -> handleSalvar());
        Button btnExcluir = new Button("Excluir"); btnExcluir.setOnAction(e -> handleExcluir());
        Button btnEditar = new Button("Editar"); btnEditar.setOnAction(e -> handleEditar());
        Button btnVoltar = new Button("Voltar"); btnVoltar.setOnAction(e -> new MainController(stage).mostrar());
        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);
        tabela = new TableView<>();
        tabela.setItems(listaFuncionarios);
        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        tabela.getColumns().add(colNome);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        root.getChildren().addAll(grid, buttonsBox, tabela);
        stage.setScene(new Scene(root, 1400, 800));
        stage.show();
    }

    private void handleSalvar() {
        try {
            String n = txtNome.getText().trim(), m = txtEmail.getText().trim(), q = txtEquipe.getText().trim();
            if (n.isEmpty() || m.isEmpty() || q.isEmpty()) throw new Exception("Campos vazios");
            if (funcionarioEmEdicao != null) {
                funcionarioEmEdicao.setNome(n); funcionarioEmEdicao.setEmail(m); funcionarioEmEdicao.setEquipe(q);
                tabela.refresh(); funcionarioEmEdicao = null;
            } else {
                listaFuncionarios.add(new Funcionario(n, m, q));
            }
            ArquivoFuncionario.salvarLista(listaFuncionarios);
            txtNome.clear(); txtEmail.clear(); txtEquipe.clear();
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void handleEditar() {
        Funcionario s = tabela.getSelectionModel().getSelectedItem();
        if (s == null) return;
        txtNome.setText(s.getNome()); txtEmail.setText(s.getEmail()); txtEquipe.setText(s.getEquipe());
        funcionarioEmEdicao = s;
    }

    private void handleExcluir() {
        try {
            Funcionario s = tabela.getSelectionModel().getSelectedItem();
            if (s == null) return;
            listaFuncionarios.remove(s);
            ArquivoFuncionario.salvarLista(listaFuncionarios);
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void mostrarErro(String m) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText(m); a.showAndWait();
    }
}
