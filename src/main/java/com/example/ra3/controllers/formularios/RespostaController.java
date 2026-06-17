package com.example.ra3.controllers.formularios;

import com.example.ra3.controllers.MainController;
import com.example.ra3.domains.formularios.Resposta;
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

public class RespostaController {
    private Stage stage;
    private TextField txtNivelEstresse;
    private TextField txtResumo;
    private TableView<Resposta> tabela;

    static final ObservableList<Resposta> listaRespostas = FXCollections.observableArrayList();

    private Resposta respostaEmEdicao = null;

    public RespostaController(Stage stage) {this.stage = stage;}

    public void mostrar(){
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label lblNivelEstresse = new Label("Qual foi o seu nível de estresse/emocional na semana de 0 a 5? (Sendo 0 - Ótimo e 5 - Crítico):");
        txtNivelEstresse = new TextField();
        txtNivelEstresse.setPrefWidth(400);

        Label lblResumo = new Label("Como foi sua semana? Descreva pontos positivos, desafios ou o que quiser compartilhar:");
        txtResumo = new TextField();
        txtResumo.setPrefWidth(400);


        grid.add(lblNivelEstresse, 0, 0);
        grid.add(txtNivelEstresse, 1, 0);
        grid.add(lblResumo, 0, 1);
        grid.add(txtResumo, 1, 1);

        HBox buttonsBox = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(event -> handleBtnRespostaSaveOnClick());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(event -> handleBtnRespostaDeleteOnClick());

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(event -> handleBtnRespostaEditOnClick());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> handleBtnVoltarOnClick());

        buttonsBox.getChildren().addAll(btnSalvar, btnExcluir, btnEditar, btnVoltar);

        tabela = new TableView<>();
        tabela.setItems(listaRespostas);

        TableColumn<Resposta, String> colNivelEstresse = new TableColumn<>("Nível de Estresse");
        colNivelEstresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivelEstresse()));

        TableColumn<Resposta, String> colResumo = new TableColumn<>("Resumo da semana");
        colResumo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResumo()));

        tabela.getColumns().addAll(colNivelEstresse, colResumo);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        root.getChildren().addAll(grid, buttonsBox, tabela);

        Scene cena = new Scene(root, 1400, 800);
        stage.setScene(cena);
        stage.show();
    }

    private void handleBtnRespostaSaveOnClick() {
        String nivelEstresse = txtNivelEstresse.getText().trim();
        String resumo = txtResumo.getText().trim();

        if (nivelEstresse.isEmpty() || resumo.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vazios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        if (respostaEmEdicao != null) {
            respostaEmEdicao.setNivelEstresse(nivelEstresse);
            respostaEmEdicao.setResumo(resumo);
            tabela.refresh();
            respostaEmEdicao = null;
        } else {
            listaRespostas.add(new Resposta(nivelEstresse, resumo));
        }

        txtNivelEstresse.clear();
        txtResumo.clear();
    }

    private void handleBtnRespostaEditOnClick() {
        Resposta selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um formulário na lista para editar.");
            alert.showAndWait();
            return;
        }

        txtNivelEstresse.setText(selecionado.getNivelEstresse());
        txtResumo.setText(selecionado.getResumo());
        respostaEmEdicao = selecionado;
    }

    private void handleBtnRespostaDeleteOnClick() {
        Resposta selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um formulário na lista para excluir.");
            alert.showAndWait();
            return;
        }

        listaRespostas.remove(selecionado);

        if (selecionado == respostaEmEdicao) {
            respostaEmEdicao = null;
            txtNivelEstresse.clear();
            txtResumo.clear();
        }
    }

    private void handleBtnVoltarOnClick() {
        MainFormulariosController mainFormulariosController = new MainFormulariosController(stage);
        mainFormulariosController.mostrar();
    }
}



