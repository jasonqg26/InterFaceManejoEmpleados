module com.lab8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens InterFace.lab8 to javafx.fxml;
    exports InterFace.lab8;
}