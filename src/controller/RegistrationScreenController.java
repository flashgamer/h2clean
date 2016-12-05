package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the registration screen.
 * @author Hotline String
 */
public class RegistrationScreenController {
    @FXML
    private JFXTextField userField;

    @FXML
    private JFXPasswordField passField;

    @FXML
    private JFXTextField titleField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField addressField;

    @FXML
    private JFXComboBox<String> typeBox;
    private Connection connection;

    @FXML
    private void initialize() {
        List<String> comboBoxList = new ArrayList<>();
        comboBoxList.add("User");
        comboBoxList.add("Worker");
        comboBoxList.add("Manager");
        comboBoxList.add("Administrator");
        typeBox.setItems(FXCollections.observableArrayList(comboBoxList));
        typeBox.setValue("User");
    }
    /**
     * Controls what happens when the user clicks on the register button.
     */
    @FXML
    private void handleRegisterButtonAction() {
        if (isInputValid()) {
            Account account = new Account(userField.getText(), passField.getText(), typeBox.getValue(), titleField.getText(), firstNameField.getText(), lastNameField.getText(), emailField.getText(), addressField.getText());
            // Facade.getInstance().addAccount(account);
            // saveAccountJson();
            // System.out.println(database.containsKey(userField.getText()));
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                statement.executeUpdate("create table if not exists accountsDB (id integer, username string, password" +
                        " string, " + "accountType string, title string, firstName string, lastName string, email " +
                        "string, address string, canSubmit boolean)");
                String sqlStatement = "insert into accountsDB (username, password, accountType, title, " +
                        "firstName, lastName, email, address, canSubmit) values('" + userField.getText() +
                        "', '" + passField.getText() + "', '" + typeBox.getValue() + "', '" + titleField.getText() + "', '" + firstNameField
                        .getText() + "', '" + lastNameField.getText() + "', '" + emailField.getText() + "', '" +
                        addressField.getText() + "', 1)";
                statement.executeUpdate(sqlStatement);
//                String fileName = userField.getText() + ".ser";
//                FileOutputStream fileOut =
//                        new FileOutputStream(fileName);
//                ObjectOutputStream out = new ObjectOutputStream(fileOut);
//                out.writeObject(account);
//                out.close();
//                fileOut.close();
            }catch(SQLException e) {
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
    }

    /**
     * Controls what happens when the user clicks on the cancel button.
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
     * Checks that all inputs on the screen are valid.
     * @return true if all inputs are valid.
     */
    @FXML
    private boolean isInputValid() {
        //loadJson();
        String errorMessage = "";

        // Checks if username field has been filled in
        if (userField.getText() == null || userField.getText().length() == 0) {
            errorMessage += "Username cannot be empty!\n";
        }        // Checks if password field has been filled in
        if (passField.getText() == null || passField.getText().length() == 0) {
            errorMessage += "Password cannot be empty!\n";
        }
        // Checks if username has already been taken
        if ((userField.getText() != null || userField.getText().length() != 0)) {
            Account a = null;
//            String username = userField.getText() + ".ser";
            boolean userNameExists = false;
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from accountsDB where username = '" + userField.getText
                        () + "'");
                while (rs.next()) {
                    if (rs.getString("username").equals(userField.getText())) {
                        userNameExists = true;
                    }
                }
//                FileInputStream fileIn = new FileInputStream(username);
//                ObjectInputStream in = new ObjectInputStream(fileIn);
//                a = (Account) in.readObject();
//                in.close();
//                fileIn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
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
            if (userNameExists){ errorMessage += "That username has already been taken!\n"; }
        }

        //successful login
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please try again with the correct login details.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

//    private void saveAccountJson() {
//        Facade.getInstance().saveAccountJson();
//    }
//    private void loadJson() {
//        Facade.getInstance().loadAccountJson();
//    }
}
