package application;

import com.sun.jdi.connect.Connector;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static JdbcDataSource getDataSource() throws NamingException, ClassNotFoundException {
        JdbcDataSource ds = new JdbcDataSource();

        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");
        Properties props = new Properties();
        props.setProperty(Context.PROVIDER_URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1") ;
        Context ctx = new InitialContext();
        ctx.bind("jdbc/test", ds);
        return ds;
    }
}
