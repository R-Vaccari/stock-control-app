package application.repositories;

import application.database.DBConnector;

import application.entities.User;
import application.entities.enums.Role;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {

    static Connection conn = null;

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
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);


            statement = conn.prepareStatement("SELECT FROM USER WHERE USERNAME = ?");
            statement.setString(1, username);

            rs = statement.executeQuery();

            User retrievedUser = new User(username, rs.getString("PASSWORD"),
                    rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                    Role.valueOf(rs.getString("USER_ROLE")));


            return retrievedUser;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
