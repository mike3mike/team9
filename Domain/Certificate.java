package Domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionDB;

public class Certificate {
    private ConnectionDB con = new ConnectionDB();

    public Certificate(int Cursistid, int courseID, String grade, String nameStaff, int id) {
        try {
            ResultSet sr = con.getList("Select * FROM Cursist WHERE id =" + Cursistid);
            while (sr.next()) {
                String name = sr.getString("naam");
                String email = sr.getString("email");
                String Dateofbirth = sr.getString("geboortedatum");
                String gender = sr.getString("geslacht");
                String addres = sr.getString("adres");
                String city = sr.getString("woonplaats");
                String country = sr.getString("land");
                int CursistId = sr.getInt("id");
                this.cursist = new Cursist(name, email, Dateofbirth, gender, addres, city, country, CursistId, null,
                        null);

            }
            sr = con.getList("SELECT * FROM cursus WHERE id =" + courseID);
            while (sr.next()) {
                String Course = sr.getString("naam");
                String Subject = sr.getString("onderwerp");
                String description = sr.getString("introductietekst");
                String diffuculty = sr.getString("niveau");
                int courseId = sr.getInt("id");
                this.course = new CourseDomain(Course, Subject, description, diffuculty, courseId);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.grade = grade;
        this.id = id;
        this.Cursistid = Cursistid;
        this.courseID = courseID;
        this.nameStaff = nameStaff;
    }

    private int Cursistid;
    private Cursist cursist;
    private CourseDomain course;
    private int courseID;
    private String grade;
    private String nameStaff;
    private int id;

    public int getCursistid() {
        return Cursistid;
    }

    public Cursist getCursist() {
        return cursist;
    }

    public CourseDomain getCourse() {
        return course;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getGrade() {
        return grade;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public int getId() {
        return id;
    }

}
