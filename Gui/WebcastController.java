package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import Components.Validators;
import Database.ConnectionDB;
import Domain.Webcast;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WebcastController extends Application {
    private ConnectionDB con = new ConnectionDB();
    private TableView Webcasts = new TableView<>();

    private TableColumn<Webcast, String> titleColumn = new TableColumn<>("title ");
    private TableColumn<Webcast, String> descriptionColumn = new TableColumn<>("description");
    private TableColumn<Webcast, String> statusColumn = new TableColumn<>("status");
    private TableColumn<Webcast, String> publishDateColumn = new TableColumn<>("publishDate");
    private TableColumn<Webcast, String> speakerColumn = new TableColumn<>("Speaker");
    private TableColumn<Webcast, String> organisationColumn = new TableColumn<>("Organisation");
    private TableColumn<Webcast, String> URLColumn = new TableColumn<>("URL");

    public WebcastController() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        publishDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
        speakerColumn.setCellValueFactory(new PropertyValueFactory<>("Speaker"));
        organisationColumn.setCellValueFactory(new PropertyValueFactory<>("organisation"));
        URLColumn.setCellValueFactory(new PropertyValueFactory<>("URL"));

        Webcasts.getColumns().addAll(titleColumn, descriptionColumn, statusColumn, publishDateColumn, speakerColumn,organisationColumn,
                URLColumn);

    }

    public Scene printModules() {

        try {

            ResultSet rs = con
                    .getList("SELECT * from webcast INNER JOIN contentItem on contentItemid = contentItem.id");
            BorderPane layout = new BorderPane();
            Scene printWebcasts = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                String title = rs.getString("titel");
                String description = rs.getString("beschrijving");
                String publishDate = rs.getString("publicatiedatum");
                String status = rs.getString("status");
                String URL = rs.getString("URL");
                String namespeaker = rs.getString("naamspreker");
                String organisation = rs.getString("organisatieWerk");
                int id = rs.getInt("id");
                System.out.println(id);
                int ContentItemID = rs.getInt("contentItemid");

                Webcasts.getItems()
                        .add(new Domain.Webcast(ContentItemID, id, publishDate, status, title, description, URL,
                                namespeaker,
                                organisation));
            }
            layout.setLeft(Webcasts);

            Button add = new Button("add new Webcast");
            Button edit = new Button("edit");

            Button delete = new Button("delete");
            Button Back = new Button("go back");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(Back, add,edit, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addWebcast());
                stage.show();

            });
            edit.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = Webcasts.getSelectionModel();
                ObservableList<Webcast> selectedItems = selectionModel.getSelectedItems();
                Webcast id = selectedItems.get(0);
                // courses.getItems().remove(id);
                Stage stage = new Stage();
                editWebcast(id);
                stage.setScene(editWebcast(id));
                stage.show();
                Webcasts.refresh();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel<Domain.Webcast> selectionModel = Webcasts.getSelectionModel();
                ObservableList<Domain.Webcast> selectedItems = selectionModel.getSelectedItems();
                Domain.Webcast id = selectedItems.get(0);
                Webcasts.getItems().remove(id);

                try {
                    deleteWebcast(id.getWebcastId());
                } catch (SQLException e) {
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
            return printWebcasts;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;

    }

    public Scene addWebcast() {

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
        TextField speakerNameInput = new TextField();
        TextField organisationNameInput = new TextField();
        TextField URLInput = new TextField();
        layout.getChildren().add(new Label("titel"));
        layout.getChildren().add(titleInput);
        layout.getChildren().add(new Label("beschrijving"));
        layout.getChildren().add(descriptionInput);
        layout.getChildren().add(new Label("status"));
        layout.getChildren().add(statusChoiceBox);
        layout.getChildren().add(new Label("publicatie datum"));
        layout.getChildren().add(PublishDateBox);
        layout.getChildren().add(new Label("URL"));
        layout.getChildren().add(URLInput);
        layout.getChildren().add(new Label("organisatie Werk"));
        layout.getChildren().add(organisationNameInput);
        layout.getChildren().add(new Label("naam spreker"));
        layout.getChildren().add(speakerNameInput);

        Button button = new Button("verzenden");
        layout.getChildren().add(button);
        button.setOnAction((eventHandler) -> {

            String date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();

            try {
                extracted(titleInput, descriptionInput, statusChoiceBox, date, speakerNameInput, organisationNameInput,
                        URLInput);
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
            String publishDateInput, TextField speakerNameInput, TextField organisationNameInput, TextField URLInput)
            throws SQLException {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String status = (String) statusInput.getValue();
        String publishDate = publishDateInput;
        String speakerName = speakerNameInput.getText();
        String organisationName = organisationNameInput.getText();
        String URL = URLInput.getText();
        if (Validators.URLValid(URL) && Validators.dateValid(publishDate)) {
            // adding course to database
            String SQL = "INSERT INTO contentItem (titel,beschrijving,status,publicatiedatum)VALUES ('" + title
            + "','"
            + description
            + "','" + status + "','" + publishDate + "')";
            try {
             
            con.execute(SQL);
            System.out.println("succes");
            } catch (SQLException e) {
System.out.println(e);            }
            
            try {
                ResultSet rs = con.getList(
                        "Select id FROM contentItem WHERE titel ='" + title + "' AND beschrijving = '" + description
                                + "'");
                while (rs.next()) {
                    int Contentid = rs.getInt("id");
                    SQL = "INSERT INTO webcast (naamspreker,contentItemid,organisatieWerk,URL)VALUES ('" + speakerName
                            + "','" + Contentid + "','"
                            + organisationName
                            + "','" + URL + "')";
                    con.execute(SQL);

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // adding course to table
            try {
                ResultSet rs = con
                        .getList(
                                "SELECT * from webcast INNER JOIN contentItem on contentItemid = contentItem.id WHERE URL ='"
                                        + URL + "' ");
                while (rs.next()) {
                    title = rs.getString("titel");
                    description = rs.getString("beschrijving");
                    publishDate = rs.getString("publicatiedatum");
                    status = rs.getString("status");
                    URL = rs.getString("URL");
                    organisationName = rs.getString("organisatieWerk");
                    speakerName = rs.getString("naamSpreker");
                    int id = rs.getInt("id");
                    int ContentItemID = rs.getInt("contentItemid");

                    Webcasts.getItems()
                            .add(new Webcast(ContentItemID, id, publishDate, status, title, description, URL,
                                    speakerName,
                                    organisationName));
                }
            } catch (SQLException e) {
                System.out.println(e);
                // TODO: handle exception
            }
        }
        else{
            System.out.println("velden niet goed!");
        }
    }

    private void deleteWebcast(int id) throws SQLException {
        String SQL = "DELETE FROM webcast WHERE contentItemid=" + id + ";";
        con.execute(SQL);
        SQL = "DELETE FROM contentItem WHERE id= "+id+"";

    }
    public Scene editWebcast(Webcast webcast) {
        try {

            VBox layout = new VBox();
            int id = webcast.getID();

            ResultSet rs = con.getList("SELECT * FROM Webcast INNER JOIN contentItem ON contentItem.id = Webcast.ContentItemid where ID = " + id );

            while (rs.next()) {
                TextField titleInput = new TextField(rs.getString("titel"));
                TextField descriptionInput = new TextField(rs.getString("beschrijving"));
                ChoiceBox statusChoiceBox = new ChoiceBox();

                statusChoiceBox.getItems().add("concept");
                statusChoiceBox.getItems().add("actief");
                statusChoiceBox.getItems().add("gearchiveerd");
                statusChoiceBox.getSelectionModel().select(rs.getString("status"));
                HBox PublishDateBox = new HBox();
                String[] date = rs.getString("publicatiedatum").split("-");
                TextField publishDay = new TextField(date[0]);
                publishDay.setPromptText("Dag");
                TextField publishMonth = new TextField(date[1]);
                publishMonth.setPromptText("maand");
        
                TextField publishYear = new TextField(date[2]);
                publishYear.setPromptText("jaar");
        
                PublishDateBox.getChildren().addAll(publishDay, publishMonth, publishYear);
                TextField speakerNameInput = new TextField(rs.getString("naamSpreker"));
                TextField organisationNameInput = new TextField(rs.getString("organisatieWerk"));
                TextField URLInput = new TextField(rs.getString("URL"));
                layout.getChildren().add(new Label("titel"));
                layout.getChildren().add(titleInput);
                layout.getChildren().add(new Label("beschrijving"));
                layout.getChildren().add(descriptionInput);
                layout.getChildren().add(new Label("status"));
                layout.getChildren().add(statusChoiceBox);
                layout.getChildren().add(new Label("publicatie datum"));
                layout.getChildren().add(PublishDateBox);
                layout.getChildren().add(new Label("URL"));
                layout.getChildren().add(URLInput);
                layout.getChildren().add(new Label("organisatie Werk"));
                layout.getChildren().add(organisationNameInput);
                layout.getChildren().add(new Label("naam spreker"));
                layout.getChildren().add(speakerNameInput);
        
                Button button = new Button("verzenden");
                Text Error = new Text();
                layout.getChildren().add(Error);
                layout.getChildren().add(button);
                String Date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();

                button.setOnAction((eventHandler) -> {
                    if(Validators.dateValid(Date)){
                        if(Validators.URLValid(URLInput.getText())){
                        try {
                            editButton(titleInput, descriptionInput, statusChoiceBox, Date,URLInput ,id,organisationNameInput,speakerNameInput,webcast );
                            layout.getChildren().add(new Label("Cursus is gewijzigd"));
    
                        } catch (SQLException e) {
                            layout.getChildren().add(new Label(e.getMessage()));
    
                        }
                       

                    }
                    else{
                            Error.setText("URL is not valid");
                    }
                    }
                    else{
                        Error.setText("Date is not valid");
                    }
                 
                });
            }

            Scene printCourses = new Scene(layout);

            return printCourses;

        } catch (SQLException e) {

        }
        return null;

    }
    private void editButton(TextField titleInput,TextField descriptionInput, ChoiceBox statusChoiceBox, String Date, TextField URLInput ,int id, TextField organisationNameInput, TextField speakerNameInput, Webcast webcast) throws SQLException {
String name = titleInput.getText();
String status = (String) statusChoiceBox.getSelectionModel().getSelectedItem();
String description = descriptionInput.getText();
String url = URLInput.getText();
String organisationName = organisationNameInput.getText();
String speakerName = speakerNameInput.getText();
String SQL = "UPDATE webcast SET naamspreker = '"+name+"',organisatieWerk= '"+organisationName +"', URL ='"+url+"'WHERE contentItemid=" + id + ";";
con.execute(SQL);
 SQL = "UPDATE contentItem SET titel = '"+ name+"',beschrijving = '"+description+"',status= '"+status+"',publicatiedatum = '"+Date+"' WHERE id=" + id + ";";
con.execute(SQL);
int courseIndex = Webcasts.getItems().indexOf(webcast);
Webcast changedCourse = new Webcast(id, id, Date, status, name, description, url, speakerName, organisationName);
Object[] webcasts = Webcasts.getItems().toArray();
webcasts[courseIndex] = changedCourse;
Webcasts.getItems().clear();
Webcasts.getItems().addAll(webcasts);
Stage thisStage = (Stage) titleInput.getScene().getWindow();
        thisStage.close();


}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printModules());
        stage.show();
    }

}
