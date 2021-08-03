/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling;

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



public class SecurityDB {
    
    
    
    
    
    public static void main(String[] args) throws Exception {
        //addPassword(66, "hell", "admin");
        //addPassword(45, "heaven", "user");
        //addPassword(99, "earth", "janitor");
        selectID();
        selectPassword();
        selectType();
    }
    
    
    
    public static void deletePassword(int id) {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("DELETE FROM login WHERE id=?");
            c.setInt(1, id);
            c.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sucess!");
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void updatePassword(int id,String password, String type) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("UPDATE login SET id =?,password= ? ,type= ?   WHERE id=?");
            c.setString(3, type);
            c.setString(2, password);
            c.setInt(1, id);
            c.setInt(4, id);
            c.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static ArrayList<String> selectID() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT id FROM login");
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
            }

            System.out.println(a);
            System.err.println("Select Sucess.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static ArrayList<String> selectPassword() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT password FROM login");
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("password"));
            }

            System.out.println(a);
            System.err.println("Select Sucess.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static ArrayList<String> selectType() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT type FROM login");
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("type"));
            }

            System.out.println(a);
            System.err.println("Select Sucess.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void addPassword(int id, String password, String type) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement updated = con.prepareStatement("INSERT INTO login(id,password,type) VALUES ('" 
                    + id + "','" + password + "','" +type+ "')");
            updated.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Insertion Completed");
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
