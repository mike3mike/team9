package Domain;

import java.sql.ResultSet;
import java.util.ArrayList;

import Database.ConnectionDB;

public class CourseDomain {
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
        ConnectionDB con = new ConnectionDB();

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
