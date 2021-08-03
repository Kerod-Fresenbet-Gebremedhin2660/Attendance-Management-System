/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.ViewEmployees;

import UI.AdminWindow.AdminController;
import static UI.AdminWindow.AdminController.getConnection;
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
import javafx.scene.layout.AnchorPane;
import UI.AdminWindow.AdminController;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class ViewemployeesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<employee> tableView;

    @FXML
    private TableColumn<employee, String> nameCol;

    @FXML
    private TableColumn<employee, String> idCol;

    @FXML
    private TableColumn<employee, String> mobileCol;

    @FXML
    private TableColumn<employee, String> emailCol;

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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("pre"));
    }
    
    ObservableList<employee> list = FXCollections.observableArrayList();
    // ObservableList<AdminController.employee> list1 = FXCollections.observableArrayList();
    

    private void loadData() {
        list.clear();
        String p = null;

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM employee");

            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                String id = res.getString("id");
                String mobile = res.getString("mobile");
                String email = res.getString("email");

                list.add(new employee(name, id, mobile, email));
            }

            System.err.println("Select Sucess.");

        } catch (Exception e) {
            System.out.println(e);
        }
        tableView.setItems(list);

    }

    public class employee {

        private String name;
        private String id;
        private String date;
        private String pre;

        public employee(String name, String id, String date, String pre) {
            this.date = date;
            this.id = id;
            this.name = name;
            this.pre = pre;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPre() {
            return pre;
        }

        public void setPre(String pre) {
            this.pre = pre;
        }
    }

}
