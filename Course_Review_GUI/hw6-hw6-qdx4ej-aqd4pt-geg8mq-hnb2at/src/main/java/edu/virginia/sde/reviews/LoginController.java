package edu.virginia.sde.reviews;

import javafx.scene.control.PasswordField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;


public class LoginController {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button closeButton;

    private DataBaseDriver dataBaseDriver = new DataBaseDriver("course_review_data.sqlite");

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();

        dataBaseDriver.createTables();

        Window owner = loginButton.getScene().getWindow();
        if (userNameField == null || userNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Please enter your username.");
            return;
        }
        if (passwordField == null || passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Please enter your password.");
            return;
        }
        if (!dataBaseDriver.userExists(userNameField.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Username does not exist.");
            return;
        }

        CourseReviewApplication.currentUser = dataBaseDriver.getUser(userNameField.getText());
        //User currentUser = dataBaseDriver.getUser(userNameField.getText());

        if(dataBaseDriver.isPasswordCorrect(CourseReviewApplication.currentUser, passwordField.getText()))
        {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful!",
                    "Welcome " + CourseReviewApplication.currentUser.getFirstName() + "!");
        }
        else
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Incorrect Password");
            return;
        }

        //If the username and password match we need to advanced to the next scene
        CourseReviewApplication.changeScene("course-search-page.fxml");

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
    }

    public void createNewAccountButtonAction(ActionEvent actionEvent) throws Exception
    {
        CourseReviewApplication.changeScene("create-account-page.fxml");
    }

    public void handleCloseButton (ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
