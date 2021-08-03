package UI.AdminWindow.toolbar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import UI.AddEmployee.addMloader;
import UI.EditEmployee.editEloader;
import UI.LeaveManager.leavemanagerloader;
import UI.ViewEmployees.viewEloader;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ToolbarController implements Initializable {
    
    @FXML
    void checkmessages(ActionEvent event) {

    }

    @FXML
    void employee(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/AddEmployee/addemployee.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Employees");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void leavemanager(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/LeaveManager/leavemanager.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Employees");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void settings(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/EditEmployee/editemployee.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Employees");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void signout(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void viewemployee(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/ViewEmployees/viewemployees.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Employees");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    

    
    

}
