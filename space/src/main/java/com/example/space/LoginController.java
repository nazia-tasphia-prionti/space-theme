package com.example.space;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button CancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView spaceImageView;

    @FXML
    private ImageView rocketImageView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField enterPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the first image
        try {
            Image spaceImage = new Image(getClass().getResource("/images/plesfitagain.jpg").toExternalForm());
            spaceImageView.setImage(spaceImage);
        } catch (Exception e) {
            System.out.println("Error loading spaceImageView image: " + e.getMessage());
        }

        // Load the second image
        try {
            Image rocketImage = new Image(getClass().getResource("/images/earth-cartoon-style.jpg").toExternalForm());
            rocketImageView.setImage(rocketImage);
        } catch (Exception e) {
            System.out.println("Error loading rocketImageView image: " + e.getMessage());
        }
    }

    public void loginButtonOnAction(ActionEvent event) {
        loginMessageLabel.setText("Trying to Login...");
        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Use a PreparedStatement to prevent SQL injection
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = ? AND password = ?";

        try {
            if (connectDB == null) {
                loginMessageLabel.setText("Database connection failed. Please try again.");
                return;
            }

            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, enterPasswordField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Login Successful!");
                } else {
                    loginMessageLabel.setText("Invalid Login. Please Try Again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            loginMessageLabel.setText("An error occurred. Please try again.");
        }
    }
}