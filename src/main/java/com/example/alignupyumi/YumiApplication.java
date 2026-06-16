package com.example.alignupyumi;

import com.example.alignupyumi.controllers.LoginController;
import com.example.alignupyumi.controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class YumiApplication extends Application {

    // Método chamado ao iniciar a aplicação - recebe o Stage principal (a janela principal).

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Cria um objeto LoginController, passando o Stage principal.
        LoginController loginController = new LoginController(primaryStage);
        loginController.mostrar();      // Exibe a tela de login.
    }

    // Método main padrão
    public static void main(String[] args) {
        launch(args);
    }
}
