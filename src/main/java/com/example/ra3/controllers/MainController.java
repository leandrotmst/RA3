package com.example.ra3.controllers;

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

public class MainController {
    private Stage stagePrincipal;
    private Scene cena;

    public MainController(Stage stagePrincipal){
        this.stagePrincipal = stagePrincipal;
        this.criarComponentes();
    }

    public void mostrar(){
        this.stagePrincipal.setScene(this.cena);
        stagePrincipal.show();
    }

    /**
     * Cria os componentes da cena.
     */
    private void criarComponentes(){
        Button btnAbrir = new Button("Funcionário");
        btnAbrir.setOnAction(event -> {
            FuncionarioController funcionarioController = new FuncionarioController(this.stagePrincipal);
            funcionarioController.mostrar();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll( btnAbrir);
        // cria a cena e atribui ao atributo
        this.cena = new Scene(layout, 300, 200);
    }

}