package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.SecurityLog;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author Vaishak Lalsangi
 */
public class AdminAccountScreenController {

    @FXML
    JFXButton deleteButton;

    @FXML
    JFXButton banButton;

    @FXML
    JFXButton unblockButton;

    @FXML
    JFXButton backButton;

    @FXML
    JFXButton securityButton;

    @FXML
    ListView<String> userList;

    private Connection connection;
    List<String> facingList = new LinkedList<>();

    @FXML
    private void initialize() {
        Map<String, Boolean> usernameList = new HashMap<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select username, canSubmit from accountsDB");
            while (rs.next()) {
                usernameList.put(rs.getString("username"), rs.getBoolean("canSubmit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (String s : usernameList.keySet()) {
            facingList.add(s + (usernameList.get(s) ? " Can Submit" : " Can't Submit"));
        }

        userList.setItems(FXCollections.observableList(facingList));
    }

    @FXML
    private void handleBanButton() {
        String user = userList.getSelectionModel().getSelectedItem();
        int userIndex = facingList.indexOf(user);
        if (user.equals("")) {
            return;
        } else {
            user = user.split(" ")[0];
            SecurityLog.recordUserBan(LoginScreenController.account.getUsername(), user);
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                String bool = "0";
                statement.executeUpdate("update accountsDB set canSubmit = " + bool + " where username = '" + user + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        facingList.remove(userIndex);
        facingList.add(user + " 0");
    }

    @FXML
    private void handleUnblockButton() {
        String user = userList.getSelectionModel().getSelectedItem();
        int userIndex = facingList.indexOf(user);
        if (user.equals("")) {
            return;
        } else {
            user = user.split(" ")[0];
            SecurityLog.recordUnbanAccount(LoginScreenController.account.getUsername(), user);
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                String bool = "1";
                statement.executeUpdate("update accountsDB set canSubmit = " + bool + " where username = '" + user + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        facingList.remove(userIndex);
        facingList.add(user + " 1");
    }

    @FXML
    private void handleDeleteButton() {
        String user = userList.getSelectionModel().getSelectedItem();
        int userIndex = facingList.indexOf(user);
        if (user.equals("")) {
            return;
        } else {
            user = user.split(" ")[0];
            SecurityLog.recordAccountDelete(LoginScreenController.account.getUsername(), user);
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:accountsDB.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                String bool = "1";
                statement.executeUpdate("delete from accountsDB where username = '"+ user + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        facingList.remove(userIndex);
    }

    @FXML
    private void handleSecurityLogButton() {
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
        thisStage.hide();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/SecurityLog.fxml"));
            Stage landingStage = new Stage();
            landingStage.setTitle("Security Log");
            landingStage.setScene(new Scene(root,600,400));
            landingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        Stage thisStage = (Stage) backButton.getScene().getWindow();
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
