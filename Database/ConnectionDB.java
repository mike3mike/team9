package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDB {

    String connectionUrl = "jdbc:sqlserver://localhost;databaseName=CodeAcademy;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private Connection con;

    public ConnectionDB() {
        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Maak de verbinding met de database.
            con = DriverManager.getConnection(connectionUrl);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void execute(String SQL) throws SQLException {
        con.createStatement().execute(SQL);

    }

    public ResultSet getList(String SQL) throws SQLException {
        try {
            return con.createStatement().executeQuery(SQL);
        } catch (SQLException e) {

            System.out.println(e);
            return null;
        }
    }
}
