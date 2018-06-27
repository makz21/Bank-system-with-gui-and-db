package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Transactions {

    void addFunds(int id, double money, Connection connection){
        try{
            System.out.println("Test");
            Statement statement = connection.createStatement();
            String addFundsSQLQuery = "IF EXISTS (SELECT * FROM Person INNER JOIN [Bank Clients] ON Person.clientID = [Bank Clients].clientID  WHERE [Bank Clients].clientID =" + id + ")" + "UPDATE [Bank Clients] SET clientFunds = clientFunds +" + money + "WHERE clientID =" + id ;
            statement.executeUpdate(addFundsSQLQuery);
            connection.close();
        }catch (SQLException exception){
        }
    }

    void withdrawFunds(int id, double money, Connection connection){
        try{
            System.out.println("Test2");
            Statement statement = connection.createStatement();
            String addFundsSQLQuery = "IF EXISTS (SELECT * FROM Person INNER JOIN [Bank Clients] ON Person.clientID = [Bank Clients].clientID  WHERE [Bank Clients].clientID =" + id + ")" + "UPDATE [Bank Clients] SET clientFunds = clientFunds -" + money + "WHERE clientID =" + id ;
            statement.executeUpdate(addFundsSQLQuery);
            connection.close();
        }catch (SQLException exception){

        }
    }

}
