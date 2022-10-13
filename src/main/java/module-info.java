module com.example.sudokiu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudokiu to javafx.fxml;
    exports com.example.sudokiu;
}