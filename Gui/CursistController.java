package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionDB;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Domain.CourseDomain;
import Domain.Cursist;
import validators.Validators;

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
    private TableColumn<Cursist, String> PostalCodeColumn = new TableColumn<>("postalcode");

    public CursistController() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        DateofbirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        addresColumn.setCellValueFactory(new PropertyValueFactory<>("addres"));
        PostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        Cursists.getColumns().addAll(nameColumn, emailColumn, DateofbirthColumn, genderColumn, cityColumn,
                countryColumn, PostalCodeColumn);

    }

    public Scene printCursists() {

        try {

            ResultSet rs = con.getList("SELECT * FROM Cursist");
            BorderPane layout = new BorderPane();
            Scene printCursists = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                String name = rs.getString("naam");
                String email = rs.getString("email");
                String Dateofbirth = rs.getString("geboortedatum");
                String gender = rs.getString("geslacht");
                String addres = rs.getString("adres");
                String postalcode = rs.getString("postcode");
                String city = rs.getString("woonplaats");
                String country = rs.getString("land");

                int id = rs.getInt("id");
                Cursists.getItems()
                        .add(new Cursist(name, email, Dateofbirth, gender, addres, postalcode, city, country, id, null,
                                null));
            }
            layout.setLeft(Cursists);

            Button add = new Button("voeg cursist toe");
            Button delete = new Button("verwijder");
            Button Back = new Button("ga terug");
            Button view = new Button("bekijk cursist");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(Back, add, view, delete);

            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addCursist());
                stage.show();

            });

            view.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = Cursists.getSelectionModel();
                ObservableList<Cursist> selectedItems = selectionModel.getSelectedItems();
                Cursist id = selectedItems.get(0);
                Stage stage = new Stage();
                stage.setScene(viewCursist(id));
                stage.show();
            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = Cursists.getSelectionModel();
                ObservableList<Cursist> selectedItems = selectionModel.getSelectedItems();
                Cursist id = selectedItems.get(0);
                Cursists.getItems().remove(id);

                try {
                    deleteCursist(id.getid());
                } catch (SQLException e) {
                    buttons.getChildren().add(new Label(e.getLocalizedMessage()));
                }

            });
            Back.setOnAction((Action) -> {
                Stage stage = new Stage();
                ApplicationController controller = new ApplicationController();
                try {
                    controller.start(stage);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Node node = (Node) Action.getSource();
                Stage thisStage = (Stage) node.getScene().getWindow();
                thisStage.close();
            });

            layout.setBottom(buttons);
            return printCursists;

        } catch (SQLException e) {

        }
        return null;

    }

    public Scene addCursist() {

        VBox layout = new VBox();
        TextField NameInput = new TextField();
        TextField emailInput = new TextField();
        HBox PublishDateBox = new HBox();
        TextField publishDay = new TextField();
        publishDay.setPromptText("Dag");
        TextField publishMonth = new TextField();
        publishMonth.setPromptText("maand");

        TextField publishYear = new TextField();
        publishYear.setPromptText("jaar");

        PublishDateBox.getChildren().addAll(publishDay, publishMonth, publishYear);
        TextField genderInput = new TextField();
        TextField cityInput = new TextField();
        TextField countryInput = new TextField();
        TextField addresInput = new TextField();
        TextField postalcodeInput = new TextField();
        layout.getChildren().add(new Label("naam"));
        layout.getChildren().add(NameInput);
        layout.getChildren().add(new Label("email"));
        layout.getChildren().add(emailInput);
        layout.getChildren().add(new Label("geboortedatum"));
        layout.getChildren().add(PublishDateBox);
        layout.getChildren().add(new Label("geslacht"));
        layout.getChildren().add(genderInput);
        layout.getChildren().add(new Label("woonplaats"));
        layout.getChildren().add(cityInput);
        layout.getChildren().add(new Label("land"));
        layout.getChildren().add(countryInput);
        layout.getChildren().add(new Label("Adres"));
        layout.getChildren().add(addresInput);
        layout.getChildren().add(new Label("postcode"));
        layout.getChildren().add(postalcodeInput);

        Button button = new Button("verzenden");
        layout.getChildren().add(button);
        button.setOnAction((eventHandler) -> {
            String date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();
            if (Validators.emailValid(emailInput.getText()) && (Validators.postcodeValid(postalcodeInput.getText()))) {

                try {
                    extracted(NameInput, emailInput, date, genderInput, cityInput, countryInput, addresInput,
                            postalcodeInput);
                    Node node = (Node) eventHandler.getSource();
                    Stage thisStage = (Stage) node.getScene().getWindow();
                    thisStage.close();
                } catch (SQLException e) {
                    layout.getChildren().add(new Label(e.getMessage()));

                }
            } else {
                layout.getChildren().add(new Label("velden kloppen niet"));

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
    private void extracted(TextField NameInput, TextField emailInput, String DOBInput,
            TextField genderInput, TextField cityInput, TextField countryInput, TextField addresInput,
            TextField postalcodeInput)
            throws SQLException {
        String name = NameInput.getText();
        String email = emailInput.getText();
        String DateOfBirth = DOBInput;
        String gender = genderInput.getText();
        String country = countryInput.getText();
        String addres = addresInput.getText();
        String postalcode = postalcodeInput.getText();
        String city = cityInput.getText();
        // adding course to database
        String SQL = "INSERT INTO Cursist (naam,email,geboortedatum,geslacht,land,adres,postcode,woonplaats)VALUES ('"
                + name
                + "','"
                + email
                + "','" + DateOfBirth + "','" + gender + "','" + country + "','" + addres + "','" + postalcode + "','"
                + city + "')";
        con.execute(SQL);
        // adding course to table
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * FROM Cursist WHERE naam = '" + name + "' AND email = '" + email + "'");
            while (rs.next()) {
                name = rs.getString("naam");
                email = rs.getString("email");
                DateOfBirth = rs.getString("geboortedatum");
                gender = rs.getString("geslacht");
                addres = rs.getString("adres");
                postalcode = rs.getString("postcode");

                String City = rs.getString("woonplaats");
                String Country = rs.getString("land");

                int id = rs.getInt("id");
                Cursists.getItems()
                        .add(new Cursist(name, email, DateOfBirth, gender, addres, postalcode, City, Country, id,
                                null, null));
            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

    }

    private Scene viewCursist(Cursist cursist) {

        BorderPane structure = new BorderPane();

        VBox layout = new VBox();

        TableView view = new TableView<>();
        view.getColumns().addAll(nameColumn, emailColumn, DateofbirthColumn, genderColumn, cityColumn,
                countryColumn);

        view.getItems().add(cursist);
        view.autosize();

        HBox treeviews = new HBox();
        TreeView modules = cursist.getEnrolledCourses();
        TreeView webcasts = cursist.viewedWebcasts();
        treeviews.getChildren().addAll(webcasts, modules);
        layout.getChildren().addAll(view, treeviews);
        structure.setCenter(layout);
        layout.setMargin(view, new Insets(5));

        return new Scene(structure, 400, 350);

    }

    private void deleteCursist(int id) throws SQLException {
        String SQL = "DELETE FROM Cursist WHERE id=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printCursists());
        stage.show();
    }

}
