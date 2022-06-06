package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {

    @FXML
    private Button start, instructions;


    @FXML
    void openInstructions() {
        String name = "instructions.fxml";
        Scene previousScene = Main.getMainStage().getScene();
        Scene scene = MainController.getSceneWin(name);
        Main.getMainStage().setScene(scene);
        scene.setOnKeyPressed(event -> { if(event.getCode().equals(KeyCode.ESCAPE)) Main.getMainStage().setScene(previousScene); });
    }
    @FXML
    void openNextWin() {
        Scene previousScene = Main.getMainStage().getScene();
        Scene scene = Controller.getScene();
        Main.getMainStage().setScene(scene);
        scene.setOnKeyPressed(event -> { if(event.getCode().equals(KeyCode.ESCAPE)) Main.getMainStage().setScene(previousScene); });
    }
    public static Scene getScene() {
        Parent root = loadFXML();
        assert root != null;
        return new Scene(root);
    }

    private static Parent loadFXML() {
        try {
            return FXMLLoader.load(MainController.class.getResource("sample.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new Pane();
        }
    }

    public static Scene getSceneWin(String name) {
        Parent root = loadFXMLWin(name);
        assert root != null;
        return new Scene(root);
    }
    private static Parent loadFXMLWin(String name) {
        try {
            return FXMLLoader.load(MainController.class.getResource(name));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new Pane();
        }
    }
}