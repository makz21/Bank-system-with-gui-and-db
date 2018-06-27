package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class ControllerAddClient {
    public TextField ClientName_box;
    public TextField ClientSurname_box;
    public TextField ClientCountry_box;
    public TextField ClientPesel_box;
    public TextField ClientTown_box;
    public TextField ClientAddress_box;
    public TextField ClientFunds_box;
    public Button CancelButton;
    public Button ProceedButton;
    public ChangeScreens changeScreensObject = new ChangeScreens();
    public Label wrongInputDataCountry;
    public Label wrongInputDataName;
    public Label wrongInputDataPesel;
    public Label wrongInputDataFunds;
    public Label wrongInputDataAddress;
    public Label wrongInputDataTown;
    public Label wrongInputDataSurname;

    public void cancelButtonAction(ActionEvent actionEvent) {
        System.out.println("CancelButton");
        try{
            ClientName_box.setText("");
            ClientSurname_box.setText("");
            ClientCountry_box.setText("");
            ClientPesel_box.setText("");
            ClientTown_box.setText("");
            ClientAddress_box.setText("");
            ClientFunds_box.setText("");
            String string = "MainMenu.fxml";
            changeScreensObject.changeScreens(actionEvent,string);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void proceedButtonAction(ActionEvent actionEvent) {
        System.out.println("Dodaj Klienta btn");
        try{
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.connectDatabase();
            boolean flagImie, flagNazwisko, flagKraj, flagMiasto, flagPesel, flagStanKonta, flagAdres;
            try {
                if(!validInputForString(ClientName_box.getText())){
                    wrongInputDataName.setVisible(true);
                    flagImie = false;
                }else{
                    flagImie = true;
                    wrongInputDataName.setVisible(false);
                }
                if(!validInputForString(ClientSurname_box.getText())){
                    wrongInputDataSurname.setVisible(true);
                    flagNazwisko = false;
                }else{
                    flagNazwisko = true;
                    wrongInputDataSurname.setVisible(false);
                }
                if(!validInputForString(ClientCountry_box.getText())){
                    wrongInputDataCountry.setVisible(true);
                    flagKraj = false;
                }else{
                    flagKraj = true;
                    wrongInputDataCountry.setVisible(false);
                }
                if(!validInputForString(ClientTown_box.getText())){
                    wrongInputDataTown.setVisible(true);
                    flagMiasto = false;
                }else{
                    wrongInputDataTown.setVisible(false);
                    flagMiasto = true;
                }
                if(ClientTown_box.getText().length() < 1){
                    wrongInputDataAddress.setVisible(true);
                    flagAdres = false;
                }else{
                    wrongInputDataAddress.setVisible(false);
                    flagAdres = true;
                }
                String test =  ClientPesel_box.getText();
                if(!validInputForNumberData(test) ||  test.length() != 11 ){
                    wrongInputDataPesel.setVisible(true);
                    flagPesel = false;
                }else{
                    flagPesel = true;
                    wrongInputDataPesel.setVisible(false);
                }
                double funds = 0;
                try{
                    funds = Double.parseDouble(ClientFunds_box.getText());
                    if(funds < 0){
                        wrongInputDataFunds.setVisible(true);
                        flagStanKonta = false;
                    }else {
                        flagStanKonta = true;
                        wrongInputDataFunds.setVisible(false);
                    }
                }catch (NumberFormatException e){
                    wrongInputDataFunds.setVisible(true);
                    flagStanKonta = false;
                    System.out.println("Niepoprawny format liczby:" + e.getMessage());
                }
                if(flagKraj && flagStanKonta && flagImie && flagPesel && flagNazwisko && flagMiasto && flagAdres) {
                    Person nowyKlient = new Person(ClientName_box.getText(), ClientSurname_box.getText(), new Address(ClientCountry_box.getText(), ClientTown_box.getText(), ClientAddress_box.getText()), ClientPesel_box.getText());
                    BankClient bankClient = new BankClient(nowyKlient, funds);
                    System.out.println("Obj klient utworzony");
                    String insertPersonQuery = "INSERT INTO [Dane Osoby](ImieOsoby, NazwiskoOsoby, KrajOsoby, MiastoOsoby, AdresOsoby, PeselOsoby) VALUES(" + nowyKlient.forSQLquery() + ")";
                    connection = databaseConnection.connectDatabase();
                    Statement statement = connection.createStatement();
                    String insertClientQuery = "INSERT INTO [Klient Banku] (StanKonta) VALUES(" + bankClient.getStanKonta() + ")";
                    System.out.println("MARKER1");
                    statement.executeUpdate(insertPersonQuery);
                    System.out.println("MARKER2");
                    System.out.println("Dodawanie danych do Dane Osoby");
                    connection = databaseConnection.connectDatabase();
                    statement = connection.createStatement();
                    statement.executeUpdate(insertClientQuery);
                    System.out.println("Dodawanie danych do Klient Banku");
                    statement.close();
                    connection.close();
                    System.out.println("Zamknięcie statement i connection");
                    String string = "MainMenu.fxml";
                    changeScreensObject.changeScreens(actionEvent,string);
                }
            }catch (SQLException e){
                System.out.println("Coś poszło nie tak:" + e.getMessage());
            }
//            addNewClient(connection);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak:" + e.getMessage());
        }
    }

    public int checkPeselAvailability(Connection connection, String clientPesel){
        try {
            String sqlQuery = "SELECT personPesel FROM [Person] WHERE personPesel = " + "'" + clientPesel + "'";
            String viablePESEL = "test";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()){
                System.out.println(viablePESEL);
                viablePESEL = resultSet.getString("personPesel");
                System.out.println(viablePESEL);
                ClientPesel_box.setText("Pesel taken");
            }
            connection.close();
            if(viablePESEL.equals(clientPesel)) {
                return 1;
            }else{
                return 0;
            }
        }catch (SQLException e){
            return 0;
        }
    }

    boolean validInputForString(String stringToValidate){
        if(Pattern.matches("[a-zA-Z]+", stringToValidate)){
            return true;
        } else {
            System.out.println("Podales niepoprawne daneA-z");
            return false;
        }
    }

    boolean validInputForNumberData(String stringToValidate){
        if(Pattern.matches("[0-9]+", stringToValidate)){
            return true;
        } else {
            System.out.println("Podales niepoprawne dane0-9");
            return false;
        }
    }

}