package Domain;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class Cursist {
    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String addres;
    private String city;
    private String country;
    private ArrayList diplomas;
    private HashMap enrolledCourses;

    /* this is the constructor */
    public Cursist(String name, String email, String dateOfBirth, String gender, String addres, String city,
            String country, ArrayList diplomas, HashMap enrolledCourses) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addres = addres;
        this.city = city;
        this.country = country;
        this.diplomas = diplomas;
        this.enrolledCourses = enrolledCourses;


    }
    /*getters and setters here */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(ArrayList diplomas) {
        this.diplomas = diplomas;
    }

    public HashMap getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(HashMap enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    /*getters and setters end */
    /* test json */

    public String 

}