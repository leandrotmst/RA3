package com.example.ra3.controllers.administrador;

import com.example.ra3.domains.administrador.Administrador;
import com.example.ra3.domains.gestor.Gestor;
import com.example.ra3.persistence.administrador.ArquivoGestor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestorController {

    private Stage stage;
    private Administrador administrador;
    private Scene cenaListagem;
    private ObservableList<Gestor> gestoresObservable;

    public GestorController(Stage stage, Administrador administrador) {
        this.stage = stage;
        this.administrador = administrador;
        criarTelaListagem();
    }

    public void mostrar() {
        carregarGestores();
        stage.setScene(cenaListagem);
        stage.setTitle("Gestores");
        stage.show();
    }

    private void carregarGestores() {
        ArrayList<Gestor> gestores = ArquivoGestor.lerLista();
        gestoresObservable.setAll(gestores);
    }

    private void criarTelaListagem() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Gestores");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Gestor> tabela = new TableView<>();
        gestoresObservable = FXCollections.observableArrayList();
        tabela.setItems(gestoresObservable);

        TableColumn<Gestor, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colNome.setPrefWidth(250);

        TableColumn<Gestor, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colEmail.setPrefWidth(280);

        TableColumn<Gestor, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));
        colTelefone.setPrefWidth(180);

        tabela.getColumns().addAll(colNome, colEmail, colTelefone);

        Button btnNovo = new Button("Novo Gestor");
        Button btnEditar = new Button("Editar Selecionado");
        Button btnExcluir = new Button("Excluir Selecionado");
        Button btnVoltar = new Button("Voltar");

        btnNovo.setOnAction(e -> mostrarFormulario(null));

        btnEditar.setOnAction(e -> {
            Gestor selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                mostrarFormulario(selecionado);
            } else {
                mostrarModalMensagem("Aviso", "Selecione um gestor para editar.");
            }
        });

        btnExcluir.setOnAction(e -> {
            Gestor selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                mostrarModalExclusao(selecionado);
            } else {
                mostrarModalMensagem("Aviso", "Selecione um gestor para excluir.");
            }
        });

        btnVoltar.setOnAction(e -> {
            MainAdministradorController main = new MainAdministradorController(stage, administrador);
            main.mostrar();
        });

        layout.getChildren().addAll(titulo, tabela, btnNovo, btnEditar, btnExcluir, btnVoltar);
        this.cenaListagem = new Scene(layout, 1300, 700);
    }

    private void mostrarFormulario(Gestor gestorParaEditar) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label titulo = new Label(gestorParaEditar == null ? "Novo Gestor" : "Editar Gestor");
        titulo.setStyle("-fx-font-size: 16px;");
        grid.add(titulo, 0, 0, 2, 1);

        grid.add(new Label("Nome:"), 0, 1);
        TextField txtNome = new TextField();
        grid.add(txtNome, 1, 1);

        grid.add(new Label("E-mail:"), 0, 2);
        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 2);

        grid.add(new Label("Telefone:"), 0, 3);
        TextField txtTelefone = new TextField();
        grid.add(txtTelefone, 1, 3);

        grid.add(new Label("Senha:"), 0, 4);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 4);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        Label lblMsg = new Label();
        grid.add(btnSalvar, 0, 5);
        grid.add(btnCancelar, 1, 5);
        grid.add(lblMsg, 0, 6, 2, 1);

        if (gestorParaEditar != null) {
            txtNome.setText(gestorParaEditar.getNome());
            txtEmail.setText(gestorParaEditar.getEmail());
            txtTelefone.setText(gestorParaEditar.getTelefone());
            txtSenha.setText(gestorParaEditar.getSenha());
        }

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String senha = txtSenha.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                lblMsg.setText("Preencha todos os campos!");
                return;
            }

            Gestor gestor = new Gestor(nome, email, telefone, senha);
            boolean salvou;
            if (gestorParaEditar == null) {
                salvou = ArquivoGestor.adicionarGestor(gestor);
            } else {
                salvou = ArquivoGestor.atualizarGestor(gestorParaEditar, gestor);
            }

            if (!salvou) {
                lblMsg.setText("E-mail ja cadastrado!");
                return;
            }

            modal.close();
            carregarGestores();
        });

        btnCancelar.setOnAction(e -> modal.close());

        Scene sceneModal = new Scene(grid, 500, 320);
        modal.setScene(sceneModal);
        modal.showAndWait();
    }

    private void mostrarModalMensagem(String titulo, String mensagem) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
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

    private void mostrarModalExclusao(Gestor gestor) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle("Confirmar exclusao");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label("Excluir o gestor \"" + gestor.getNome() + "\"?");
        msg.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnNao = new Button("Nao");

        btnSim.setOnAction(e -> {
            ArquivoGestor.excluirGestor(gestor);
            modal.close();
            carregarGestores();
        });

        btnNao.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnSim, btnNao);
        Scene scene = new Scene(layout, 500, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
