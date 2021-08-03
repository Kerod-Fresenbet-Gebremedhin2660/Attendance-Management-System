/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import regex.regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import databasehandling.SecurityDB;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Alerts.AlertMaker;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import databasehandling.EmployeeDB;
import databasehandling.databaseHelper;
import Game.GameFrame;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class LoginController implements Initializable {

//    private final static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label redlabel;
    @FXML
    private Label userlabel;
    @FXML
    private Label passlabel;

    @FXML
    private StackPane stackp;

    @FXML
    private AnchorPane anchorp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handletextfieldAction(ActionEvent event) {
        redlabel.setText("ATTENDANCE MANAGEMENT SYSTEM");
        redlabel.setStyle("-fx-background-color: #2A2E37");
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception {

        if (!databaseHelper.isReachable()) {
            new GameFrame().setVisible(true);
        } else {
            userlabel.setText("");
            passlabel.setText("");
            redlabel.setText("ATTENDANCE MANAGEMENT SYSTEM");
            redlabel.setStyle("-fx-background-color: black");
            String uname = username.getText();
            String pword = password.getText();

            try {
                Integer.parseInt(uname);
                System.err.println("Yes");
            } catch (Exception e) {
                System.err.println("No");
                redlabel.setText("INVALID CREDENTIALS");
                redlabel.setStyle("-fx-background-color: d32f2f");
                username.getStyleClass().add("wrong-credentials");
         

            }

            if (username.getText().isEmpty()) {
                userlabel.setText("*username required");
            }
            //
            if (password.getText().isEmpty()) {
                passlabel.setText("*password required");
            } else {
                ArrayList<String> a = SecurityDB.selectID();
                ArrayList<String> b = SecurityDB.selectPassword();
                ArrayList<String> c = SecurityDB.selectType();
                int idid = Integer.parseInt(uname);
                for (int i = 0; i < a.size(); i++) {
                    if ((a.get(i).equals(uname)) && (b.get(i).equals(pword))) {
                        closeStage();
                        String type = c.get(i);
                        if ("user".equals(type)) {
                            AlertMaker.showMaterialDialog(stackp, anchorp, new ArrayList<>(), "Login Sucessful", "Welcome Employee!");
                            EmployeeDB.saveid(idid);
                            System.err.println(EmployeeDB.loadid());
                            loadUser();
                            closeStage();
                        } else {
                            AlertMaker.showMaterialDialog(stackp, anchorp, new ArrayList<>(), "Login Sucessful", "Welcome Administrator!");
                            EmployeeDB.saveid(idid);
                            System.err.println(EmployeeDB.loadid());
                            loadAdmin();
                            closeStage();
                        }
                        break;
                    } else {
                        redlabel.setText("INVALID CREDENTIALS");
                        redlabel.setStyle("-fx-background-color: d32f2f");
                        username.getStyleClass().add("wrong-credentials");
                        password.getStyleClass().add("wrong-credentials");
                    }
                }
            }

        }
    }

    void loadAdmin() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/AdminWindow/AdminWindow.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Attendance Management: Administrator");
            stage.setScene(new Scene(parent));
            stage.show();
            //.setStageIcon(stage);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    void loadUser() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/UI/EmployeeWindow/EmployeeMain.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Attendance Management: Employee");
            stage.setScene(new Scene(parent));
            stage.show();
            //.setStageIcon(stage);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

//    void loadMain() {
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/library/assistant/ui/main/main.fxml"));
//            Stage stage = new Stage(StageStyle.DECORATED);
//            stage.setTitle("Library Assistant");
//            stage.setScene(new Scene(parent));
//            stage.show();
//            LibraryAssistantUtil.setStageIcon(stage);
//        }
//        catch (IOException ex) {
//            //LOGGER.log(Level.ERROR, "{}", ex);
//        }
//    }
}
