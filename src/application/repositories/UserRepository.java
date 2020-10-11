package application.repositories;

import application.database.DBConnector;
import application.entities.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {

    static Connection conn = null;

    public static List<User> buildListFromDB() {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<User>> handler = new BeanListHandler<User>(User.class);

        try {
            conn = DBConnector.getConnection();
            List<User> users = qr.query(conn, "SELECT * FROM users", handler);

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


}
