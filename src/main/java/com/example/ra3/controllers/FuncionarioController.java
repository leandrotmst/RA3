package com.example.ra3.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FuncionarioController {
    private Stage stage;

    public FuncionarioController(Stage stage){
        this.stage = stage;
    }

    /**
     * Método responsável por criar os controles (componentes) para tratamento dos dados.
     *
     */
    public void mostrar(){
        // cria todos os componentes visuais,
        // cria a cena e mostra a janela
        // definições do gridpane
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(10,10,10,10 ));
        // Label para o nome
        Label l = new Label();
        l.setText("Label ");
        l.setFont(new Font("Arial",20));
        pane.add(l,0,0);
        // Botão
        Button btnSaveFuncionario = new Button();
        btnSaveFuncionario.setText("Gravar Funcionario");
        btnSaveFuncionario.setOnAction((event) -> handleBtnFuncionarioSaveOnClick(event));
        // Adiciona ao Pane na coluna 1, linha 0
        pane.add(btnSaveFuncionario,1,0);
        Scene cena = new Scene(pane, 1400,800);
        stage.setScene(cena);
        stage.show();
    }


    /**
     * Método para tratar os dados de pessoa
     * @param e
     */
    public void handleBtnFuncionarioSaveOnClick(ActionEvent e){
        System.out.println("handleBtnFuncionarioSave click");
    }
}
