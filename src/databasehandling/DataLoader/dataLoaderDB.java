/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling.DataLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Henok Gelaneh
 */
public class dataLoaderDB {
    
    
    
    ObservableList<String> list = FXCollections.observableArrayList();
    public  ObservableList<String> getData() throws Exception {
        list.clear();
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM febraury");
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                String id= res.getString("id");
                String date = res.getString("date");
                String p = "YES";
                list.add(name);
                list.add(id);
                list.add(date);
                list.add(p);
            }

            //System.out.println(a);
            System.err.println("Select Sucess.");
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }


    public static void createTable() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement newTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS passcheck(id int NOT NULL AUTO_INCREMENT, first varchar(255), last varchar(255),PRIMARY KEY(id))");
            newTable.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Function Completed.");
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


}
