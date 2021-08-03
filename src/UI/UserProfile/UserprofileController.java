/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.UserProfile;

import UI.AdminWindow.AdminController;
import static UI.AdminWindow.AdminController.getConnection;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import databasehandling.EmployeeDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.ImageIcon;
import databasehandling.EmployeeDB;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class UserprofileController implements Initializable {

    @FXML
    private TableColumn<employee, String> nameCol;

    @FXML
    private TableColumn<employee, String> idCol;

    @FXML
    private TableColumn<employee, String> dateCol;

    @FXML
    private TableColumn<employee, String> validCol;

    @FXML
    private TableView<employee> tableview1;

    @FXML
    private TableView<employee> tableview2;

    @FXML
    private Pane paneView;

    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton back;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label email;

    @FXML
    private Label mobile;

    @FXML
    private Label propic;

    @FXML
    private ImageView imageicon;

    @FXML
    void handleBackAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    void handleEditAction(ActionEvent event) throws Exception {
        loadChartData();
        loadUserData();
        initCol();
        loadData(EmployeeDB.loadid());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadChartData();
            loadUserData();
            initCol();
            loadData(EmployeeDB.loadid());
        } catch (Exception ex) {
            Logger.getLogger(UserprofileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        validCol.setCellValueFactory(new PropertyValueFactory<>("pre"));
    }

    private void closeStage() {
        ((Stage) edit.getScene().getWindow()).close();
    }
    
    ObservableList<employee> list = FXCollections.observableArrayList();

    private void loadUserData() throws Exception {
        ArrayList<String> a = EmployeeDB.selectUser(EmployeeDB.loadid());
        name.setText(a.get(1));
        id.setText(a.get(0));
        mobile.setText(a.get(2));
        email.setText(a.get(3));
        Image icon = new Image(a.get(4), true);
        imageicon.setImage(icon);
    }

    private void loadChartData() {
        paneView.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("MONTHS");
        yAxis.setLabel("DAYS");
        BarChart b = new BarChart(xAxis, yAxis);
        b.setTitle("ATTENDANCE");
        b.setBarGap(10);
        b.setStyle("-fx-background-color: #FFFFFF");
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Days Present");
        series.getData().add(new XYChart.Data<>("JANUARY", 30));
        series.getData().add(new XYChart.Data<>("FEBRUARY", 12));
        series.getData().add(new XYChart.Data<>("MARCH", 28));
        b.getData().add(series);
        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Days Absent");

        series2.getData().add(new XYChart.Data<>("JANUARY", 0));
        series2.getData().add(new XYChart.Data<>("FEBRUARY", 18));
        series2.getData().add(new XYChart.Data<>("MARCH", 2));
        b.getData().add(series2);
        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName("Days Late");

        series3.getData().add(new XYChart.Data<>("JANUARY", 9));
        series3.getData().add(new XYChart.Data<>("FEBRUARY", 4));
        series3.getData().add(new XYChart.Data<>("MARCH", 15));
        b.getData().add(series3);
        paneView.getChildren().add(b);
    }

    private void loadData(int id) {
        String arr[] = {"january", "february", "march", "april", "may", "june"};
        list.clear();
        String p = null;
        for (int i = 0; i < arr.length; i++) {
            try {
                Connection con = getConnection();
                PreparedStatement sel = con.prepareStatement("SELECT * FROM " + arr[i] + " WHERE id=?");

                sel.setInt(1, id);
                ArrayList<String> a = new ArrayList<>();
                ResultSet res = sel.executeQuery();
                while (res.next()) {
                    String name = res.getString("date");
                    String id1 = res.getString("punchin");
                    String date1 = res.getString("punchout");
                    if (res.getString("punchout") != null) {
                        p = "YES";
                    } else {
                        p = "NO";
                    }

                    list.add(new employee(name, id1, date1, p));
                }

                System.err.println("Select Sucess.");

            } catch (Exception e) {
                System.out.println(e);
            }
            tableview1.setItems(list);
        }

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
