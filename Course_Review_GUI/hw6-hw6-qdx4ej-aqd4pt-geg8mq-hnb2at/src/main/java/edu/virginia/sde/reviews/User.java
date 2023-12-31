package edu.virginia.sde.reviews;

public class User {
    private String userName, firstName, lastName, password;

    public User(String userName, String firstName, String lastName, String password){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){return true;}
        if (o == null || getClass() != o.getClass()){return false;}

        User user = (User) o;
        return userName.equals(user.getUserName());
    }

    @Override
    public String toString() {
        return "User: " + userName
                + ", "+firstName
                + " " + lastName;
    }
}
