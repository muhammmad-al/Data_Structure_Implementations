package edu.virginia.sde.reviews;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.hibernate.annotations.processing.SQL;

import java.io.IOException;
import java.sql.*;


public class CreateAccountController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button backButton;

    private DataBaseDriver dataBaseDriver = new DataBaseDriver("course_review_data.sqlite");


    public void handleCreateAccountButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        Window owner = createAccountButton.getScene().getWindow();
        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        dataBaseDriver.createTables();


        //Actual code goes here 🤣
        String username = userNameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (userNameField == null || userNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Please complete all fields.");
            return;
        }
        if (passwordField == null || passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Please complete all fields.");
            return;
        }
        if (firstNameField == null || firstNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Please complete all fields.");
            return;
        }
        if (lastNameField == null || lastNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Please complete all fields.");
            return;
        }

        if(!dataBaseDriver.userExists(username)) {
            User user = new User(username, firstName, lastName, password);

            if(passwordField.getText().length() < 8)
            {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Your password must be at least 8 characters long.");
                return;
            }

            dataBaseDriver.addUser(user);
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Create Account Error", "Username Already Exists.");
        }


        /////////////////////////////////////////////

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Created Account Successfully", "Created Account Successfully");
        CourseReviewApplication.changeScene("login-page.fxml");
    }

    public void handleBackButtonAction (ActionEvent actionEvent) throws IOException {
        CourseReviewApplication.changeScene("login-page.fxml");
    }
}
