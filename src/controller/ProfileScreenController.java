package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Profile;

import java.io.IOException;
import java.sql.*;

/**
 * Controller for Profile Screen
 *
 * Created by Minju on 10/3/2016.
 */
public class ProfileScreenController {

    @FXML
    private Label titleField;

    @FXML
    private Label nameField;

    @FXML
    private Label userField;

    @FXML
    private Label emailField;

    @FXML
    private Label addressField;

    @FXML
    private Label accountTypeField;

    @FXML
    private JFXTextField changeTitle;

    @FXML
    private JFXTextField changeFirst;

    @FXML
    private JFXTextField changeLast;

    @FXML
    private JFXTextField changeEmail;

    @FXML
    private JFXTextField changeAddress;
    private Connection connection;

    /**
     * Automatically called to initialize the sreen
     */
    @FXML
    private void initialize() {
        String username = LoginScreenController.account.getUsername();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from accountsDB where username = '" + username + "'");
            if (rs.next()) {
                titleField.setText(rs.getString("title"));
                nameField.setText(rs.getString("firstName") + " " + rs.getString("lastName"));
                emailField.setText(rs.getString("email"));
                addressField.setText(rs.getString("address"));
                userField.setText(rs.getString("username"));
                accountTypeField.setText(rs.getString("accountType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

// --Commented out by Inspection START (11/16/16, 3:11 PM):
//    /**
//     * Updates the profile information
//     */
//    protected void save() {
//        Profile profile = LoginScreenController.account.getUser().getProfile();
//        userField.setText(LoginScreenController.account.getUsername());
//        accountTypeField.setText(LoginScreenController.account.getAccountType
//                ());
//        titleField.setText(profile.getTitle());
//        nameField.setText(profile.getFirstName() + " " + profile.getLastName());
//        emailField.setText(profile.getEmail());
//        addressField.setText(profile.getAddress());
//    }
// --Commented out by Inspection STOP (11/16/16, 3:11 PM)

    /**
     * Called when user clicks Edit and alters profile information
     */
    @FXML
    private void handleEditPressed() {
        String username = LoginScreenController.account.getUsername();
        Profile profile = LoginScreenController.account.getUser().getProfile();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
            Statement statement2 = connection.createStatement();
            statement2.setQueryTimeout(30);
            ResultSet rs = statement2.executeQuery("select * from accountsDB where username = '" + username + "'");
            if (changeTitle.getText().length() != 0) {
                titleField.setText(changeTitle.getText());
                profile.setTitle(changeTitle.getText());
                statement2.executeUpdate("update accountsDB set title = '" + changeTitle.getText() + "' where " +
                        "username" +
                        " = '" + username + "'");
            }
            if (changeFirst.getText().length() != 0) {
                nameField.setText(changeFirst.getText() + " " + profile.getLastName());
                profile.setFirstName(changeFirst.getText());
                statement2.executeUpdate("update accountsDB set firstName = '" + changeFirst.getText() + "' where " +
                        "username = '" + username + "'");
            }
            if (changeLast.getText().length() != 0) {
                nameField.setText(profile.getFirstName() + " " + changeLast.getText());
                profile.setLastName(changeLast.getText());
                statement2.executeUpdate("update accountsDB set lastName = '" + changeLast.getText() + "' where " +
                        "username = '" + username + "'");
            }
            if (changeEmail.getText().length() != 0) {
                emailField.setText(changeEmail.getText());
                profile.setEmail(changeEmail.getText());
                statement2.executeUpdate("update accountsDB set email = '" + changeEmail.getText() + "' where " +
                        "username = '" + username + "'");
            }
            if (changeAddress.getText().length() != 0) {
                addressField.setText(changeAddress.getText());
                profile.setAddress(changeAddress.getText());
                statement2.executeUpdate("update accountsDB set address = '" + changeAddress.getText() + "' where " +
                        "username = '" + username + "'");
            }
            if (rs.next()) {
                titleField.setText(rs.getString("title"));
                nameField.setText(rs.getString("firstName") + " " + rs.getString("lastName"));
                emailField.setText(rs.getString("email"));
                addressField.setText(rs.getString("address"));
                userField.setText(rs.getString("username"));
                accountTypeField.setText(rs.getString("accountType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    /**
     * Called when user clicks Cancel
     */
    @FXML
    private void handleCancelPressed() {
        Stage thisStage = (Stage) userField.getScene().getWindow();
        thisStage.close();
        thisStage.hide();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/LandingScreen.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Landing Screen");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
