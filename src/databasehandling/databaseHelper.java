/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling;

import EntityModels.Employee;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Henok Gelaneh
 */
public class databaseHelper {
    
    public static void main(String[] args) throws Exception {
//        if (itExists(11)){
//            System.err.println("Found");
//        }
//        else{
//            System.err.println("Not Found");
//        }
        //System.err.println(returnFile("news"));
        nameId(11);
        
    }

    public static ArrayList<String> nameId(int id) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM employee WHERE id = ?");
            sel.setInt(1, id);
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
                a.add(res.getString("name"));
            }

            System.out.println(a);
            System.err.println("Select Success.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static boolean isReachable() throws IOException{
        
        boolean r = false;
        try{
            Socket s = new Socket("localhost",3306);
            r = true;
            
        } 
           catch(IOException e){
                System.err.println(e);
            }
     
        return r;
    }
    
    
    //check if the id already exist
    public static boolean itExists(int id) throws Exception{
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM employee WHERE id = ?");
            sel.setInt(1, id);
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
                a.add(res.getString("name"));
            }
            System.err.println("Select Success.");
            if(a.isEmpty()){
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
    
    public static String returnFile(String id){
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT path FROM filepath WHERE id = ?");
            sel.setString(1, id);
            String a = null;
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a = res.getString("path");
            }
            System.err.println("Select Success.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static void insertCur(int id){
        try {
            Connection con = getConnection();
            PreparedStatement newTable = con.prepareStatement("INSERT INTO loggedin VALUES(?)");
            newTable.setInt(1, id);
            newTable.executeUpdate();
            System.err.println("Insert Complete");
        } catch (Exception e) {
            System.out.println(e);
        } 
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


