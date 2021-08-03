/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.AddEmployee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import databasehandling.EmployeeDB;
import EntityModels.Employee;
import Alerts.AlertMaker;
import java.util.ArrayList;
import javafx.scene.layout.StackPane;
import databasehandling.databaseHelper;
import javafx.stage.Stage;
import databasehandling.SecurityDB;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class AddemployeeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane mainContainer;

    @FXML
    private StackPane stack;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXComboBox<String> sex;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    void addPicture(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        closeStage();
    }
    
    private void closeStage() {
        ((Stage) mainContainer.getScene().getWindow()).close();
    }

    @FXML
    void saveAction(ActionEvent event) throws Exception {
        
        String n = name.getText();
        String m = mobile.getText();
        String e = email.getText();
        String s = sex.getValue();

        if (id.getText().isEmpty() || name.getText().isEmpty() || mobile.getText().isEmpty() || email.getText().isEmpty() || sex.getValue()=="") {
            AlertMaker.showMaterialDialog(stack, mainContainer, new ArrayList(), "Insufficent Inputs", "Please enter to all applicable fields.");
        } else {
            int id1 = Integer.parseInt(id.getText());
            if (databaseHelper.itExists(id1)) {
                AlertMaker.showMaterialDialog(stack, mainContainer, new ArrayList(), "Invalid Inputs", "Sorry, the set ID has already been Taken");
            } else {
                Employee em = new Employee(id1, n, m, e, s, 30, "");
                EmployeeDB.addEmployee(em);
                SecurityDB.addPassword(id1, s+"123", "user");
                AlertMaker.showMaterialDialog(stack, mainContainer, new ArrayList(), "Registration Sucessful", "Employee " + n + " has been Added.");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> lili = FXCollections.observableArrayList("male", "female");
        sex.setItems(lili);
    }

}
