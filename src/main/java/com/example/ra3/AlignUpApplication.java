package com.example.ra3;

import com.example.ra3.controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class AlignUpApplication extends Application {

    // Método chamado ao iniciar a aplicação - recebe o Stage principal (a janela principal).
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cria um objeto MainController, passando o Stage principal.
        MainController mainController = new MainController(primaryStage);
        mainController.mostrar(); // Exibe a tela inicial com os botões dos módulos
    }

    //Método main padrão
    public static void main(String[] args) {
        launch(args);
    }
}