module com.example.alignupyumi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.alignupyumi to javafx.fxml;
    exports com.example.alignupyumi;
}