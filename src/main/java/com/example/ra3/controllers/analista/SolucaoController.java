package com.example.ra3.controllers.analista;

import com.example.ra3.domains.analista.Analista;
import com.example.ra3.domains.analista.Solucao;
import com.example.ra3.persistence.analista.ArquivoSolucao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

//Controlador responsável pelo CRUD de Soluções - tabela para listagem, formulário modal para inserção/edição, e modal de confirmação para exclusão.

public class SolucaoController {

    private Stage stage;
    private Analista analista;
    private Scene cenaListagem;
    private ObservableList<Solucao> solucoesObservable; // lista observável

    public SolucaoController(Stage stage, Analista analista) {
        this.stage = stage;
        this.analista = analista;
        criarTelaListagem();
    }

    public void mostrar() {
        carregarSolucoes();
        stage.setScene(cenaListagem);
        stage.setTitle("Minhas Soluções");
        stage.show();
    }

    // Carrega as soluções do analista atual na ObservableList
    private void carregarSolucoes() {
        ArrayList<Solucao> solucoes = ArquivoSolucao.buscarPorAnalista(analista.getEmail());
        solucoesObservable.setAll(solucoes);
    }

    private void criarTelaListagem() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Soluções Propostas");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // TableView para exibir as soluções
        TableView<Solucao> tabela = new TableView<>();
        solucoesObservable = FXCollections.observableArrayList();
        tabela.setItems(solucoesObservable);

        // Colunas da tabela - SimpleStringProperty com getters
        TableColumn<Solucao, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        colTitulo.setPrefWidth(200);

        TableColumn<Solucao, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));
        colDesc.setPrefWidth(300);

        TableColumn<Solucao, String> colEquipe = new TableColumn<>("Equipe Alvo");
        colEquipe.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEquipe()));
        colEquipe.setPrefWidth(150);

        tabela.getColumns().addAll(colTitulo, colDesc, colEquipe);

        Button btnNova = new Button("Nova Solução");
        Button btnEditar = new Button("Editar Selecionada");
        Button btnExcluir = new Button("Excluir Selecionada");
        Button btnVoltar = new Button("Voltar");

        btnNova.setOnAction(e -> mostrarFormulario(null));

        btnEditar.setOnAction(e -> {
            Solucao selecionada = tabela.getSelectionModel().getSelectedItem();
            if (selecionada != null) mostrarFormulario(selecionada);
            else mostrarModalMensagem("Aviso", "Selecione uma solução para editar.");
        });

        btnExcluir.setOnAction(e -> {
            Solucao selecionada = tabela.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                mostrarModalExclusao(selecionada);
            } else {
                mostrarModalMensagem("Aviso", "Selecione uma solução para excluir.");
            }
        });

        btnVoltar.setOnAction(e -> {
            MainAnalistaController main = new MainAnalistaController(stage, analista);
            main.mostrar();
        });

        layout.getChildren().addAll(titulo, tabela, btnNova, btnEditar, btnExcluir, btnVoltar);
        this.cenaListagem = new Scene(layout, 1300, 700);
    }

    //Exibe um formulário modal para criar ou editar uma solução
    private void mostrarFormulario(Solucao solucaoParaEditar) {
        Stage modal = new Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label titulo = new Label(solucaoParaEditar == null ? "Nova Solução" : "Editar Solução");
        titulo.setStyle("-fx-font-size: 16px;");
        grid.add(titulo, 0, 0, 2, 1);

        grid.add(new Label("Título:"), 0, 1);
        TextField txtTitulo = new TextField();
        grid.add(txtTitulo, 1, 1);

        grid.add(new Label("Descrição:"), 0, 2);
        TextArea txtDescricao = new TextArea();
        txtDescricao.setPrefRowCount(4);
        txtDescricao.setPrefWidth(300);
        grid.add(txtDescricao, 1, 2);

        grid.add(new Label("Equipe Alvo:"), 0, 3);
        TextField txtEquipe = new TextField();
        grid.add(txtEquipe, 1, 3);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        Label lblMsg = new Label();
        grid.add(btnSalvar, 0, 4);
        grid.add(btnCancelar, 1, 4);
        grid.add(lblMsg, 0, 5, 2, 1);

        // Se for edição, preenche os campos com os dados existentes
        if (solucaoParaEditar != null) {
            txtTitulo.setText(solucaoParaEditar.getTitulo());
            txtDescricao.setText(solucaoParaEditar.getDescricao());
            txtEquipe.setText(solucaoParaEditar.getEquipe());
        }

        btnSalvar.setOnAction(e -> {
            String novoTitulo = txtTitulo.getText().trim();
            String novaDesc = txtDescricao.getText().trim();
            String novaEquipe = txtEquipe.getText().trim();

            if (novoTitulo.isEmpty() || novaDesc.isEmpty() || novaEquipe.isEmpty()) {
                lblMsg.setText("Preencha todos os campos!");
                return;
            }

            if (solucaoParaEditar == null) {
                // Inserção
                Solucao nova = new Solucao(novoTitulo, novaDesc, novaEquipe, analista.getEmail());
                ArquivoSolucao.adicionarSolucao(nova);
            } else {
                // Atualização: cria uma nova solução (com mesmo e-mail do analista) e substitui.
                Solucao atualizada = new Solucao(novoTitulo, novaDesc, novaEquipe, analista.getEmail());
                ArquivoSolucao.atualizarSolucao(solucaoParaEditar, atualizada);
            }
            modal.close();
            carregarSolucoes(); // recarrega a tabela
        });

        btnCancelar.setOnAction(e -> modal.close());

        Scene sceneModal = new Scene(grid, 1300, 700);
        modal.setScene(sceneModal);
        modal.showAndWait();
    }

    //Modal de mensagem simples
    private void mostrarModalMensagem(String titulo, String mensagem) {
        Stage modal = new Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle(titulo);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label(mensagem);
        msg.setWrapText(true);
        msg.setAlignment(Pos.CENTER);
        msg.setStyle("-fx-font-size: 14px;");

        Button btnOk = new Button("OK");
        btnOk.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnOk);
        Scene scene = new Scene(layout, 500, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }

    //Modal de confirmação para exclusão de uma solução - Segue padrão do Exemplo 3 do meu material
    private void mostrarModalExclusao(Solucao solucao) {
        Stage modal = new Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle("Confirmar exclusão");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label("Excluir a solução \"" + solucao.getTitulo() + "\"?");
        msg.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnNao = new Button("Não");

        btnSim.setOnAction(e -> {
            ArquivoSolucao.excluirSolucao(solucao);
            modal.close();
            carregarSolucoes();
        });

        btnNao.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnSim, btnNao);
        Scene scene = new Scene(layout, 500, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }
}