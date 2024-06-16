package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CourseReviewApplication extends Application{
    private static Stage stage;

    public static User currentUser;
    public static Course currentCourse;
    public static void main(String[] args){launch(args);}

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("login-page.fxml"))));
        stage = primaryStage;
        Scene loginScene = new Scene(root);
        primaryStage.setTitle("Course Review");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    //Method came from Stack Overflow
    //https://stackoverflow.com/questions/37200845/how-to-switch-scenes-in-javafx
    public static void changeScene(String fxml) throws IOException {
        try {
            Parent root = FXMLLoader.load((Objects.requireNonNull(CourseReviewApplication.class.getResource(fxml))));;
            stage.setScene(new Scene(root));
        }catch (IOException e){
            throw new IOException("Changing scenes error"); //i did not write this correctly
        }
    }
    public static void setStageTitle(String newTitle) {
        stage.setTitle(newTitle);
    }
}