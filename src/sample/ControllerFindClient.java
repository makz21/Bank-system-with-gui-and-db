package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerFindClient {
    public Button CancelButton;
    public Button ProceedButton;
    public TableView tableView;
    public Button SearchButton;
    public TextField EnterCriterium_box;
    public TextField EnterStatement_box;
    public ChangeScreens changeScreensObject = new ChangeScreens();
    public DatabaseConnection databaseConnectionObject = new DatabaseConnection();
    public Label wrongInputDataCriterium;
    private ObservableList<ObservableList> data;
    public final String displaySpecificClientsSelectQuery = "SELECT Person.clientID ," + "personName ," + "personSurname ," + "personCountry ," + "personTown ," + "personAddress ," + "personPesel ," + "[Bank Clients].clientFunds" + " FROM Person INNER JOIN [Bank Clients] ON Person.clientID = [Bank Clients].clientID ";
    public final String personID = "WHERE Person.clientID = ";
    public final String personName = "WHERE Person.personName = ";
    public final String personSurname = "WHERE Person.personSurname = ";
    public final String personCountry = "WHERE Person.personCountry = ";
    public final String personTown = "WHERE Person.personTown = ";
    public final String personAddress = "WHERE Person.personAddress = ";
    public final String personPesel = "WHERE Person.personPesel = ";

    public void cancelButtonAction(ActionEvent actionEvent) {
        System.out.println("CancelButton");
        try {
            EnterCriterium_box.setText("");
            EnterStatement_box.setText("");
            String string = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,string);
        }catch (IOException e){

        }
    }

    public void proceedButtonAction(ActionEvent actionEvent) {
        System.out.println("FinClientButton");
        try {
            String string = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,string);
        }catch (IOException e){

        }
    }
    public void searchButtonAction(ActionEvent actionEvent) {
        int choice = 0;
        if(validInputForChoice(EnterCriterium_box.getText())){
            String statement = EnterStatement_box.getText();
            choice = Integer.parseInt(EnterCriterium_box.getText());
            wrongInputDataCriterium.setVisible(false);
            findClient(choice,statement);
        }
        else{
            wrongInputDataCriterium.setVisible(true);
        }
    }

    public void findClient(int choice, String statement){
        switch(choice){
            case 1:
                displayClients(displaySpecificClientsSelectQuery + personID + statement);
                break;
            case 2:
                displayClients(displaySpecificClientsSelectQuery + personName + "'" + statement + "'");
                break;
            case 3:
                displayClients(displaySpecificClientsSelectQuery + personSurname + "'" + statement + "'");
                break;
            case 4:
                displayClients(displaySpecificClientsSelectQuery + personCountry + "'" + statement + "'");
                break;
            case 5:
                displayClients(displaySpecificClientsSelectQuery + personTown + "'" + statement + "'");
                break;
            case 6:
                displayClients(displaySpecificClientsSelectQuery + personAddress + "'" + statement + "'");
                break;
            case 7:
                displayClients(displaySpecificClientsSelectQuery + personPesel + "'" + statement + "'");
                break;
            default:
                break;
        }
    }

    public void displayClients(String query){
        data = FXCollections.observableArrayList();
        try{
            Connection connection = databaseConnectionObject.connectDatabase();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            for(int i=0 ; i<resultSet.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn tableColumn = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
                tableColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(tableColumn);
                System.out.println("Column ["+i+"] ");
            }
            while(resultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                    row.add(resultSet.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }
            tableView.setItems(data);
        }catch(SQLException e){

        }
    }

    boolean validInputForChoice(String stringToValidate){
        if(Pattern.matches("[1-7]+", stringToValidate)){
            return true;
        } else {
            System.out.println("Podales nie poprawne dane");
            return false;
        }
    }

}


