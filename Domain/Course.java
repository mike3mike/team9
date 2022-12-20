package Domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.ConnectionDB;

public class Course {
    private ConnectionDB con = new ConnectionDB();

    public ResultSet printCourses() throws SQLException {
        try {
            ResultSet rs = con.getList("SELECT * FROM Courses");

            return rs;

        } catch (SQLException e) {

        }
        return null;

    }

    public void addCourse(String name, String Subject, String description, String difficulty) {

        String SQL = "INSERT INTO Courses (name,subject,description,diffuculty)VALUES ('" + name + "','" + Subject
                + "','" + description + "','"
                + difficulty + "',)";
        con.execute(SQL);

    }

}
