package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerDisplayClient{

    public Button BackButton;
    public Button LoadButton;
    public ChangeScreens changeScreensObject = new ChangeScreens();
    public DatabaseConnection databaseConnectionObject = new DatabaseConnection();
    public TableView tableView;
    public ObservableList<ObservableList> data;
    public String okienko;
    public void backButtonAction(ActionEvent actionEvent) {
        System.out.println("Cofnij");
        try {
            okienko = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void loadButtonAction(ActionEvent actionEvent) {
        try {
            okienko = "DisplayClient.fxml";
            System.out.println("Przycisk załaduj bazę wciśnięty");
            data = FXCollections.observableArrayList();
            Connection conn = databaseConnectionObject.connectDatabase();
            String displayClientsSqlQuery = "SELECT [Dane Osoby].klientID ," + "ImieOsoby ," + "NazwiskoOsoby ," + "KrajOsoby ," + "MiastoOsoby ," + "AdresOsoby ," + "PeselOsoby ," + "[Klient Banku].StanKonta/100 AS StanKonta " +
                    "FROM [Dane Osoby] INNER JOIN [Klient Banku] ON [Dane Osoby].klientID = [Klient Banku].klientID";
            ResultSet resultSet = conn.createStatement().executeQuery(displayClientsSqlQuery);
            data.removeAll(data);
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn tableColumn = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(tableColumn);
                System.out.println("Column [" + i + "] ");
            }
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);
        } catch (SQLException e) {
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

}

