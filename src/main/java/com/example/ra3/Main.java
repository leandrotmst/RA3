package com.example.ra3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // Lista observável que armazena os dados na memória
    private final ObservableList<Contato> listaContatos = FXCollections.observableArrayList();

    // Tabela e Campos do Formulário
    private TableView<Contato> tabela = new TableView<>();
    private TextField txtNome = new TextField();
    private TextField txtEmail = new TextField();

    // Botões
    private Button btnSalvar = new Button("Salvar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar");

    // Mantém o contato que está selecionado para edição
    private Contato contatoSelecionado = null;

    @Override
    public void start(Stage stage) {
        stage.setTitle("CRUD Contatos - JavaFX");

        // 1. Configuração do Formulário (GridPane)
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(10));
        formulario.setAlignment(Pos.CENTER);

        formulario.add(new Label("Nome:"), 0, 0);
        formulario.add(txtNome, 1, 0);
        formulario.add(new Label("E-mail:"), 0, 1);
        formulario.add(txtEmail, 1, 1);

        // 2. Configuração dos Botões
        HBox containerBotoes = new HBox(10);
        containerBotoes.setAlignment(Pos.CENTER);
        containerBotoes.getChildren().addAll(btnSalvar, btnExcluir, btnLimpar);
        formulario.add(containerBotoes, 1, 3);

        // 3. Configuração da Tabela (TableView)
        TableColumn<Contato, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colNome.setPrefWidth(150);

        TableColumn<Contato, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colEmail.setPrefWidth(180);

        tabela.getColumns().addAll(colNome, colEmail);
        tabela.setItems(listaContatos);

        // 4. Eventos e Lógica do CRUD

        // Ação de Salvar (Serve tanto para Criar quanto para Atualizar)
        btnSalvar.setOnAction(e -> salvarContato());

        // Ação de Excluir
        btnExcluir.setOnAction(e -> excluirContato());

        // Ação de Limpar formulário
        btnLimpar.setOnAction(e -> limparFormulario());

        // Detectar clique na tabela (Preenche o formulário para Edição)
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                contatoSelecionado = novo;
                txtNome.setText(novo.getNome());
                txtEmail.setText(novo.getEmail());
                btnSalvar.setText("Atualizar");
            }
        });

        // 5. Layout Principal
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(formulario, tabela);

        Scene scene = new Scene(root, 500, 450);
        stage.setScene(scene);
        stage.show();
    }

    // --- OPERAÇÕES DO CRUD ---

    private void salvarContato() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Aviso", "Campos Obrigatórios", "Por favor, preencha pelo menos Nome e E-mail.");
            return;
        }

        if (contatoSelecionado == null) {
            // C - CREATE (Inserir novo)
            Contato novoContato = new Contato(nome, email);
            listaContatos.add(novoContato);
        } else {
            // U - UPDATE (Editar existente)
            contatoSelecionado.setNome(nome);
            contatoSelecionado.setEmail(email);
            tabela.refresh(); // Força a tabela a se atualizar visualmente
        }

        limparFormulario();
    }

    private void excluirContato() {
        if (contatoSelecionado != null) {
            // D - DELETE (Remover)
            listaContatos.remove(contatoSelecionado);
            limparFormulario();
        } else {
            mostrarAlerta("Aviso", "Nenhum contato selecionado", "Selecione um contato na tabela para excluir.");
        }
    }

    private void limparFormulario() {
        txtNome.clear();
        txtEmail.clear();
        contatoSelecionado = null;
        tabela.getSelectionModel().clearSelection();
        btnSalvar.setText("Salvar");
    }

    private void mostrarAlerta(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}