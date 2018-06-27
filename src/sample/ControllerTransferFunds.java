package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

public class ControllerTransferFunds {
    public TextField AdresseeID_box;
    public TextField ReceiverID_box;
    public TextField AmoutOfMoney_box;
    public Button CancelButton;
    public Button ProceedButton;
    public ChangeScreens changeScreensObject = new ChangeScreens();
    public TableView tableView;
    public Button loadButton;
    public ObservableList<ObservableList> data;
    public DatabaseConnection databaseConnectionObject = new DatabaseConnection();
    public Label wrongInputDataAddressee;
    public Label wrongInputDataReceiver;
    public Label wrongInputDataMoney;

    public void cancelButtonAction(ActionEvent actionEvent) {
        System.out.println("CancelButton");
        try {
            AdresseeID_box.setText("");
            ReceiverID_box.setText("");
            AmoutOfMoney_box.setText("");
            String string = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,string);
        }catch (IOException e){

        }
    }

    public void proceedButtonAction(ActionEvent actionEvent) {
        System.out.println("ProceedButton");
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.connectDatabase();
            Transactions transactions = new Transactions();
            RoundDownMoney roundDownMoneyObject = new RoundDownMoney();
            boolean flagAddressee, flagReceiver, flagMoney, flagBalance;
            int adresseeID = 0;
            try{
                connection = databaseConnection.connectDatabase();
                adresseeID = Integer.parseInt(AdresseeID_box.getText());
                if(checkFundsAvailability(connection,adresseeID) == 0){
                    wrongInputDataAddressee.setVisible(true);
                    AdresseeID_box.setText("");
                    flagAddressee = false;
                }else {
                    wrongInputDataAddressee.setVisible(false);
                    flagAddressee = true;
                }
            }catch (NumberFormatException e){
                wrongInputDataAddressee.setVisible(true);
                AdresseeID_box.setText("");
                flagAddressee = false;
            }
            int receiverID = 0;
            try{
                connection = databaseConnection.connectDatabase();
                receiverID = Integer.parseInt(ReceiverID_box.getText());
                if(checkClientAvailability(connection, receiverID) == 0 || receiverID==adresseeID){
                    wrongInputDataReceiver.setVisible(true);
                    ReceiverID_box.setText("");
                    flagReceiver = false;
                }
                else {
                    wrongInputDataReceiver.setVisible(false);
                    flagReceiver = true;
                }
            }catch (NumberFormatException e){
                wrongInputDataReceiver.setVisible(true);
                ReceiverID_box.setText("");
                flagReceiver = false;
            }
            double amountOfMoney = 0;
            try {
                amountOfMoney = Double.parseDouble(AmoutOfMoney_box.getText());
                if(amountOfMoney < 0){
                    flagMoney = false;
                    wrongInputDataMoney.setVisible(true);
                    AmoutOfMoney_box.setText("");
                }else {
                    flagMoney = true;
                    wrongInputDataMoney.setVisible(false);
                }
            }catch (NumberFormatException e){
                wrongInputDataMoney.setVisible(true);
                AmoutOfMoney_box.setText("");
                flagMoney = false;
            }
            connection = databaseConnectionObject.connectDatabase();
            if(amountOfMoney < checkFundsAvailability(connection, adresseeID)){
                flagBalance = true;
            }else{
                System.out.println("Za malo hajsu");
                flagBalance = false;
                AmoutOfMoney_box.setText("");
                wrongInputDataMoney.setVisible(true);
                flagMoney = false;
            }
            if(flagAddressee && flagMoney && flagReceiver && flagBalance){
                connection = databaseConnectionObject.connectDatabase();
                transactions.addFunds(receiverID, amountOfMoney, connection);
                connection = databaseConnection.connectDatabase();
                transactions.withdrawFunds(adresseeID, amountOfMoney, connection);
                String string = "MainMenu.fxml";
                changeScreensObject.changeScreens(actionEvent, string);
            }
        }catch (IOException e){

        }
    }

    public double checkFundsAvailability(Connection connection, int clientID){
        try {
            String sqlQuery = "SELECT clientFunds FROM [Bank Clients] WHERE clientID = " + clientID;
            double funds = 0;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()){
                funds = resultSet.getDouble("clientFunds");
                System.out.println(funds);
            }
            connection.close();
            return funds;
        }catch (SQLException e){
            return 0;
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
