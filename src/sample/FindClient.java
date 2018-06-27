package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindClient {
    public final String displaySpecificClientsSelectQuery = "SELECT * FROM Person INNER JOIN [Bank Clients] ON Person.clientID = [Bank Clients].clientID WHERE ";
    public final String personID = "Person.clientID = ";
    public final String personName = "Person.personName = ";
    public final String personSurname = "Person.personSurname = ";
    public final String personCountry = "Person.personCountry = ";
    public final String personTown = "Person.personTown = ";
    public final String personAddress = "Person.personAddress = ";
    public final String personPesel = "Person.personPesel = ";


    public void findClient(int choice, String statement, Connection connection){
        switch(choice){
            case 1:
                displayClients(connection,displaySpecificClientsSelectQuery + personID + statement);
                break;
            case 2:
                displayClients(connection,displaySpecificClientsSelectQuery + personName + "'" + statement + "'");
                break;
            case 3:
                displayClients(connection,displaySpecificClientsSelectQuery + personSurname + "'" + statement + "'");
                break;
            case 4:
                displayClients(connection,displaySpecificClientsSelectQuery + personCountry + "'" + statement + "'");
                break;
            case 5:
                displayClients(connection,displaySpecificClientsSelectQuery + personTown + "'" + statement + "'");
                break;
            case 6:
                displayClients(connection,displaySpecificClientsSelectQuery + personAddress + "'" + statement + "'");
                break;
            case 7:
                displayClients(connection,displaySpecificClientsSelectQuery + personPesel + "'" + statement + "'");
                break;
            default:
                break;
        }
    }

//    public void displayClients(Connection connection, String findClientQuery){
//        try{
//            System.out.println("displayClients");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(findClientQuery);
//            while(resultSet.next()) {
//                int id = resultSet.getInt("clientID");
//                String name = resultSet.getString("personName");
//                String surname = resultSet.getString("personSurname");
//                String country = resultSet.getString("personCountry");
//                String town = resultSet.getString("personTown");
//                String address = resultSet.getString("personAddress");
//                String pesel = resultSet.getString("personPesel");
//                double money = resultSet.getDouble("clientFunds");
//                System.out.println(id + " " + name + " " + surname + " " + country + " " + town + " " + address + " " + pesel + " " + money);
//            }
//            connection.close();
//        }catch(SQLException exception){
//
//        }
//    }
public void displayClients(Connection connection, String findClientQuery){
    try{
        System.out.println("displayClients");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(findClientQuery);
        while(resultSet.next()) {
            int id = resultSet.getInt("clientID");
            String name = resultSet.getString("personName");
            String surname = resultSet.getString("personSurname");
            String country = resultSet.getString("personCountry");
            String town = resultSet.getString("personTown");
            String address = resultSet.getString("personAddress");
            String pesel = resultSet.getString("personPesel");
            double money = resultSet.getDouble("clientFunds");
            System.out.println(id + " " + name + " " + surname + " " + country + " " + town + " " + address + " " + pesel + " " + money);
        }
        connection.close();
    }catch(SQLException exception){

    }
}
}
