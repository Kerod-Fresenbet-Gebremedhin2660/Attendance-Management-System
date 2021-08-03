/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling;

import EntityModels.Employee;
import static databasehandling.EmployeeDB.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import EntityModels.Request;
import static databasehandling.SecurityDB.getConnection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Henok Gelaneh
 */
public class leaveHelper {

    public static void main(String[] args) throws Exception {
//        Request r = new Request(12,"henok", "funeral", 10);
//        addRequest(r);
        System.err.println(getAmount(11));
    }

    public static void addRequest(Request r) throws Exception {
        try {

            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("INSERT INTO leaverequests(id,name,daysrequested,reason) VALUES (?,?,?,?)");
            c.setInt(3, r.getDaysrequested());
            c.setString(2, r.getName());
            c.setInt(1, r.getId());
            c.setString(4, r.getReason());
            c.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Insertion Completed");
        }
    }
    
    public static int getAmount(int id){
        try { 
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT daysrequested FROM leaverequests WHERE id=?");
            int a = 0 ;
            sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a = res.getInt("daysrequested");
            }
            System.err.println("Select Success.");
            return a;
        
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static void removeRequest(int id) {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("DELETE FROM leaverequests WHERE id=?");
            c.setInt(1, id);
            c.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Deletetion Completed");
        }
    }

    public static boolean isPending(int id) {
        try { 
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT id FROM leaverequests WHERE id=?");
            ArrayList<String> a = new ArrayList<>();
            sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
            }
            System.err.println("Select Success.");
            if(a.isEmpty() || a.get(0)==null){
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
    
    public static boolean requestExist(int id) {
        try { 
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM leaverequests");
            ArrayList<String> a = new ArrayList<>();
            //sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
            }
            System.err.println("Select Success.");
            if(a.isEmpty() || a.get(0)==null){
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
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
