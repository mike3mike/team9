package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionDB;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class CursistController extends Application {
    private ConnectionDB con = new ConnectionDB();
    private TableView Cursists = new TableView<>();

    private TableColumn<Cursist, String> nameColumn = new TableColumn<>("name");
    private TableColumn<Cursist, String> emailColumn = new TableColumn<>("email");
    private TableColumn<Cursist, String> DateofbirthColumn = new TableColumn<>("date of birth");
    private TableColumn<Cursist, String> genderColumn = new TableColumn<>("gender");
    private TableColumn<Cursist, String> cityColumn = new TableColumn<>("city");
    private TableColumn<Cursist, String> countryColumn = new TableColumn<>("country");
    private TableColumn<Cursist, String> addresColumn = new TableColumn<>("addres");

    public CursistController() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        DateofbirthColumn.setCellValueFactory(new PropertyValueFactory<>("DateofbirthColumn"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("genderColumn"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        addresColumn.setCellValueFactory(new PropertyValueFactory<>("addres"));
        Cursists.getColumns().addAll(nameColumn, emailColumn, DateofbirthColumn, genderColumn, cityColumn,
                countryColumn);

    }

    public Scene printCursists() {

        try {

            ResultSet rs = con.getList("SELECT * FROM Cursists");
            BorderPane layout = new BorderPane();
            Scene printCursists = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String Dateofbirth = rs.getString("Dateofbirth");
                String gender = rs.getString("gender");
                String addres = rs.getString("addres");
                String city = rs.getString("city");
                String country = rs.getString("country");

                int id = rs.getInt("ID");
                Cursists.getItems()
                        .add(new Cursist(name, email, Dateofbirth, gender, addres, city, country, id, null, null));
            }
            layout.setLeft(Cursists);

            Button add = new Button("add new Cursist");
            Button delete = new Button("delete");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(add, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addCursist());
                stage.show();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = Cursists.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                Cursists.getItems().remove(id);

                try {
                    deleteCursist(id.getId());
                } catch (SQLException e) {
                    buttons.getChildren().add(new Label(e.getLocalizedMessage()));
                }

            });

            layout.setBottom(buttons);
            return printCursists;

        } catch (SQLException e) {

        }
        return null;

    }

    public Scene addCursist() {

        GridPane layout = new GridPane();
        TextField NameInput = new TextField();
        TextField emailInput = new TextField();
        TextField DOBInput = new TextField();
        TextField genderInput = new TextField();
        TextField cityInput = new TextField();
        TextField countryInput = new TextField();
        TextField addresInput = new TextField();
        layout.add(new Label("Name"), 1, 1);
        layout.add(NameInput, 1, 2);
        layout.add(new Label("Email"), 2, 1);
        layout.add(emailInput, 2, 2);
        layout.add(new Label("Date of birth"), 3, 1);
        layout.add(DOBInput, 3, 2);
        layout.add(new Label("gender"), 4, 1);
        layout.add(genderInput, 4, 2);
        layout.add(new Label("City"), 5, 1);
        layout.add(cityInput, 5, 2);
        layout.add(new Label("Country"), 5, 1);
        layout.add(countryInput, 5, 2);
        layout.add(new Label("Addres"), 5, 1);
        layout.add(addresInput, 5, 2);

        Button button = new Button("verzenden");
        layout.add(button, 1, 5);
        button.setOnAction((eventHandler) -> {

            try {
                extracted(NameInput, emailInput, DOBInput, genderInput, cityInput, countryInput, addresInput);

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
    private void extracted(TextField NameInput, TextField emailInput, TextField DOBInput,
            TextField genderInput, TextField cityInput, TextField countryInput, TextField addresInput)
            throws SQLException {
        String name = NameInput.getText();
        String email = emailInput.getText();
        String DateOfBirth = DOBInput.getText();
        String gender = genderInput.getText();
        String country = countryInput.getText();
        String addres = addresInput.getText();
        String city = cityInput.getText();
        // adding course to database
        String SQL = "INSERT INTO Cursist (name,email,DateOfBirth,gender,country,addres,city)VALUES ('" + name + "','"
                + email
                + "','" + DateOfBirth + "','" + gender + "','" + country + "','" + addres + "','" + "','" + city + "')";
        con.execute(SQL);
        // adding course to table
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * FROM Cursist WHERE name = " + name + " AND difficulty = '" + email + "' ");
            while (rs.next()) {

                int id = rs.getInt("ID");
                Cursists.getItems()
                        .add(new Cursist(name, email, DateOfBirth, gender, addres, city, country, id, null, null));

            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

        NameInput.clear();
        emailInput.clear();
        DOBInput.clear();
        genderInput.clear();
        cityInput.clear();
        countryInput.clear();
        addresInput.clear();

    }

    private void deleteCursist(int id) throws SQLException {
        String SQL = "DELETE FROM Cursist WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printCursists());
        stage.show();
    }

}
