package application.entities;


import application.entities.enums.Role;

public class User {

    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;

    public User() { }

    public User(String password, String username, String firstName, String lastName, Role role) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
