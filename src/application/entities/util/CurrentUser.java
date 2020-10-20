package application.entities.util;

import application.entities.User;

public class CurrentUser {

    private User user;

    public CurrentUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
