package Domain;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import Database.ConnectionDB;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Cursist {
    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String addres;
    private String postalcode;
    private String city;
    private String country;
    private int id;
    private ArrayList<Certificate> diplomas = new ArrayList<>();
    private ArrayList<CourseDomain> enrolledCourses = new ArrayList<>();
    private ArrayList<CourseDomain> Webcasts = new ArrayList<>();
    private ConnectionDB con = new ConnectionDB();

    /* this is the constructor */
    public Cursist(String name, String email, String dateOfBirth, String gender, String addres, String Postalcode,
            String city,
            String country, int id, ArrayList diplomas, ArrayList<CourseDomain> enrolledCourses) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addres = addres;
        this.postalcode = postalcode;

        this.city = city;
        this.country = country;
        this.diplomas = diplomas;
        this.id = id;
        try {
            ResultSet rs = con.getList("SELECT * FROM inschrijving WHERE cursistID=" + id);
            while (rs.next()) {
                int courseid = rs.getInt("cursusID");

                rs = con.getList("SELECT * FROM cursus WHERE id=" + courseid);
                while (rs.next()) {
                    String coursename = rs.getString("naam");
                    String courseSubject = rs.getString("onderwerp");
                    String coursedescription = rs.getString("introductietekst");
                    String coursediffuculty = rs.getString("niveau");
                    this.enrolledCourses.add(
                            new CourseDomain(coursename, courseSubject, coursedescription, coursediffuculty, courseid));

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(enrolledCourses);

    }
    /* getters and setters here */

    // public String courseProgress() {

    // }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddres() {
        return addres;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getid() {
        return id;
    }

    public ArrayList getDiplomas() {
        return diplomas;
    }

    public ArrayList<CourseDomain> getEnrolledCourses() {

        return enrolledCourses;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public int getId() {
        return id;
    }

    public ConnectionDB getCon() {
        return con;
    }

}