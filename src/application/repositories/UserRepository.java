package application.repositories;

import application.database.DBConnector;
import application.entities.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements GenericRepository<User> {

    static Connection conn = null;

    @Override
    public void insert(User user) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getRole().toString());

            statement.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(User user) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("UPDATE user SET password = ?, firstname = ?, lastname = ? WHERE username = ?");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUsername());

            statement.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(User user) {
        PreparedStatement statement = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("DELETE FROM user WHERE username = ?");
            statement.setString(1, user.getUsername());

            statement.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> buildListFromDB() {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<User>> handler = new BeanListHandler<User>(User.class);

        try {
            conn = DBConnector.getConnection();
            List<User> users = qr.query(conn, "SELECT * FROM USER", handler);

            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static User findByUsername(String username) {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<User> handler = new BeanHandler<>(User.class);

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);

            User user = qr.query(conn, "SELECT * FROM USER WHERE USERNAME = ?", handler, username);

            conn.commit();
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
