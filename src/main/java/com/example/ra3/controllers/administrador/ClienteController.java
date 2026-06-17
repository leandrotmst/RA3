package com.example.ra3.controllers.administrador;

import com.example.ra3.domains.administrador.Administrador;
import com.example.ra3.domains.administrador.Cliente;
import com.example.ra3.persistence.administrador.ArquivoCliente;
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

public class ClienteController {

    private Stage stage;
    private Administrador administrador;
    private Scene cenaListagem;
    private ObservableList<Cliente> clientesObservable;

    public ClienteController(Stage stage, Administrador administrador) {
        this.stage = stage;
        this.administrador = administrador;
        criarTelaListagem();
    }

    public void mostrar() {
        carregarClientes();
        stage.setScene(cenaListagem);
        stage.setTitle("Clientes");
        stage.show();
    }

    private void carregarClientes() {
        ArrayList<Cliente> clientes = ArquivoCliente.lerLista();
        clientesObservable.setAll(clientes);
    }

    private void criarTelaListagem() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Clientes");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Cliente> tabela = new TableView<>();
        clientesObservable = FXCollections.observableArrayList();
        tabela.setItems(clientesObservable);

        TableColumn<Cliente, String> colCnpj = new TableColumn<>("CNPJ");
        colCnpj.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCnpj()));
        colCnpj.setPrefWidth(180);

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colNome.setPrefWidth(250);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colEmail.setPrefWidth(280);

        tabela.getColumns().addAll(colCnpj, colNome, colEmail);

        Button btnNovo = new Button("Novo Cliente");
        Button btnEditar = new Button("Editar Selecionado");
        Button btnExcluir = new Button("Excluir Selecionado");
        Button btnVoltar = new Button("Voltar");

        btnNovo.setOnAction(e -> mostrarFormulario(null));

        btnEditar.setOnAction(e -> {
            Cliente selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                mostrarFormulario(selecionado);
            } else {
                mostrarModalMensagem("Aviso", "Selecione um cliente para editar.");
            }
        });

        btnExcluir.setOnAction(e -> {
            Cliente selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                mostrarModalExclusao(selecionado);
            } else {
                mostrarModalMensagem("Aviso", "Selecione um cliente para excluir.");
            }
        });

        btnVoltar.setOnAction(e -> {
            MainAdministradorController main = new MainAdministradorController(stage, administrador);
            main.mostrar();
        });

        layout.getChildren().addAll(titulo, tabela, btnNovo, btnEditar, btnExcluir, btnVoltar);
        this.cenaListagem = new Scene(layout, 1300, 700);
    }

    private void mostrarFormulario(Cliente clienteParaEditar) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label titulo = new Label(clienteParaEditar == null ? "Novo Cliente" : "Editar Cliente");
        titulo.setStyle("-fx-font-size: 16px;");
        grid.add(titulo, 0, 0, 2, 1);

        grid.add(new Label("CNPJ:"), 0, 1);
        TextField txtCnpj = new TextField();
        grid.add(txtCnpj, 1, 1);

        grid.add(new Label("Nome:"), 0, 2);
        TextField txtNome = new TextField();
        grid.add(txtNome, 1, 2);

        grid.add(new Label("E-mail:"), 0, 3);
        TextField txtEmail = new TextField();
        grid.add(txtEmail, 1, 3);

        grid.add(new Label("Senha:"), 0, 4);
        PasswordField txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 4);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        Label lblMsg = new Label();
        grid.add(btnSalvar, 0, 5);
        grid.add(btnCancelar, 1, 5);
        grid.add(lblMsg, 0, 6, 2, 1);

        if (clienteParaEditar != null) {
            txtCnpj.setText(clienteParaEditar.getCnpj());
            txtNome.setText(clienteParaEditar.getNome());
            txtEmail.setText(clienteParaEditar.getEmail());
            txtSenha.setText(clienteParaEditar.getSenha());
        }

        btnSalvar.setOnAction(e -> {
            String cnpj = txtCnpj.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            if (cnpj.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                lblMsg.setText("Preencha todos os campos!");
                return;
            }

            Cliente cliente = new Cliente(cnpj, nome, email, senha);
            boolean salvou;
            if (clienteParaEditar == null) {
                salvou = ArquivoCliente.adicionarCliente(cliente);
            } else {
                salvou = ArquivoCliente.atualizarCliente(clienteParaEditar, cliente);
            }

            if (!salvou) {
                lblMsg.setText("CNPJ ou e-mail ja cadastrado!");
                return;
            }

            modal.close();
            carregarClientes();
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

    private void mostrarModalExclusao(Cliente cliente) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle("Confirmar exclusao");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label msg = new Label("Excluir o cliente \"" + cliente.getNome() + "\"?");
        msg.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnNao = new Button("Nao");

        btnSim.setOnAction(e -> {
            ArquivoCliente.excluirCliente(cliente);
            modal.close();
            carregarClientes();
        });

        btnNao.setOnAction(e -> modal.close());

        layout.getChildren().addAll(msg, btnSim, btnNao);
        Scene scene = new Scene(layout, 500, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
