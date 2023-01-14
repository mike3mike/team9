package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.DocFlavor.STRING;
import javax.sound.sampled.SourceDataLine;

import Database.ConnectionDB;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModuleController extends Application {
    private ConnectionDB con = new ConnectionDB();
    private TableView Modules = new TableView<>();

    private TableColumn<Module, String> titleColumn = new TableColumn<>("title ");
    private TableColumn<Module, String> descriptionColumn = new TableColumn<>("description");
    private TableColumn<Module, String> statusColumn = new TableColumn<>("status");
    private TableColumn<Module, String> publishDateColumn = new TableColumn<>("publishDate");
    private TableColumn<Module, String> courseColumn = new TableColumn<>("course");
    private TableColumn<Module, String> versionColumn = new TableColumn<>("version");
    private TableColumn<Module, String> contactColumn = new TableColumn<>("contact");
    private TableColumn<Module, String> contactEmailColumn = new TableColumn<>("contactEmail");

    public ModuleController() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        publishDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        contactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));

        Modules.getColumns().addAll(titleColumn, descriptionColumn, statusColumn, publishDateColumn, courseColumn,
                versionColumn, contactColumn, contactEmailColumn);

    }

    public Scene printModules() {

        try {

            ResultSet rs = con.getList("SELECT * from module INNER JOIN contentItem on contentItemid = contentItem.id");
            BorderPane layout = new BorderPane();
            Scene printModules = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                String title = rs.getString("titel");
                String description = rs.getString("beschrijving");
                String publishDate = rs.getString("publicatiedatum");
                String status = rs.getString("status");
                String version = rs.getString("versie");
                String contact = rs.getString("naamContactpersoon");
                String contactEmail = rs.getString("email");
                int id = rs.getInt("id");
                System.out.println(id);
                int ContentItemID = rs.getInt("contentItemid");

                Modules.getItems()
                        .add(new Domain.Module(ContentItemID, id, publishDate, status, title, description, version,
                                contact,
                                contactEmail));
            }
            layout.setLeft(Modules);

            Button add = new Button("add new Cursis");
            Button delete = new Button("delete");
            Button Back = new Button("go back");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(Back, add, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addModule());
                stage.show();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel<Domain.Module> selectionModel = Modules.getSelectionModel();
                ObservableList<Domain.Module> selectedItems = selectionModel.getSelectedItems();
                Domain.Module id = selectedItems.get(0);
                Modules.getItems().remove(id);

                try {
                    deleteModule(id.getModuleId());
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
            return printModules;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;

    }

    public Scene addModule() {

        VBox layout = new VBox();
        TextField titleInput = new TextField();
        TextField descriptionInput = new TextField();
        ChoiceBox statusChoiceBox = new ChoiceBox();
        statusChoiceBox.getItems().add("concept");
        statusChoiceBox.getItems().add("actief");
        statusChoiceBox.getItems().add("gearchiveerd");
        HBox PublishDateBox = new HBox();
        TextField publishDay = new TextField();
        publishDay.setPromptText("Dag");
        TextField publishMonth = new TextField();
        publishMonth.setPromptText("maand");

        TextField publishYear = new TextField();
        publishYear.setPromptText("jaar");

        PublishDateBox.getChildren().addAll(publishDay, publishMonth, publishYear);
        TextField versionInput = new TextField();
        TextField contactInput = new TextField();
        TextField contactEmail = new TextField();
        layout.getChildren().add(new Label("titel"));
        layout.getChildren().add(titleInput);
        layout.getChildren().add(new Label("beschrijving"));
        layout.getChildren().add(descriptionInput);
        layout.getChildren().add(new Label("status"));
        layout.getChildren().add(statusChoiceBox);
        layout.getChildren().add(new Label("publicatie datum"));
        layout.getChildren().add(PublishDateBox);
        layout.getChildren().add(new Label("versie"));
        layout.getChildren().add(versionInput);
        layout.getChildren().add(new Label("contactpersoon"));
        layout.getChildren().add(contactInput);
        layout.getChildren().add(new Label("email van contactpersoon"));
        layout.getChildren().add(contactEmail);

        Button button = new Button("verzenden");
        layout.getChildren().add(button);
        button.setOnAction((eventHandler) -> {

            String date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();

            try {
                extracted(titleInput, descriptionInput, statusChoiceBox, date, versionInput, contactInput,
                        contactEmail);
                // Node node = (Node) eventHandler.getSource();
                // Stage thisStage = (Stage) node.getScene().getWindow();
                // thisStage.close();
            } catch (SQLException e) {
                System.out.println(e);
                layout.getChildren().add(new Label(e.getLocalizedMessage()));

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
    private void extracted(TextField titleInput, TextField descriptionInput, ChoiceBox statusInput,
            String publishDateInput, TextField versionInput, TextField contactInput, TextField contactEmailInput)
            throws SQLException {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String status = (String) statusInput.getValue();
        String publishDate = publishDateInput;
        String version = versionInput.getText();
        String contact = contactInput.getText();
        String contactEmail = contactEmailInput.getText();
        // adding course to database
        String SQL = "INSERT INTO contentItem (titel,beschrijving,status,publicatiedatum)VALUES ('" + title
                + "','"
                + description
                + "','" + status + "','" + publishDate + "')";
        con.execute(SQL);
        try {
            ResultSet rs = con.getList(
                    "Select id FROM contentItem WHERE titel ='" + title + "' AND beschrijving = '" + description + "'");
            while (rs.next()) {
                int Contentid = rs.getInt("id");
                SQL = "INSERT INTO module (versie,contentItemid,naamContactpersoon,email)VALUES ('" + version
                        + "','" + Contentid + "','"
                        + contact
                        + "','" + contactEmail + "')";
                con.execute(SQL);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // adding course to table
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * from module INNER JOIN contentItem on contentItemid = contentItem.id WHERE titel ='"
                                    + title + "' AND beschrijving = '" + description + "'");
            while (rs.next()) {
                title = rs.getString("titel");
                description = rs.getString("beschrijving");
                publishDate = rs.getString("publicatiedatum");
                status = rs.getString("status");
                version = rs.getString("versie");
                contact = rs.getString("naamContactpersoon");
                contactEmail = rs.getString("email");
                int id = rs.getInt("id");
                int ContentItemID = rs.getInt("contentItemid");

                Modules.getItems()
                        .add(new Domain.Module(ContentItemID, id, publishDate, status, title, description, version,
                                contact,
                                contactEmail));
            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

    }

    private void deleteModule(int id) throws SQLException {
        String SQL = "DELETE FROM module WHERE id=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printModules());
        stage.show();
    }

}
