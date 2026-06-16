package com.example.ra3.controllers;

import javafx.scene.Scene;
import javafx.scene.control.*;
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
        Button btnAbrirFuncionario = new Button("Funcionário");
        btnAbrirFuncionario.setOnAction(event -> {
            FuncionarioController funcionarioController = new FuncionarioController(this.stagePrincipal);
            funcionarioController.mostrar();
        });

        Button btnAbrirGestor = new Button("Gestor");
        btnAbrirGestor.setOnAction(event -> {
            GestorController gestorController = new GestorController(this.stagePrincipal);
            gestorController.mostrar();
        });

        Button btnAbrirSetor = new Button("Setor");
        btnAbrirSetor.setOnAction(event -> {
            SetorController setorController = new SetorController(this.stagePrincipal);
            setorController.mostrar();
        });

        Button btnAbrirCargo = new Button("Cargo");
        btnAbrirCargo.setOnAction(event -> {
            CargoController cargoController = new CargoController(this.stagePrincipal);
            cargoController.mostrar();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(btnAbrirFuncionario, btnAbrirGestor, btnAbrirSetor, btnAbrirCargo);

        this.cena = new Scene(layout, 300, 200);
    }

}