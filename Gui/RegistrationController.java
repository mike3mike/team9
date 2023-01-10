package Gui;

import javafx.application.Application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.ConnectionDB;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Domain.CourseDomain;
import Domain.Cursist;
import Domain.Registration;
import Domain.CourseDomain;

public class RegistrationController extends Application {
    private ConnectionDB con = new ConnectionDB();
    private TableView registrations = new TableView<>();

    private TableColumn<Registration, String> CursistColumn = new TableColumn<>("Cursist");
    private TableColumn<Registration, String> CourseColumn = new TableColumn<>("Course");
    private TableColumn<Registration, String> RegistrationColumn = new TableColumn<>("RegistrationDate");
    private ArrayList<CourseDomain> courses = new ArrayList<>();
    private ArrayList<Cursist> cursists = new ArrayList<>();

    public RegistrationController() {
        CursistColumn.setCellValueFactory(new PropertyValueFactory<>("Cursist"));
        CourseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
        RegistrationColumn.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));

        registrations.getColumns().addAll(CursistColumn, CourseColumn, RegistrationColumn);

    }

    public Scene printRegistration() {

        try {

            ResultSet rs = con.getList("SELECT * FROM Registration");
            BorderPane layout = new BorderPane();
            Scene printregistrations = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                int nameID = rs.getInt("Cursist");
                int courseID = rs.getInt("Course");
                ResultSet sr = con.getList("Select * FROM Cursist WHERE ID = " + nameID);
                String name = rs.getString("name");
                String email = rs.getString("email");
                String Dateofbirth = rs.getString("Dateofbirth");
                String gender = rs.getString("gender");
                String addres = rs.getString("addres");
                String city = rs.getString("city");
                String country = rs.getString("country");
                sr = con.getList("SELECT * FROM course WHERE ID=" + courseID);
                String Course = sr.getString("name");
                String Subject = rs.getString("subject");
                String description = rs.getString("description");
                String diffuculty = rs.getString("difficulty");
                String RegistrationDate = rs.getString("RegistrationDate");
                int id = rs.getInt("ID");
                CourseDomain course = new CourseDomain(name, Subject, description, RegistrationDate, id);
                Cursist cursist = new Cursist(name, email, Dateofbirth, gender, addres, city, country, id, null, null);
                registrations.getItems()
                        .add(new Registration(cursist, course, RegistrationDate, id));
                courses.add(course);
                cursists.add(cursist);
            }
            layout.setLeft(registrations);

            Button add = new Button("add new Cursist");
            Button delete = new Button("delete");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(add, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addregistrations());
                stage.show();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = registrations.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                registrations.getItems().remove(id);

                try {
                    deleteregistrations(id.getId());
                } catch (SQLException e) {
                    buttons.getChildren().add(new Label(e.getLocalizedMessage()));
                }

            });

            layout.setBottom(buttons);
            return printregistrations;

        } catch (SQLException e) {

        }
        return null;

    }

    public Scene addregistrations() {

        GridPane layout = new GridPane();
        ChoiceBox<CourseDomain> choiceBoxCourses = new ChoiceBox();
        for (int i = 0; courses.size() > i; i++) {
            choiceBoxCourses.getItems().add(courses.get(i));
        }
        ChoiceBox<Cursist> choiceBoxCursists = new ChoiceBox();
        for (int i = 0; cursists.size() > i; i++) {
            choiceBoxCursists.getItems().add(cursists.get(i));
        }
        TextField registrationDateInput = new TextField();
        layout.add(new Label("Course"), 1, 1);
        layout.add(choiceBoxCourses, 1, 2);
        layout.add(new Label("Cursist"), 2, 1);
        layout.add(choiceBoxCursists, 2, 2);
        layout.add(new Label("Registration Date"), 3, 1);
        layout.add(registrationDateInput, 3, 2);

        Button button = new Button("verzenden");
        layout.add(button, 1, 5);
        button.setOnAction((eventHandler) -> {

            try {
                extracted(choiceBoxCourses, choiceBoxCursists, registrationDateInput);

            } catch (SQLException e) {
                layout.add(new Label(e.getMessage()), 2, 5);

            }
        });
        Scene addCourse = new Scene(layout);
        return addCourse;
    }

    // this method is a submit method for the addCourse method. It excecutes an
    // sql query to add the course to the database and it retrieves back the course
    // from the database so it can be added to the table
    // (the course is retrieved from the database because the id gets
    // autoincremented in the databases)
    private void extracted(ChoiceBox choiceBoxCourses, ChoiceBox choiceBoxCursists, TextField registrationDateInput)
            throws SQLException {
        String date = registrationDateInput.getText();
        CourseDomain course = (CourseDomain) choiceBoxCourses.getValue();
        Cursist cursist = (Cursist) choiceBoxCursists.getValue();
        int CourseID = course.getId();
        int curistID = cursist.getID();

        // adding course to database
        String SQL = "INSERT INTO Registration (course,curist,registrationDate)VALUES ('" + CourseID + "','"
                + curistID
                + "','" + date + "')";
        con.execute(SQL);
        // adding course to table
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * FROM Registration WHERE course = " + CourseID + " AND curist = '" + curistID
                                    + "' ");
            while (rs.next()) {

                int id = rs.getInt("ID");
                registrations.getItems()
                        .add(new Registration(cursist, course, date, id));

            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

        registrationDateInput.clear();

    }

    private void deleteregistrations(int id) throws SQLException {
        String SQL = "DELETE FROM registration WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printRegistration());
        stage.show();
    }

}
