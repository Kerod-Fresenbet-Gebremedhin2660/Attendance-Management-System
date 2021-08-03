/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import databasehandling.databaseHelper;
import Game.GameFrame;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henok Gelaneh
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;

    @FXML
    private JFXTextField newsText;
    @FXML
    private JFXButton login;

    @FXML
    private JFXButton AboutUs;
    
    
    
    @FXML
    void aboutUsAction(ActionEvent event) {
        
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        if (!databaseHelper.isReachable()) {
            new GameFrame().setVisible(true);
        } else {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/UI/login/login.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Login");
                stage.setScene(new Scene(parent));
                stage.show();
                //.setStageIcon(stage);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }
    
    public void displayNews(){
        String file = databaseHelper.returnFile("news");
        ArrayList<String> n = new ArrayList<>();
            try {
                Scanner sc = new Scanner(new File(file));
                while(sc.hasNext()){
                    String str = sc.nextLine();
                    if(!"".equals(str)){
                        n.add(str);
                    }
                }
                sc.close();
                for (String p: n){
                    String[] u = p.split("xxx");
                    newsText.setText("------------------------------------------------------------");
                    newsText.setText("TITLE: "+ u[0]+"\n");
                    newsText.setText("DATE POSTED: "+ u[1]+"\n");
                    StringBuilder sb = new StringBuilder(u[2]);
                    int i =0;
                    while((i = sb.indexOf(" ",i+60))!=-1){
                        sb.replace(i, i+1, "\n");
                    }
                    String a3 = sb.toString();
                    newsText.setText("------------------------------------------------------------\n"
                            + "                        >>> NEWS <<<                        \n"
                            + "------------------------------------------------------------\n"
                            + "=============================================================\n"
                            + a3);
                    
                    }
               

                        } catch (FileNotFoundException ex) {
                System.err.println(ex);
            }
    
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = new File("src/newsfx/[MP4 1080p] Major Lazer - Cold Water (Dance Video) ft. Justin Bieber, MØ.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        //mp.setAutoPlay(true);
        DoubleProperty w = mv.fitWidthProperty();
        DoubleProperty h = mv.fitHeightProperty();
        w.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        h.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        displayNews();
    }

    public void playAction(ActionEvent event) {
        mp.play();
    }

    public void pauseAction(ActionEvent event) {
        mp.pause();
    }

    public void reloadAction(ActionEvent event) {
        mp.seek(mp.getStartTime());
    }

}
