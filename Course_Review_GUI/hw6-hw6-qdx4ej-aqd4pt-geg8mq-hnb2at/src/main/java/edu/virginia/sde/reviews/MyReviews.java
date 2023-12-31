package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MyReviews extends Application {
    //mock data
    List<Course> courses = Arrays.asList(
            new Course("CS", 3140, "SDE", 4.50),
            new Course("CS", 2130, "Hell", 1.00),
            new Course("CS", 3100, "DSA", 3.00),
            new Course("APMA", 3080, "Linear Algebra", 5.00));

    private static final int ROWS_PER_PAGE = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        //UI layout for the course list
        TableView<Course> tableView = new TableView<>();
        ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
        tableView.setItems(courseObservableList);

        //add columns to TableView for subject, number, title, rating
        TableColumn<Course, String> subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        TableColumn<Course, Integer> numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<Course, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Course, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        //ensure course rating is displayed with two decimal places
        ratingColumn.setCellFactory(column -> new TableCell<Course, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    // Format the double to 2 decimal places
                    setText(String.format("%.2f", item));
                }
            }
        });
        tableView.getColumns().addAll(subjectColumn, numberColumn, titleColumn, ratingColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //ensure only 4 columns

        //if user selects course, navigate to course review screen
        tableView.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // Double click
                    Course clickedCourse = row.getItem();
                    System.out.println("Clicked on " + clickedCourse.getTitle());
                    // TODO: Open Course Review Screen for clickedCourse
                    try {
                        CourseReviewApplication.changeScene("course-review.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });

        //create additional UI components (search field, search/add courses buttons)
        TextField searchField = new TextField();
        searchField.setPromptText("Search Courses");

        Button searchButton = new Button("Search");
        Button addCourseButton = new Button("Add Course");
        //add event handlers for buttons

        //setup layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchField, searchButton, addCourseButton, tableView);

        tableView.setPrefHeight(400);

        //setup stage and scene
        Scene scene = new Scene(layout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("My Reviews Screen");
        stage.show();
    }
}
