/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat.Server;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class ServerController implements Initializable {

    @FXML
    private JFXTextArea ta_chat;

    @FXML
    private JFXButton start;

    @FXML
    private Button end;

    @FXML
    private Button online;

    @FXML
    private Button clear;

    ArrayList clientOutputStreams;
    ArrayList<String> users;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public class ClientHandler implements Runnable {

        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ta_chat.appendText("Unexpected error... \n");
            }

        }

        @Override
        public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    ta_chat.appendText("Received: " + message + "\n");
                    data = message.split(":");

                    for (String token : data) {
                        ta_chat.appendText(token + "\n");
                    }

                    if (data[2].equals(connect)) {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } else if (data[2].equals(chat)) {
                        tellEveryone(message);
                    } else {
                        ta_chat.appendText("No Conditions were met. \n");
                    }
                }
            } catch (Exception ex) {
                ta_chat.appendText("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }

    @FXML
    void clearAction(ActionEvent event) {
        ta_chat.setText("");
    }

    @FXML
    void endAction(ActionEvent event) {
        try {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.appendText("Server stopping... \n");

        ta_chat.setText("");
    }

    @FXML
    void onlineAction(ActionEvent event) {
        ta_chat.appendText("\n Online users : \n");
        for (String current_user : users) {
            ta_chat.appendText(current_user);
            ta_chat.appendText("\n");
        }
    }

    @FXML
    void startAction(ActionEvent event
    ) {
        Thread starter = new Thread(new ServerStart());
        starter.start();

        ta_chat.appendText("Server started...\n");
    }

    public class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();
            try {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    ta_chat.appendText("Got a connection. \n");
                }
            } catch (Exception ex) {
                ta_chat.appendText("Error making a connection. \n");
            }
        }
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.appendText("Before " + name + " added. \n");
        users.add(name);
        ta_chat.appendText("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                ta_chat.appendText("Sending: " + message + "\n");
                writer.flush();
                //ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } catch (Exception ex) {
                ta_chat.appendText("Error telling everyone. \n");
            }
        }
    }
}
