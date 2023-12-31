package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseSearchController {
    @FXML
    private Button logOutButton;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField titleField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addCourseButton;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
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
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
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
                    /////////////
                    CourseReviewApplication.currentCourse = row.getItem();
                    /////////////
                    try {
                        CourseReviewApplication.changeScene("course-review-page.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // Handle the double click event (e.g., open Course Review Screen)
                }
            });
            return row;
        });
        // Add event handlers for buttons
        searchButton.setOnAction(event -> handleSearch());
        addCourseButton.setOnAction(event -> { // i hate it here
            try {
                handleAddCourse();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Define methods like handleSearch(), handleAddCourse() here
    public void handleSearch() {
        String subjectSearch = subjectField.getText().trim().toUpperCase();
        String numberSearch = numberField.getText().trim();
        String titleSearch = titleField.getText().trim().toLowerCase();

        ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
        for (Course course : courses) {
            boolean subjectMatches = subjectSearch.isEmpty() || course.getSubject().equals(subjectSearch);
            boolean numberMatches = numberSearch.isEmpty() || Integer.toString(course.getNumber()).equals(numberSearch);
            boolean titleMatches = titleSearch.isEmpty() || course.getTitle().toLowerCase().contains(titleSearch);

            if (subjectMatches && numberMatches && titleMatches) {
                filteredCourses.add(course);
            }
        }

        tableView.setItems(filteredCourses);
    }

    public void handleAddCourse() throws SQLException {
        Stage stage = (Stage) tableView.getScene().getWindow(); // Gets the current stage for the alert owner

        if(!dataBaseDriver.isConnected())
            dataBaseDriver.connect();
        dataBaseDriver.createTables();

        String subject = subjectField.getText().trim().toUpperCase();
        String numberStr = numberField.getText().trim();
        String title = titleField.getText().trim();

        if (!subject.matches("[A-Z]{2,4}") || subject.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Invalid Subject", "Subject must be 2 to 4 letters.");
            return;
        }

        if (!numberStr.matches("\\d{4}")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Invalid Number", "Number must be exactly 4 digits.");
            return;
        }

        if (title.isEmpty() || title.length() > 50) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Invalid Title", "Title must be between 1 and 50 characters.");
            return;
        }

        int number = Integer.parseInt(numberStr);
        Course newCourse = new Course(subject, number, title, 0.0); // Assuming initial rating is 0.0
        if(dataBaseDriver.courseExists(newCourse))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Course Already Exists", "There is already a course with that mnemonic and number.");
            return;
        }
        courses.add(newCourse);
        tableView.getItems().add(newCourse);

        subjectField.clear();
        numberField.clear();
        titleField.clear();

        dataBaseDriver.addCourse(newCourse);

        dataBaseDriver.commit();
        dataBaseDriver.disconnect();
    }

    public void handleLogOutAction (ActionEvent actionEvent) throws IOException, SQLException {
        CourseReviewApplication.changeScene("login-page.fxml");
        if(!dataBaseDriver.isConnected()){
            dataBaseDriver.connect();
        }
        dataBaseDriver.DefaultUser();
        dataBaseDriver.disconnect();
    }
    public void handleMyReviewsAction (ActionEvent actionEvent) throws IOException, SQLException {
        CourseReviewApplication.changeScene("my-reviews.fxml");
    }
}