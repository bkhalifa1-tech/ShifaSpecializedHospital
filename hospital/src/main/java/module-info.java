module com.example.alshifaspecializedhospital {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.alshifaspecializedhospital to javafx.fxml;
    exports com.example.alshifaspecializedhospital;
}