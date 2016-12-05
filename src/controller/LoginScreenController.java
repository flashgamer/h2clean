package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Account;
import model.SecurityLog;

import java.io.IOException;
import java.sql.*;

/**
 * Controller for user login
 * Some code reused from CS2340 Resources
 *
 * @author mkwon41
 */
public class LoginScreenController {
    /*
        References to the FXML widgets in .fxml files
     */
    @FXML
    private JFXTextField userField;

    @FXML
    private JFXPasswordField passField;

    private Stage _loginStage;
    private Connection connection;

    // --Commented out by Inspection (11/16/16, 3:07 PM):private String userKey;
    public static String userType;
    public static Account account; // Account that is logged in for reporting purposes.
    static int reportNum = 1;

    /**
     * called automatically after load
     */
    @FXML
    private void initialize() {

    }

// --Commented out by Inspection START (11/16/16, 3:08 PM):
//    /**
//     * Sets the stage of the dialog.
//     *
//     * @param loginStage the stage of the login
//     */
//    public void setLoginStage(Stage loginStage) { _loginStage = loginStage; }
// --Commented out by Inspection STOP (11/16/16, 3:08 PM)



    /**
     * Called when user clicks Login
     */
    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        // loadJson();
        if (isInputValid()) {
            Stage thisStage = (Stage) userField.getScene().getWindow();
            thisStage.close();
            thisStage.hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LandingScreen.fxml"));
                Parent root = fxmlLoader.load();
                Stage landingStage = new Stage();
                //LandingScreenController lsc = fxmlLoader.<LandingScreenController>getController();
                landingStage.setTitle("Landing Screen");
                landingStage.setScene(new Scene(root,600,400));
                landingStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Called when user clicks Cancel
     */
    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) userField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeScreen.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Welcome Screen");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks if username and password fields have been filled in
     * and then checks if the username and password are in the database.
     *
     * @return true if input is valid
     */
    private boolean isInputValid() {
        // loadJson();
        String errorMessage = "";
        boolean userMatch = false;
        //boolean passMatch = false;
        userType = null;
        Account a = null;
        // Checks if username field has been filled in
        if (userField.getText() == null || userField.getText().length() == 0) {
            errorMessage += "Username cannot be empty!\n";
        }
        // Checks if password field has been filled in
        if (passField.getText() == null || passField.getText().length() == 0) {
            errorMessage += "Password cannot be empty!\n";
        }
        // Checks if username is in database
        if (userField.getText().length() != 0) {
//            String fileName = userField.getText() + ".ser";
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from accountsDB where username = '" + userField
                        .getText() + "'");
                if (!rs.next()) {
                    errorMessage += "That username doesn't exist!\n";
                    SecurityLog.recordLogin(userField.getText(), SecurityLog.Status.UNKNOWN_ID);
                } else {
                    userMatch = true;
                    userType = rs.getString("accountType");
                }
//                FileInputStream fileIn = new FileInputStream(fileName);
//                ObjectInputStream in = new ObjectInputStream(fileIn);
//                a = (Account) in.readObject();
//                in.close();
//                fileIn.close();
            }catch(SQLException e) {
                e.printStackTrace();
//            }catch(ClassNotFoundException c) {
//                System.out.println("Deserialized class not found");
//                c.printStackTrace();
//                return false;
            }
            finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    // connection close failed.
                    System.err.println(e);
                }
            }
        }
        // Checks if password matches to the user.
        if ((passField.getText() != null || passField.getText().length() != 0)
                && userMatch) {
//            String fileName = userField.getText() + ".ser";
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from accountsDB where username = '" + userField
                        .getText() + "'");
                if (!rs.getString("password").equals(passField.getText())) {
                    errorMessage += "The password is invalid!\n";
                    SecurityLog.recordLogin(userField.getText(), SecurityLog.Status.BAD_PASSWORD);
                } else {
                    a = new Account(rs.getString("username"), rs.getString("password"), rs.getString("accountType"),
                            rs.getString("title"), rs.getString("firstName"), rs.getString("lastName"), rs.getString
                            ("email"), rs.getString("address"));
                }
//                FileInputStream fileIn = new FileInputStream(fileName);
//                ObjectInputStream in = new ObjectInputStream(fileIn);
//                a = (Account) in.readObject();
//                in.close();
//                fileIn.close();
            }catch(SQLException e) {
                e.printStackTrace();
//            }catch(ClassNotFoundException c) {
//                System.out.println("Deserialized class not found");
//                c.printStackTrace();
//                return false;
            }
            finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    // connection close failed.
                    System.err.println(e);
                }
            }
//            if (!passField.getText().equals(a.getPassword())) { errorMessage += "The password is invalid!\n"; }
        }


        //successful login
        if (errorMessage.length() == 0) {
            account = a;
            SecurityLog.recordLogin(userField.getText(), SecurityLog.Status.SUCCESS);
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(_loginStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

//    private void loadJson() {
//        Facade.getInstance().loadAccountJson();
//    }
}
