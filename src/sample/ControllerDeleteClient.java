package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerDeleteClient {

    public Button CancelButton;
    public Button ProceedButton;
    public TextField ClientToDelete_box;
    public ChangeScreens changeScreensObject = new ChangeScreens();
    public TableView tableView;
    public Button loadButton;
    public Label wrongInputDataID;
    private ObservableList<ObservableList> data;
    public DatabaseConnection databaseConnectionObject = new DatabaseConnection();

    public void cancelButtonAction(ActionEvent actionEvent) {
        System.out.println("CancelButton");
        try {
            ClientToDelete_box.setText("");
            String string = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,string);
        }catch (IOException e){

        }
    }

    public void proceedButtonAction(ActionEvent actionEvent) {
        System.out.println("AddFundButton");
        boolean flagID;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.connectDatabase();
            int clientID = 0;
            try{
                clientID = Integer.parseInt(ClientToDelete_box.getText());
                if(checkClientAvailability(connection, clientID) == 0){
                    wrongInputDataID.setVisible(true);
                    ClientToDelete_box.setText("");
                    flagID = false;
                }else {
                    wrongInputDataID.setVisible(false);
                    flagID = true;
                }
            }catch (NumberFormatException e){
                wrongInputDataID.setVisible(true);
                ClientToDelete_box.setText("");
                flagID = false;
            }
            if(flagID) {
                connection = databaseConnection.connectDatabase();
                deleteClient(connection,clientID);
                String string = "MainMenu.fxml";
                changeScreensObject.changeScreens(actionEvent, string);
            }
        }catch (IOException e){

        }
    }

    public int checkClientAvailability(Connection connection, int clientID){
        try {
            String sqlQuery = "SELECT clientID FROM [Bank Clients] WHERE clientID = " + clientID;
            int viableID = 0;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()){
                viableID = resultSet.getInt("clientID");
                System.out.println(viableID);
            }
            connection.close();
            return viableID;
        }catch (SQLException e){
            return 0;
        }
    }

    public void deleteClient(Connection connection, int clientID){
        try{
            String deletePersonQuery = "DELETE FROM [Person] WHERE clientID = " + clientID;
            String deleteClientQuery = "DELETE FROM [Bank Clients] WHERE clientID = " + clientID;
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteClientQuery);
            statement.executeUpdate(deletePersonQuery);
            connection.close();
        }catch(SQLException e){

        }
    }

    public void loadButtonAction(ActionEvent actionEvent) {
        try {
            data = FXCollections.observableArrayList();
            Connection connection = databaseConnectionObject.connectDatabase();
            String displayClientsSqlQuery = "SELECT Person.clientID ," + "personName ," + "personSurname ," + "personCountry ," + "personTown ," + "personAddress ," + "personPesel ," + "[Bank Clients].clientFunds" + " FROM Person INNER JOIN [Bank Clients] ON Person.clientID = [Bank Clients].clientID";
            ResultSet resultSet = connection.createStatement().executeQuery(displayClientsSqlQuery);
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

        }
    }
}
