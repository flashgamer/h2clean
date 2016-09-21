import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainScreenController {
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        System.out.println("clicked");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root,600,400));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
