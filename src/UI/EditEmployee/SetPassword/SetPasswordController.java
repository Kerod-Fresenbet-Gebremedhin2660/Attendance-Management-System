/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.EditEmployee.SetPassword;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import databasehandling.SecurityDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import Alerts.AlertMaker;
import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import databasehandling.EmployeeDB;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
import javafx.scene.control.Alert;

public class SetPasswordController implements Initializable {

    @FXML
    private StackPane stack;

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXTextField old;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private JFXTextField newp;

    @FXML
    boolean saveAction(ActionEvent event) throws Exception {
        label1.setText("");
            label2.setText("");
            ArrayList<String> pass = SecurityDB.selectPassword();
        if (old.getText().isEmpty() && newp.getText().isEmpty()) {
            label1.setText("  *required");
            label2.setText("  *required");
        } else if (old.getText().isEmpty()) {
            label1.setText("  *required");
        } else if (newp.getText().isEmpty()) {
            label2.setText("  *required");
        } else {
            for (int i=0; i<pass.size();i++){
                if(old.getText().equals(pass.get(i))){
                    try {
                        SecurityDB.updatePassword(EmployeeDB.loadid(), old.getText(), "user");
                        AlertMaker.showMaterialDialog(stack, anchor, new ArrayList<>(), "PASSWORD SET", "Password Sucessfully Changed");
                    } catch (Exception ex) {
                        Logger.getLogger(SetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return true;
                } 
                }
           
                    label1.setText("  *not found");
                    
        }     
            return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
