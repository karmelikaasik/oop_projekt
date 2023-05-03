module pro.projekt_graafiline {
    requires javafx.controls;
    requires javafx.fxml;


    opens pro.projekt_graafiline to javafx.fxml;
    exports pro.projekt_graafiline;
}