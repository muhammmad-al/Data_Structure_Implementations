package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyReviewsPageController {
    @FXML
    private Label curUserLabel;
    @FXML
    private TableView<ReviewView> reviewsTable;
    @FXML
    private ComboBox ratingSelection;
    @FXML
    private TextField commentField;
    @FXML
    private TableColumn<ReviewView, Integer> ratingColumn;
    @FXML
    private TableColumn<ReviewView, String> courseColumn;
    @FXML
    private TableColumn<ReviewView, String> commentColumn;
    private final DataBaseDriver dataBaseDriver = new DataBaseDriver("course_review_data.sqlite");
    private final User curUser = CourseReviewApplication.currentUser;
    public void initialize() throws SQLException {

        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        dataBaseDriver.createTables();
        curUserLabel.setText(String.format("Current User: %s", curUser.getUserName()));
        /////////////////////////////////
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        refreshReviewsDisplay();
        ////////////////////////////////

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
    }

    private void refreshReviewsDisplay() throws SQLException {
        List<Review> reviews = dataBaseDriver.getUserReviews(curUser); // Fetch reviews for the current course
        List<ReviewView> reviewViews = new ArrayList<>();
        for (Review review : reviews) {
            reviewViews.add(new ReviewView(review));
        }
        ObservableList<ReviewView> data = FXCollections.observableArrayList(reviewViews);
        reviewsTable.setItems(data);
    }

    public void handleLogOutAction (ActionEvent actionEvent) throws IOException, SQLException {
        CourseReviewApplication.changeScene("login-page.fxml");
        if(!dataBaseDriver.isConnected()){
            dataBaseDriver.connect();
        }
        dataBaseDriver.DefaultUser();
        dataBaseDriver.disconnect();
    }

    public void handleBackButtonAction (ActionEvent actionEvent) throws IOException {
        CourseReviewApplication.changeScene("course-search-page.fxml");

    }
}
