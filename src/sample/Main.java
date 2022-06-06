package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        primaryStage.setTitle("Kerberos");
        primaryStage.getIcons().add(new Image("/logo_KERBEROS1.png"));
        primaryStage.setScene(MainController.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getMainStage() { return mainStage; }

    public static void main(String[] args) {
        launch(args);
    }
}