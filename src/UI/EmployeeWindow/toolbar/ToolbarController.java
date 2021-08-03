package UI.EmployeeWindow.toolbar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ToolbarController implements Initializable {

    
    @FXML
    void loginAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void messageAction(ActionEvent event) {
        
    }

    @FXML
    void profileAction(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/UserProfile/userprofile.fxml"));
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
    void requestAction(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/EmployeeLeave/leaveRequester.fxml"));
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
    void settingAction(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/EditEmployee/SetPassword/setpassword.fxml"));
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
