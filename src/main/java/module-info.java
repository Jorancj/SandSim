module com.example.sandsim {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sandsim to javafx.fxml;
    exports com.example.sandsim;
}