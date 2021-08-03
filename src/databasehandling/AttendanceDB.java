/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling;

import EntityModels.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import DateValidator.dateChecker;
import EntityModels.Calendar;
import static databasehandling.SecurityDB.getConnection;
import databasehandling.validator.validatorDB;
import static databasehandling.validator.validatorDB.getConnection;
import java.text.SimpleDateFormat;

/**
 *
 * @author Henok Gelaneh
 */
public class AttendanceDB {

    public static void main(String[] args) throws Exception {
        //punchIn(17);
        //punchOut(12);
        System.err.println((Calendar.pickMonth()));
//        PresentCheck(29, "february");
//        System.err.println(LateCheck(29, "february"));

    }

    public static void punchIn(int id) throws Exception {
        String month = Calendar.pickMonth();
        String date = Calendar.currentDay();
        String time = Calendar.currentTime();
        ArrayList<String> a = databaseHelper.nameId(id);
        Integer id1 = Integer.parseInt(a.get(0));

        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("UPDATE " + month + " SET punchin =? WHERE id =" + id + " AND date =?");
            c.setString(1, time);
            c.setString(2, date);
            c.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Update Sucess");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void punchOut(int id) throws Exception {
        String month = Calendar.pickMonth();
        String date = Calendar.currentDay();
        String time = Calendar.currentTime();
        ArrayList<String> a = databaseHelper.nameId(id);
        Integer id1 = Integer.parseInt(a.get(0));

        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("UPDATE " + month + " SET punchout=? WHERE id=? AND date=?");
            c.setInt(2, id);
            c.setString(1, time);
            c.setString(3, date);
            c.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Update Sucess");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static int PresentCheck(int id, String month) {

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT punchout FROM " + month + " WHERE id =?");
            sel.setInt(1, id);
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                if (res.getString("punchout") != null) {
                    a.add(res.getString("punchout"));
                }
            }
            return a.size();

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int LateCheck(int id, String month) throws Exception {
        if (validatorDB.isDone(id)) {
            try {
                Connection con = getConnection();
                PreparedStatement sel = con.prepareStatement("SELECT * FROM " + month + " WHERE id =?");
                sel.setInt(1, id);
                ArrayList<String> a = new ArrayList<>();
                ArrayList<String> b = new ArrayList<>();
                ResultSet res = sel.executeQuery();
                while (res.next()) {
                    if (res.getString("punchout") != null && res.getString("punchin") != null) {

                        String d = res.getString("punchin");
                        String[] split = d.split(":");
                        if (split[0].equals("09")) {
                            a.add(split[0]);
                        }

                    }

                }
                return a.size();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return 0;
    }

    public static int AbsentCheck(int id, String month) throws Exception {
        ArrayList<String> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        String curday = new SimpleDateFormat("dd").format(java.util.Calendar.getInstance().getTime());
        String curmon = new SimpleDateFormat("MM").format(java.util.Calendar.getInstance().getTime());
        int counter = 0;
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT punchout FROM " + month + " WHERE id =?");
            sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String d = res.getString("punchout");
                if (d == null) {
                    a.add(d);
                    counter += 1;
                }
            }
            int p = PresentCheck(id, month);
            
            int now = Integer.parseInt(curday);
            int ab = now - p;
            int absent = counter -(28-now);
            return ab;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;

    }

    public static boolean isDone(int id, String date) throws Exception {
        String month = Calendar.pickMonth();
        //String date = Calendar.currentDay();
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT punchout FROM " + month + " WHERE id = ? AND date =?");
            sel.setInt(1, id);
            sel.setString(2, date);
            ArrayList<String> b = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                b.add(res.getString("punchout"));
            }
            System.err.println(b);
            System.err.println("Select Success.");
            if (b.isEmpty() || b.get(0) == null) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
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
