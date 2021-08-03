package Chat.Client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ClientController implements Initializable {

    @FXML
    private JFXTextField tf_chat;

    @FXML
    private JFXButton b_send;

    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_port;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField lb_passowrd;

    @FXML
    private JFXButton b_login;

    @FXML
    private JFXButton b_connect;

    @FXML
    private JFXButton b_disconnect;

    @FXML
    private JFXTextArea ta_chat;

    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        users.add(data);
    }

    public void userRemove(String data) {
        ta_chat.appendText(data + " is now offline.\n");
    }

    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token : tempList) {
            //users.append(token + "\n");
        }
    }

    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            ta_chat.appendText("Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {
        try {
            ta_chat.appendText("Disconnected.\n");
            sock.close();
        } catch (Exception ex) {
            ta_chat.appendText("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);

    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");

                    if (data[2].equals(chat)) {
                        ta_chat.appendText(data[0] + ": " + data[1] + "\n");
                        //ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    } else if (data[2].equals(connect)) {
                        ta_chat.redo();
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        userRemove(data[0]);
                    } else if (data[2].equals(done)) {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    @FXML
    void connectAction(ActionEvent event) {
        if (isConnected == false) {
            username = tf_username.getText();
            tf_username.setEditable(false);

            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            } catch (Exception ex) {
                ta_chat.appendText("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

            ListenThread();

        } else if (isConnected == true) {
            ta_chat.appendText("You are already connected. \n");
        }
    }

    @FXML
    void disconnectAction(ActionEvent event) {
        sendDisconnect();
        Disconnect();
    }

    @FXML
    void loginAction(ActionEvent event) {
        tf_username.setText("");
        if (isConnected == false) {
            String anon = "user";
            Random generator = new Random();
            int i = generator.nextInt(999) + 1;
            String is = String.valueOf(i);
            anon = anon.concat(is);
            username = anon;

            tf_username.setText(anon);
            tf_username.setEditable(false);

            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            } catch (Exception ex) {
                ta_chat.appendText("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

            ListenThread();

        } else if (isConnected == true) {
            ta_chat.appendText("You are already connected. \n");
        }
    }

    @FXML
    void sendAction(ActionEvent event) {
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.appendText("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }
   

}
