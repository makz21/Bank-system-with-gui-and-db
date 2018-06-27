package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerMainMenu {

    public Button AddFunds;

    public ChangeScreens changeScreensObject = new ChangeScreens();
    public Button AddClient;
    public Button Quit;
    public Button TransferFunds;
    public Button WithdrawFunds;
    public Button FindClient;
    public Button DisplayClients;
    public Button DeleteClient;
    private String okienko;

    public void addFundsAction(ActionEvent actionEvent) {
        System.out.println("AddFundsActionButton");
        try {
            okienko = "AddFunds.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void addClientAction(ActionEvent actionEvent) {
        System.out.println("AddClientActionButton");
        try {
            okienko = "AddClient.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void deleteClientAction(ActionEvent actionEvent) {
        System.out.println("DeleteClientActionButton");
        try {
            okienko = "DeleteClient.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void displayClientsAction(ActionEvent actionEvent) {
        System.out.println("DisplayClientActionButton");
        try {
            okienko = "DisplayClient.fxml";
//            System.out.println("***MARKER***");
//            System.out.println(actionEvent);
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void findClientAction(ActionEvent actionEvent) {
        System.out.println("FindClientActionButton");
        try {
            okienko = "FindClient.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void withdrawFundsAction(ActionEvent actionEvent) {
        System.out.println("WithdrawFundsActionButton");
        try {
            okienko = "WithdrawFunds.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void transferFundsAction(ActionEvent actionEvent) {
        System.out.println("TransferFundsActionButton");
        try {
            okienko = "TransferFunds.fxml";
            changeScreensObject.changeScreens(actionEvent,okienko);
        }catch (IOException e){
            System.out.println("Coś poszło nie tak: " + e.getMessage());
        }
    }

    public void quitAction(ActionEvent actionEvent) {
        System.exit(1);
    }
}
