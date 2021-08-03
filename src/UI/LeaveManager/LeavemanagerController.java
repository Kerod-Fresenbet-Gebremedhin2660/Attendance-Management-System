/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.LeaveManager;

import static UI.AdminWindow.AdminController.getConnection;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import databasehandling.leaveHelper;
import filehandling.leaveManager;
import Alerts.AlertMaker;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class LeavemanagerController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private StackPane stack1;

    @FXML
    private AnchorPane anchor1;
    
    @FXML
    private TableView<dataType> tableView;

    @FXML
    private JFXTextField id;
    
     @FXML
    private TableColumn<dataType, String> namecol;

    @FXML
    private TableColumn<dataType, String> idcol;

    @FXML
    private TableColumn<dataType, String> dayscol;

    @FXML
    private TableColumn<dataType, String> reasoncol;

    @FXML
    void approveAction(ActionEvent event) throws IOException {
        
        if(id.getText().isEmpty()){
            AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "Insufficent data", "Enter value into ID");
        }
        else{
            int id1 = Integer.parseInt(id.getText());
            leaveManager.approveLeave(id1, leaveHelper.getAmount(id1));
            leaveHelper.removeRequest(id1);
             AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "SUCCESS", "Leave Request Approved");
        }
    }

    @FXML
    void denyAction(ActionEvent event) {
        if(id.getText().isEmpty()){
            AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "Insufficent data", "Enter value into ID");
        }
        else{
            int id1 = Integer.parseInt(id.getText());
            leaveHelper.removeRequest(id1);
            AlertMaker.showMaterialDialog(stack1, anchor1, new ArrayList(), "SUCCESS", "Leave Request Denied");
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        initCol();
        loadData();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }    
    
    private void initCol() {
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        reasoncol.setCellValueFactory(new PropertyValueFactory<>("request"));
        dayscol.setCellValueFactory(new PropertyValueFactory<>("days"));
    }
    
    ObservableList<dataType> list = FXCollections.observableArrayList();
    
    private void loadData() {
        list.clear();
        String p = null;

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM leaverequests");

            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                String id = res.getString("id");
                String r = res.getString("reason");
                String d = res.getString("daysrequested");

                list.add(new dataType(name, id, r, d));
            }

            System.err.println("Select Sucess.");

        } catch (Exception e) {
            System.out.println(e);
        }
        tableView.setItems(list);

    }

    public class dataType {

        private String name;
        private String id;
        private String request;
        private String days;

        public dataType(String name, String id, String request, String days) {
            this.days = days;
            this.id = id;
            this.name = name;
            this.request = request;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

    }
    
}
