package edu.virginia.sde.reviews;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.AttributedCharacterIterator;
import java.util.*;
public class DataBaseDriver {
    private final String sqliteFilename;
    private Connection connection;

    public final String USERS_TABLE = "Users",
    USERNAME = "Username",
    FIRST_NAME = "FirstName",
    LAST_NAME = "LastName",
    PASSWORD = "Password";

    final String createUsers = """
            CREATE TABLE IF NOT EXISTS Users(
            Username            TEXT PRIMARY KEY NOT NULL,
            Firstname           TEXT not null,
            Lastname            TEXT not null,
            Password            TEXT not null
            );
            """;
    final String createCourses= """
            CREATE TABLE IF NOT EXISTS Courses(
            CourseID            INTEGER PRIMARY KEY,
            Title               TEXT NOT NULL,
            CourseNum           REAL not null,
            Subject             TEXT not null,
            Rating              REAL not null
            );
            """;
    final String createReviews = """
            CREATE TABLE IF NOT EXISTS Reviews(
            ReviewID            INTEGER PRIMARY KEY,
            Rating              INTEGER NOT NULL,
            Comment             TEXT,
            Timestamp           TEXT NOT NULL,
            Username            TEXT NOT NULL,
            CourseID            INTEGER NOT NULL,
            FOREIGN KEY (Username)
                REFERENCES Users (Username)
                ON DELETE CASCADE,
            FOREIGN KEY (CourseID)
                REFERENCES Courses (CourseID)
                ON DELETE CASCADE
            );
            """;

    public DataBaseDriver(String sqlListDatabaseFilename) {
        this.sqliteFilename = sqlListDatabaseFilename;
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("The connection is already opened");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteFilename);
        //the next line enables foreign key enforcement - do not delete/comment out
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        //the next line disables auto-commit - do not delete/comment out
        connection.setAutoCommit(false);
    }

    public boolean isConnected() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void createTables() throws SQLException{
        if(connection.isClosed()){
            throw new IllegalStateException("Connection not stable, just like me.");
        }
        var createTables = List.of(createUsers, createCourses, createReviews);
        for(var query : createTables){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }

    public void addUsers(List<User> users) throws SQLException{
        try{
            for(User user : users){
                PreparedStatement statement = connection.prepareStatement(
                        """
                                INSERT INTO Users(Username, Firstname, Lastname, Password) values
                                    (?, ?, ?, ?)
                                """
                );
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getLastName());
                statement.setString(4, user.getPassword());
                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e){
            rollback();
            throw e;
        }
    }
    public void addUser(User user) throws SQLException{
        try{
            PreparedStatement statement = connection.prepareStatement(
                    """
                            INSERT INTO Users(Username, Firstname, Lastname, Password) values
                                (?, ?, ?, ?)
                            """
            );
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e){
            rollback();
            throw e;
        }
    }
    private User getUser(ResultSet resultSet) throws SQLException{
        var userName = resultSet.getString(USERNAME);
        var firstName = resultSet.getString(FIRST_NAME);
        var lastName = resultSet.getString(LAST_NAME);
        var password = resultSet.getString(PASSWORD);

        return new User(userName, firstName,lastName,password);
    }
    public User getUser(String userName) throws SQLException {
        if (connection.isClosed()){
            throw new IllegalStateException("Connection is closed.");
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        var user = getUser(resultSet);
        return user;
    }

    public boolean isPasswordCorrect(User user, String input) throws SQLException
    {
        if(connection.isClosed())
            throw new IllegalStateException("Connection is closed.");
        PreparedStatement statement = connection.prepareStatement("SELECT Password FROM Users WHERE Username = ?");
        statement.setString(1, user.getUserName());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString(1).equals(input);
    }

    public void DefaultUser() throws SQLException {
        if(userExists("DefaultUser")) {
            CourseReviewApplication.currentUser = getUser("DefaultUser");
        }
        else{
            User defaultUser = new User("DefaultUser", "Nan", "Nan", "Nan");
            CourseReviewApplication.currentUser = defaultUser;
        }
    }

    public List<User> getAllUsers() throws SQLException{
        if (connection.isClosed()){
            throw new IllegalStateException("Connection is closed.");
        }
        PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM Users
                """);
        var users = new ArrayList<User>();

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            var user = getUser(resultSet);
            users.add(user);
        }
        return users;
    }

    public boolean userExists(String username) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE USERNAME = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public void addCourse(Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                """
                        INSERT INTO Courses (Title, CourseNum, Subject, Rating)
                        VALUES (?, ?, ?, ?)
                    """
        );
        statement.setString(1, course.getTitle());
        statement.setInt(2, course.getNumber());
        statement.setString(3, course.getSubject());
        statement.setDouble(4, course.getRating());

        statement.executeUpdate();
        statement.close();
    }

    public List<Course> getCourses() throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                """
                        SELECT * FROM Courses;
                    """
        );
        List<Course> courses = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            String subject = resultSet.getString("Subject");
            int number = resultSet.getInt("CourseNum");
            String title = resultSet.getString("Title");
            double rating = resultSet.getDouble("Rating");

            Course course = new Course(subject, number, title, rating);
            double averageRating = getAverageRating(course);
            course = new Course(subject, number, title, averageRating);
            courses.add(course);
        }
        return courses;
    }

    public int getCourseID(Course course) throws SQLException
    {
        int id = -1;
        PreparedStatement statement = connection.prepareStatement("SELECT CourseID FROM Courses WHERE CourseNum = ? AND Subject = ?");
        statement.setInt(1, course.getNumber());
        statement.setString(2, course.getSubject());

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next())
            id = resultSet.getInt(1);
        return id;
    }

    public void addReview(Review review, User user, Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(
                """
                        INSERT INTO Reviews (Rating, Comment, Timestamp, Username, CourseID)
                        VALUES (?, ?, ?, ?, ?)
                    """
        );
        statement.setInt(1, review.getRating());
        statement.setString(2, review.getComment());
        statement.setString(3, review.getTimestamp().toString());
        statement.setString(4, user.getUserName());
        statement.setInt(5, getCourseID(course));
        statement.executeUpdate();
    }

    public List<Review> getAllReviews() throws SQLException
    {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            int rating = resultSet.getInt("Rating");
            String timestamp = resultSet.getString("Timestamp");
            String comment = resultSet.getString("comment");
            Course course = getCourseFromID(resultSet.getInt("CourseID"));

            reviews.add(new Review(rating, Timestamp.valueOf(timestamp), comment, course));
        }
        return reviews;
    }

    public Course getCourseFromID(int id) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Courses where CourseID = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String subject = resultSet.getString("Subject");
        int number = resultSet.getInt("CourseNum");
        String title = resultSet.getString("Title");
        Double rating = resultSet.getDouble("Rating");
        return new Course(subject, number, title, rating);
    }

    public boolean courseExists(Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Courses WHERE CourseNum = ? AND Subject = ?");
        statement.setInt(1, course.getNumber());
        statement.setString(2, course.getSubject());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean hasReviewed(User user, Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE Username = ? AND CourseID = ?");
        statement.setString(1, user.getUserName());
        statement.setInt(2, getCourseID(course));
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public List<Review> getUserReviews(User user) throws SQLException
    {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE Username = ?");
        statement.setString(1, user.getUserName());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            int courseID = resultSet.getInt("CourseID");
            int rating = resultSet.getInt("Rating");
            String comment = resultSet.getString("Comment");
            String timestamp = resultSet.getString("Timestamp");
            Review review = new Review(rating, Timestamp.valueOf(timestamp), comment, getCourseFromID(courseID));
            reviews.add(review);
        }
        return reviews;
    }

    public List<Review> getReviewsForCourse(Course course) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE CourseID = ?");
        statement.setInt(1, getCourseID(course));
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            int rating = resultSet.getInt("Rating");
            String timestamp = resultSet.getString("Timestamp");
            String comment = resultSet.getString("comment");
            reviews.add(new Review(rating, Timestamp.valueOf(timestamp), comment, course));
        }
        return reviews;
    }

    public void deleteReview(User user, Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Reviews WHERE Username = ? AND CourseID = ?");
        statement.setString(1, user.getUserName());
        statement.setInt(2, getCourseID(course));
        statement.executeUpdate();
    }

    public double getAverageRating(Course course) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE CourseID = ?");
        statement.setInt(1, getCourseID(course));
        ResultSet resultSet = statement.executeQuery();
        int sum = 0;
        int count = 0;
        while(resultSet.next())
        {
            sum += resultSet.getInt("Rating");
            count++;
        }
        if(count == 0)
            return 0;
        return (double) sum /count;
    }
}
