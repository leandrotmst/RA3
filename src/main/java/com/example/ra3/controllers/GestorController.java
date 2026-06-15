package com.example.ra3.controllers;

import com.example.ra3.domains.Gestor;
import com.example.ra3.persistence.ArquivoGestor;
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

public class GestorController {
    private Stage stage;
    private TextField txtNome, txtEmail, txtTelefone;
    private TableView<Gestor> tabela;
    private static final ObservableList<Gestor> listaGestors = FXCollections.observableArrayList();

    static {
        try {
            listaGestors.addAll(ArquivoGestor.lerLista());
        } catch (FuncionarioException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
    }

    private Gestor gestorEmEdicao = null;

    public GestorController(Stage stage){ this.stage = stage; }

    public void mostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);
        txtNome = new TextField(); txtEmail = new TextField(); txtTelefone = new TextField();
        grid.add(new Label("Nome:"), 0, 0); grid.add(txtNome, 1, 0);
        grid.add(new Label("E-mail:"), 0, 1); grid.add(txtEmail, 1, 1);
        grid.add(new Label("Telefone:"), 0, 2); grid.add(txtTelefone, 1, 2);
        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar"); btnSalvar.setOnAction(e -> handleSalvar());
        Button btnExcluir = new Button("Excluir"); btnExcluir.setOnAction(e -> handleExcluir());
        Button btnEditar = new Button("Editar"); btnEditar.setOnAction(e -> handleEditar());
        Button btnVoltar = new Button("Voltar"); btnVoltar.setOnAction(e -> new MainController(stage).mostrar());
        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);
        tabela = new TableView<>();
        tabela.setItems(listaGestors);
        TableColumn<Gestor, String> colNome = new TableColumn<>("Nome");
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
            String n = txtNome.getText().trim(), m = txtEmail.getText().trim(), t = txtTelefone.getText().trim();
            if (n.isEmpty() || m.isEmpty() || t.isEmpty()) throw new Exception("Campos vazios");
            if (gestorEmEdicao != null) {
                gestorEmEdicao.setNome(n); gestorEmEdicao.setEmail(m); gestorEmEdicao.setTelefone(t);
                tabela.refresh(); gestorEmEdicao = null;
            } else {
                listaGestors.add(new Gestor(n, m, t));
            }
            ArquivoGestor.salvarLista(listaGestors);
            txtNome.clear(); txtEmail.clear(); txtTelefone.clear();
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void handleEditar() {
        Gestor s = tabela.getSelectionModel().getSelectedItem();
        if (s == null) return;
        txtNome.setText(s.getNome()); txtEmail.setText(s.getEmail()); txtTelefone.setText(s.getTelefone());
        gestorEmEdicao = s;
    }

    private void handleExcluir() {
        try {
            Gestor s = tabela.getSelectionModel().getSelectedItem();
            if (s == null) return;
            listaGestors.remove(s);
            ArquivoGestor.salvarLista(listaGestors);
        } catch (Exception e) { mostrarErro(e.getMessage()); }
    }

    private void mostrarErro(String m) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText(m); a.showAndWait();
    }
}
