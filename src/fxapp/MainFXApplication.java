package fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFXApplication extends Application {
private Stage mainScreen;
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainScreen = primaryStage;
        welcomeScreen(mainScreen);
    }

    private void welcomeScreen(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/WelcomeScreen.fxml"));
            Parent root = loader.load();
            ((controller.MainScreenController)loader.getController()).setPrimaryStage(mainScreen);
            mainScreen.setTitle("h2clean");
            mainScreen.setScene(new Scene(root, 600, 400));
            mainScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
