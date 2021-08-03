/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.EmployeeLeave;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import filehandling.leaveManager;
import UI.login.loginloader;
import databasehandling.EmployeeDB;
import databasehandling.leaveHelper;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import databasehandling.databaseHelper;
import EntityModels.Request;
import databasehandling.leaveHelper;
import Alerts.AlertMaker;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class LeaveRequesterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane stack1;

    @FXML
    private AnchorPane anchor1;

    @FXML
    private JFXComboBox<String> reason;

    @FXML
    private TextField days;

    @FXML
    private Label leavelabel;

    @FXML
    private Label amountlabel;

    @FXML
    private Label penlabel;

    @FXML
    private Label remlabel;

    @FXML
    private Label yearlylabel;

    @FXML
    void cancelAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    void sendAction(ActionEvent event) throws Exception {

        if (leaveManager.checkLeave(EmployeeDB.loadid())) {
            AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "ON LEAVE", "Sorry, you are currently on leave");
        } else {
            if (reason.getValue().isEmpty() || days.getText().isEmpty()) {
                AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "Insufficient Data", "Enter data into all available fields");
            } else {
                ArrayList<String> a = new ArrayList<>();
                a = databaseHelper.nameId(EmployeeDB.loadid());
                String id1 = a.get(0);
                int id2 = Integer.parseInt(id1);
                String name1 = a.get(1);
                String reason1 = reason.getValue();
                String days1 = days.getText();
                int days2 = Integer.parseInt(days1);
                Request r = new Request(id2, name1, reason1, days2);
                leaveHelper.addRequest(r);
                AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "REQUEST FILED", "Your request has been filed.");
            }
        }

    }

    @FXML
    void refreshAction(ActionEvent event) {
        load();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();

    }

    private void closeStage() {
        ((Stage) reason.getScene().getWindow()).close();
    }

    public void load() {
        int id = EmployeeDB.loadid();
        String a = Integer.toString(leaveManager.daysLeft(id));
        if (leaveManager.checkLeave(id)) {
            leavelabel.setText("---YES---");
            String rem = Integer.toString(leaveManager.toReturn(id));
            remlabel.setText(rem);
        } else {
            leavelabel.setText("---");
            remlabel.setText("---");
        }

        yearlylabel.setText(a);
        if (leaveHelper.isPending(id)) {
            penlabel.setText("PENDING");
        }

        ObservableList<String> lili = FXCollections.observableArrayList("Vacation", "Birth", "Funeral", "Personal");
        reason.setItems(lili);

    }

}
