module com.example.ra3 {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.ra3;
    exports com.example.ra3.controllers;
    exports com.example.ra3.controllers.gestor;
    exports com.example.ra3.controllers.analista;
    exports com.example.ra3.controllers.cargo;
    exports com.example.ra3.domains;
    exports com.example.ra3.domains.gestor;
    exports com.example.ra3.domains.analista;
    exports com.example.ra3.domains.cargo;
    exports com.example.ra3.domains.setor;
    exports com.example.ra3.persistence.gestor;
    exports com.example.ra3.persistence.analista;
    exports com.example.ra3.exceptions.gestor;
}