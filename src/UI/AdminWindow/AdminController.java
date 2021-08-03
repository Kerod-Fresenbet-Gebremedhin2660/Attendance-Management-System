/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.AdminWindow;

import Alerts.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import filehandling.fileeditor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class AdminController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab bookIssueTab;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXComboBox<String> month;
    @FXML
    private Tab renewTab;

    @FXML
    private Tab renewTab1;

    @FXML
    private TableView tableview;

    @FXML
    private TableColumn<employee, String> nameCol;

    @FXML
    private TableColumn<employee, String> idCol;

    @FXML
    private TableColumn<employee, String> dateCol;

    @FXML
    private TableColumn<employee, String> validCol;

    @FXML
    private JFXTextField idfield;

    @FXML
    private JFXButton search2;

    @FXML
    private TableView<employee> tableview1;

    @FXML
    private TableColumn<employee, String> nameCol1;

    @FXML
    private TableColumn<employee, String> idCol1;

    @FXML
    private TableColumn<employee, String> dateCol1;

    @FXML
    private TableColumn<employee, String> validCol1;

    @FXML
    private JFXDatePicker date2;

    @FXML
    private JFXTextField newstitle;

    @FXML
    private JFXTextArea newsarea;

    @FXML
    private JFXButton post;

    @FXML
    private JFXButton postvideo;

    @FXML
    private StackPane stackpane;

    @FXML
    private BorderPane anchorpane2;

    @FXML
    void postAction(ActionEvent event) {
        String title = newstitle.getText();
        String news = newsarea.getText();
        if(newstitle.getText().isEmpty()){
            AlertMaker.showMaterialDialog(stackpane, anchorpane2, new ArrayList(), "Insufficient Data", "Please enter data in title field.");
        }
        else if (newsarea.getText().isEmpty()){
            AlertMaker.showMaterialDialog(stackpane, anchorpane2, new ArrayList(), "Insufficient Data", "Please enter data in news field.");
        }
        else {
            fileeditor.postNews(title, news);
            AlertMaker.showMaterialDialog(stackpane, anchorpane2, new ArrayList(), "PUBLISHED", "News has been Sucessfully published.");
        }
    }

    @FXML
    void search1Action(ActionEvent event) {
        initCol();
        String mo = month.getValue();
        if (month.getValue() == null && date.getValue() == null) {
            AlertMaker.showMaterialDialog(stackpane, anchorpane2, new ArrayList(), "Insufficient Data", "Please enter data in required fields.");
        } else if (date.getValue() == null) {
            loadData(mo, "null");
        } else {
            LocalDate date1 = date.getValue();
            Instant a = Instant.from(date1.atStartOfDay(ZoneId.systemDefault()));
            Date d = Date.from(a);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(d);
            String curday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            System.err.println(curday);
            loadData(mo, curday);
        }

        System.err.println(mo);

    }

    @FXML
    void search2Action(ActionEvent event) {
        initCol1();
        String id = idfield.getText();
        
        if (idfield.getText().isEmpty() && date2.getValue() == null) {
            AlertMaker.showMaterialDialog(stackpane, anchorpane2, new ArrayList(), "Insufficient Data", "Please enter data in required fields.");
        } else if (date2.getValue() == null) {
            int id2 = Integer.parseInt(id);
            loadData2(id2, "null");
        } else {
            int id2 = Integer.parseInt(id);
            LocalDate date1 = date2.getValue();
            Instant a = Instant.from(date1.atStartOfDay(ZoneId.systemDefault()));
            Date d = Date.from(a);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(d);
            String curday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            System.err.println(curday);
            loadData2(id2, curday);
        }
//
//        //System.err.println(id);
        //loadData2(29, "2019-02-06");
    }

    @FXML
    void postvideoAction(ActionEvent event) {
        Stage stage = null;
        FileChooser fileCh = new FileChooser();
        fileCh.setTitle("Files");
        fileCh.showOpenDialog(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            init();
            initCol();
            //loadData("february", "null");
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<String> lili = FXCollections.observableArrayList("january", "february", "march", "april");
        month.setItems(lili);
        //tableview.setItems(lili);
    }

    private void init() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/AdminWindow/toolbar/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);

        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        validCol.setCellValueFactory(new PropertyValueFactory<>("pre"));
    }

    private void initCol1() {
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        validCol1.setCellValueFactory(new PropertyValueFactory<>("pre"));
    }

    ObservableList<employee> list = FXCollections.observableArrayList();
    ObservableList<employee> list1 = FXCollections.observableArrayList();

    private void loadData(String month, String date) {
        list.clear();
        String p = null;
        if (date.equals("null")) {
            try {
                Connection con = getConnection();
                PreparedStatement sel = con.prepareStatement("SELECT * FROM " + month);
                //sel.setString(1, date);
                ArrayList<String> a = new ArrayList<>();
                ResultSet res = sel.executeQuery();
                while (res.next()) {
                    String name = res.getString("name");
                    String id = res.getString("id");
                    String date1 = res.getString("date");
                    if (res.getString("punchout") != null) {
                        p = "YES";
                    } else {
                        p = "NO";
                    }

                    list.add(new employee(name, id, date1, p));
                }

                System.err.println("Select Sucess.");

            } catch (Exception e) {
                System.out.println(e);
            }
            tableview.setItems(list);
        } else {
            try {
                Connection con = getConnection();
                PreparedStatement sel = con.prepareStatement("SELECT * FROM " + month + " WHERE date=?");
                sel.setString(1, date);
                ArrayList<String> a = new ArrayList<>();
                ResultSet res = sel.executeQuery();
                while (res.next()) {
                    String name = res.getString("name");
                    String id = res.getString("id");
                    String date1 = res.getString("date");
                    if (res.getString("punchout") != null) {
                        p = "YES";
                    } else {
                        p = "NO";
                    }

                    list.add(new employee(name, id, date1, p));
                }

                System.err.println("Select Sucess.");

            } catch (Exception e) {
                System.out.println(e);
            }
            tableview.setItems(list);
        }

    }

    private void loadData2(int id, String date) {
        String arr[] = {"january", "february", "march", "april", "may", "june"};
        list1.clear();
        String p = null;
        for (int i = 0; i < arr.length; i++) {
            if (date.equals("null")) {
                try {
                    Connection con = getConnection();
                    PreparedStatement sel = con.prepareStatement("SELECT * FROM " + arr[i]+" WHERE id=?");
                    sel.setInt(1, id);
                    ArrayList<String> a = new ArrayList<>();
                    ResultSet res = sel.executeQuery();
                    while (res.next()) {
                        String name = res.getString("name");
                        String id1 = res.getString("id");
                        String date1 = res.getString("date");
                        if (res.getString("punchout") != null) {
                            p = "YES";
                        } else {
                            p = "NO";
                        }

                        list1.add(new employee(name, id1, date1, p));
                    }

                    System.err.println("Select Sucess.");

                } catch (Exception e) {
                    System.out.println(e);
                }
                tableview1.setItems(list1);
            } else {
                try {
                    Connection con = getConnection();
                    PreparedStatement sel = con.prepareStatement("SELECT * FROM " + arr[i] + " WHERE date=? AND id=?");
                    sel.setString(1, date);
                    sel.setInt(2, id);
                    ArrayList<String> a = new ArrayList<>();
                    ResultSet res = sel.executeQuery();
                    while (res.next()) {
                        String name = res.getString("name");
                        String id1 = res.getString("id");
                        String date1 = res.getString("date");
                        if (res.getString("punchout") != null) {
                            p = "YES";
                        } else {
                            p = "NO";
                        }

                        list1.add(new employee(name, id1, date1, p));
                    }

                    System.err.println("Select Sucess.");

                } catch (Exception e) {
                    System.out.println(e);
                }
                tableview1.setItems(list1);
            }

        }

    }

    public static Connection getConnection() throws Exception {
        try {

            String url = "jdbc:mysql://localhost:3306/attendancemanagement";
            String username = "Henok Gelaneh";
            String password = "secret26";

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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

//    private void loadData() {
//        
//        
//        String qu = "SELECT * FROM BOOK";
//        ResultSet rs = handler.execQuery(qu);
//        try {
//            while (rs.next()) {
//                String titlex = rs.getString("title");
//                String author = rs.getString("author");
//                String id = rs.getString("id");
//                String publisher = rs.getString("publisher");
//                Boolean avail = rs.getBoolean("isAvail");
//
//                list.add(new Book(titlex, id, author, publisher, avail));
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        tableview.setItems(list);
//    }
}
