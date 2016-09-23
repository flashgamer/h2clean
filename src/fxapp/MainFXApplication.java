package fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFXApplication extends Application {
private Stage mainScreen;

    /**
     * Called when program launched.
     * @param primaryStage The first stage to show up
     * @throws Exception because exceptions will happen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainScreen = primaryStage;
        welcomeScreen(mainScreen);
    }

    /**
     * Loads and shows the Welcome Screen as shown in the
     * corresponding Welcome Screen FXML file
     * @param mainScreen Stage to be shown.
     */
    private void welcomeScreen(Stage mainScreen) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            mainScreen.setTitle("h2clean");
            mainScreen.setScene(new Scene(root, 600, 400));
            mainScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called to launch the JavaFX application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
