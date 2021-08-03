/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.EmployeeWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import Alerts.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import databasehandling.AttendanceDB;
import databasehandling.validator.validatorDB;
import Alerts.AlertMaker;
import databasehandling.EmployeeDB;
import filehandling.leaveManager;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class EmployeeMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane anchor;

    @FXML
    private StackPane stackpane;

    @FXML
    private Button button;

    @FXML
    private Label timeLabel;

    @FXML
    private Label labelDate;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private void checkInAction(ActionEvent event) throws Exception {
        Date date1 = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date1);
        String curtime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        if (leaveManager.checkLeave(EmployeeDB.loadid())) {
            AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "ON LEAVE", "Sorry, you are currently on leave");
        } else {
            if (validatorDB.canPin(id)) {
                if (validatorDB.PinTime()) {
                    AttendanceDB.punchIn(id);
                    AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "YOU HAVE PUNCHED IN", "TIME : " + curtime);
                } else {
                    AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "PAST PUNCH IN TIME", "Sorry, you are past the punch in time");
                }
            } else {
                AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "ALREADY PUNCHED IN", "You cannot punch in again today.");
            }
        }

    }

    @FXML
    private void checkOutAction(ActionEvent event) throws Exception {
        Date date1 = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date1);
        String curtime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        if (leaveManager.checkLeave(EmployeeDB.loadid())) {
            AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "ON LEAVE", "Sorry, you are currently on leave");
        } else {

            if (validatorDB.isDone(id)) {
                AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "ALREADY PUNCHED OUT", "You cannot punch out again today.");
            } else {
                if (validatorDB.canPout(id)) {
                    if (validatorDB.PoutTime()) {
                        AttendanceDB.punchOut(id);
                        AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "YOU HAVE PUNCHED OUT", "TIME : " + curtime);
                    } else {
                        AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "PAST PUNCH OUT TIME", "Sorry, you cannot punch out now.");
                    }
                } else {
                    AlertMaker.showMaterialDialog(stackpane, timeLabel, new ArrayList(), "NOT PUNCHED IN", "Please, punch in first");
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTime();
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int id = 29;

//    @FXML
//    private void handleButtonAction(javafx.event.ActionEvent event) {
//        AlertMaker.showMaterialDialog(stackpane, anchor, new ArrayList(), "Insufficient Data", "Please enter data in all fields.");
//    }
    private void loadTime() {
        long endTime = 9999;
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curday = new SimpleDateFormat("MMMMM dd, yyyy").format(Calendar.getInstance().getTime());
        labelDate.setText(curday);
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        event -> {
                            final long diff = System.currentTimeMillis();
                            if (diff < 0) {
                                timeLabel.setText(timeFormat.format(0));

                            } else {
                                timeLabel.setText(timeFormat.format(diff));
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void init() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/EmployeeWindow/toolbar/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);

        } catch (IOException ex) {
            Logger.getLogger(EmployeeMainController.class.getName()).log(Level.SEVERE, null, ex);
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

}
