package Domain;

import java.sql.ResultSet;
import java.util.ArrayList;

import Database.ConnectionDB;

public class CourseDomain {
    private ConnectionDB con = new ConnectionDB();

    private String name;
    private String subject;
    private String description;
    private String difficulty;
    private int id;
    private ArrayList<Module> modules;

    public CourseDomain(String name, String subject, String description, String difficulty, int id) {
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.difficulty = difficulty;
        this.id = id;
        this.modules = new ArrayList<Module>();

        try {

            ResultSet rs = con.getList(
                    "SELECT * from module INNER JOIN contentItem on contentItemid = contentItem.id WHERE cursusID ="
                            + id);
            // this while loop adds courses to the array
            while (rs.next()) {
                String title = rs.getString("titel");
                String moduleDescription = rs.getString("beschrijving");
                String publishDate = rs.getString("publicatiedatum");
                String status = rs.getString("status");
                String version = rs.getString("versie");
                String contact = rs.getString("naamContactpersoon");
                String contactEmail = rs.getString("email");
                int moduleId = rs.getInt("id");
                int ContentItemID = rs.getInt("contentItemid");

                modules.add(new Domain.Module(ContentItemID, moduleId, publishDate, status, title, moduleDescription,
                        version,
                        contact,
                        contactEmail));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<CourseDomain> getCourses() {
        ArrayList<CourseDomain> courses = new ArrayList<>();

        try {
            ResultSet rs = con.getList("SELECT * FROM Courses");
            while (rs.next()) {
                String name = rs.getString("name");
                String Subject = rs.getString("subject");
                String description = rs.getString("description");
                String diffuculty = rs.getString("difficulty");

                int id = rs.getInt("ID");
                courses.add(new CourseDomain(name, Subject, description, diffuculty, id));
            }
            return courses;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public String toString() {
        return name + " " + difficulty;
    }

}
