package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CourseReviewController {
    @FXML
    private Label courseInfoLabel;
    @FXML
    private Label averageRatingLabel;
    @FXML
    private TableView<ReviewView> reviewsTable;
    @FXML
    private ComboBox ratingSelection;
    @FXML
    private TextField commentField;
    @FXML
    private TableColumn<ReviewView, Integer> ratingColumn;
    @FXML
    private TableColumn<ReviewView, String> timestampColumn;
    @FXML
    private TableColumn<ReviewView, String> commentColumn;

    private final User curUser = CourseReviewApplication.currentUser;
    private final DataBaseDriver dataBaseDriver = new DataBaseDriver("course_review_data.sqlite");
    private final Course curCourse = CourseReviewApplication.currentCourse;

    public CourseReviewController() {}

    public void initialize() throws SQLException {

        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        dataBaseDriver.createTables();
        List<Review> reviews = dataBaseDriver.getAllReviews();
        // reviews to reviewViews?
        List<ReviewView> reviewViews = new ArrayList<>();
        for(Review review : reviews)
            reviewViews.add(new ReviewView(review));

        courseInfoLabel.setText(String.format("Current Course | %s %d: %s", curCourse.getSubject(), curCourse.getNumber(), curCourse.getTitle()));
        averageRatingLabel.setText(String.format("Average Rating: %.2f", curCourse.getRating()));

        ////////////////////////////
        ObservableList<ReviewView> data = FXCollections.observableArrayList(reviewViews);

        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

//        tableView.setItems(data);
        refreshReviewsDisplay();

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
    }

    @FXML
    private void handleSubmitReview() throws SQLException {
        if (!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        Window owner = commentField.getScene().getWindow();

        String rating = (String) ratingSelection.getValue();
        if(rating.equals("Rating"))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Review Error", "You must choose a rating to review.");
            return;
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String comment = commentField.getText();

        Review review = new Review(Integer.parseInt(rating), timestamp, comment, curCourse);

        if(dataBaseDriver.hasReviewed(curUser, curCourse))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Review Error", "You can only leave one review.");
            return;
        }
        try {
            dataBaseDriver.addReview(review, curUser, curCourse);
            dataBaseDriver.commit();
            refreshReviewsDisplay(); // Refresh the TableView with the new data
        } catch (SQLException e) {
            dataBaseDriver.rollback();
            throw e;
        } finally {
            dataBaseDriver.disconnect();
        }
    }

    @FXML
    private void handleDeleteReview() throws SQLException
    {
        if (!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        Window owner = commentField.getScene().getWindow();

        if(!dataBaseDriver.hasReviewed(curUser, curCourse))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Review Deletion Error", "You don't have a review to delete.");
            return;
        }

        dataBaseDriver.deleteReview(curUser, curCourse);
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Deletion Successful", "Your review has been deleted.");
        refreshReviewsDisplay();

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
    }

    public void handleBackButtonAction (ActionEvent actionEvent) throws IOException {
        CourseReviewApplication.changeScene("course-search-page.fxml");
    }

    private void refreshReviewsDisplay() throws SQLException {
        List<Review> reviews = dataBaseDriver.getReviewsForCourse(curCourse); // Fetch reviews for the current course
        List<ReviewView> reviewViews = new ArrayList<>();
        for (Review review : reviews) {
            reviewViews.add(new ReviewView(review));
        }
        ObservableList<ReviewView> data = FXCollections.observableArrayList(reviewViews);
        reviewsTable.setItems(data);
    }
}