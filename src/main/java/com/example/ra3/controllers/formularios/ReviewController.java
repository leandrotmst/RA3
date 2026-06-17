package com.example.ra3.controllers.formularios;

import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.formularios.Review;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReviewController {
    private Stage stage;
    private TextField txtSolucao;
    private TextField txtResultado;
    private TextField txtData;
    private TableView<Review> tabela;

    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static final ObservableList<Review> listaReviews = FXCollections.observableArrayList();

    private Review reviewEmEdicao = null;

    public ReviewController(Stage stage){
        this.stage = stage;
    }

    public void mostrar(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label lblSolucao = new Label("Solução implementada:");
        txtSolucao = new TextField();
        txtSolucao.setPrefWidth(400);

        Label lblResultado = new Label("Resultado obtido:");
        txtResultado = new TextField();
        txtResultado.setPrefWidth(400);

        Label lblData = new Label("Data do registro (DD/MM/AAAA):");
        txtData = new TextField();
        txtData.setPrefWidth(400);
        txtData.setPromptText("Ex: 17/06/2026");
        txtData.textProperty().addListener((obs, antigo, novo) -> {
            if (novo.length() > 10) txtData.setText(antigo);
        });

        grid.add(lblSolucao, 0, 0);
        grid.add(txtSolucao, 1, 0);
        grid.add(lblResultado, 0, 1);
        grid.add(txtResultado, 1, 1);
        grid.add(lblData, 0, 2);
        grid.add(txtData, 1, 2);

        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(event -> handleBtnReviewSaveOnClick());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(event -> handleBtnReviewDeleteOnClick());

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(event -> handleBtnReviewEditOnClick());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> handleBtnVoltarOnClick());

        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);

        tabela = new TableView<>();
        tabela.setItems(listaReviews);

        TableColumn<Review, String> colSolucao = new TableColumn<>("Solucão implementada");
        colSolucao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSolucao()));

        TableColumn<Review, String> colResultado = new TableColumn<>("Resultado obtido");
        colResultado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResultado()));

        TableColumn<Review, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataRegistro().format(FORMATO_BR)));

        tabela.getColumns().addAll(colSolucao, colResultado, colData);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        root.getChildren().addAll(grid, buttonsBox, tabela);

        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    private void handleBtnReviewSaveOnClick() {
        String solucao = txtSolucao.getText().trim();
        String resultado = txtResultado.getText().trim();
        String dataTexto = txtData.getText().trim();

        if (solucao.isEmpty() || resultado.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vazios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataTexto, FORMATO_BR);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Inválida");
            alert.setHeaderText(null);
            alert.setContentText("Data inválida! Use o formato DD/MM/AAAA. Ex: 17/06/2026");
            alert.showAndWait();
            return;
        }

        if (reviewEmEdicao != null) {
            reviewEmEdicao.setSolucao(solucao);
            reviewEmEdicao.setResultado(resultado);
            reviewEmEdicao.setDataRegistro(data);
            tabela.refresh();
            reviewEmEdicao = null;
        } else {
            listaReviews.add(new Review(solucao, resultado, data));
        }

        txtSolucao.clear();
        txtResultado.clear();
        txtData.clear();
    }

    private void handleBtnReviewEditOnClick() {
        Review selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma review na lista para editar.");
            alert.showAndWait();
            return;
        }

        txtSolucao.setText(selecionado.getSolucao());
        txtResultado.setText(selecionado.getResultado());
        txtData.setText(selecionado.getDataRegistro().format(FORMATO_BR));
        reviewEmEdicao = selecionado;
    }

    private void handleBtnReviewDeleteOnClick() {
        Review selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma review na lista para excluir.");
            alert.showAndWait();
            return;
        }

        listaReviews.remove(selecionado);

        if (selecionado == reviewEmEdicao) {
            reviewEmEdicao = null;
            txtSolucao.clear();
            txtResultado.clear();
            txtData.clear();
        }
    }

    private void handleBtnVoltarOnClick() {
        MainFormulariosController mainFormulariosController = new MainFormulariosController(stage);
        mainFormulariosController.mostrar();
    }

}

