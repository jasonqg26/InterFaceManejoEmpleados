package lab8;

import javafx.application.Application;
import javafx.stage.Stage;



public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        InterFace interFace = new InterFace(15);

        // Configurar y mostrar la ventana principal
        primaryStage.setTitle("Gestión de Trabajadores");
        primaryStage.setScene(interFace.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}