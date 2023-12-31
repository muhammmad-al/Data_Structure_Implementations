package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;



public class MyReviewsController {
    @FXML
    private static Stage stage;
    @FXML
    private Button backButton;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> commentColumn;
    @FXML
    private TableColumn<Course, Double> ratingColumn;

    private DataBaseDriver dataBaseDriver = new DataBaseDriver("course_review_data.sqlite");
    private List<Course> courses;

    public void initialize() throws SQLException {
        //initialize courses via database
        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        dataBaseDriver.createTables();
        courses = dataBaseDriver.getCourses();
        dataBaseDriver.commit();
        dataBaseDriver.disconnect();

        ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
        tableView.setItems(courseObservableList);

        // Initialize columns
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // Set cell factory for rating column (ensure rating is displayed with two decimal places)
        ratingColumn.setCellFactory(column -> new TableCell<Course, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        // Set up row factory for table view clicks as before (ensure you can click on courses)
        tableView.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Course clickedCourse = row.getItem();
                    try {
                        CourseReviewApplication.changeScene("course-review-page.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }
    public void handleBackButtonAction (ActionEvent actionEvent) throws IOException {
        CourseReviewApplication.changeScene("course-search-page.fxml");

    }
}
