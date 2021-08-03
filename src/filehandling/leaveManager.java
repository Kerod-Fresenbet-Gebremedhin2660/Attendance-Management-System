/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filehandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import DateValidator.dateChecker;
import static databasehandling.SecurityDB.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import databasehandling.databaseHelper;
import java.io.IOException;

/**
 *
 * @author Henok Gelaneh
 */
public class leaveManager {

    public static void main(String[] args) throws IOException {
        //reduceDays(11,2);
        System.err.println(checkLeave(11));
        //approveLeave(45, "Yes", 4);
       // removedata(56);
        //checkLeave(11);

    }

    public static void removedata(int id) {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("DELETE FROM leaverequests WHERE id=?");
            sel.setInt(1, id);
            sel.executeUpdate();
            System.err.println("Delete Sucess.");

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public static boolean canRequest(int id, Integer amount) {

        int daysLeft = 0;

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT leavedays FROM employee WHERE id=?");
            sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                daysLeft = res.getInt("leavedays");
            }
            System.err.println("Select Sucess.");

        } catch (Exception e) {
            System.out.println(e);
        }
        if (daysLeft >= amount) {
            return true;
        } else {
            return false;
        }
    }

    public static Integer daysLeft(int id) {
        int daysLeft = 0;

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT leavedays FROM employee WHERE id=?");
            sel.setInt(1, id);
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                daysLeft = res.getInt("leavedays");
            }
            System.err.println("Select Sucess.");

        } catch (Exception e) {
            System.out.println(e);
        }
        return daysLeft;
    }

    public static boolean reduceDays(int id, int amount) {

        String file = databaseHelper.returnFile("onLeave");

        if (canRequest(id, amount)) {

            try {
                Connection con = getConnection();
                int daysLeft = daysLeft(id);
                daysLeft = daysLeft - amount;
                PreparedStatement c = con.prepareStatement("UPDATE employee SET leavedays=? WHERE id=?");
                c.setInt(2, id);
                c.setInt(1, daysLeft);
                c.executeUpdate();
                JOptionPane.showMessageDialog(null, "Update Sucess");
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            return false;
        }
        return false;
    }

    public static boolean approveLeave(int ID, Integer amount) throws IOException {
        dateChecker checker = new dateChecker();
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        Calendar cal = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int day = Integer.parseInt(checker.yyyyMMdd("dd"));
        String file = databaseHelper.returnFile("leave");

        
            if (!checkLeave(ID)) {

                for (int i = 0, j = 1; i < amount; i++) {
                    cal.add(Calendar.DAY_OF_MONTH, j);
                    String time1 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.newLine();
                    bw.write(ID + " " + time1);
                    bw.close();
                    fw.close();

                }
                reduceDays(ID, amount);
                return true;
            }
        

        return false;

    }

    public static boolean checkLeave(int id) {
        String id1 = Integer.toString(id);
        dateChecker checker = new dateChecker();
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String file = databaseHelper.returnFile("leave");
        int day = Integer.parseInt(checker.yyyyMMdd("dd"));
        String curdate = new SimpleDateFormat("yyyy/MM/" + (day)).format(Calendar.getInstance().getTime());
        System.err.println(curdate);
        ArrayList<String> n = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNext()) {
                String str = sc.nextLine();
                n.add(str);
            }
            System.err.println(n);
            sc.close();
            for (String p : n) {
                String u[] = p.split(" ");

                if (u[0].equals(id1)) {
                    if (u[1].equals(curdate)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        return false;
    }

    public static Integer toReturn(int id) {
        dateChecker checker = new dateChecker();
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int day = Integer.parseInt(dateChecker.yyyyMMdd("dd"));
        int month = Integer.parseInt(dateChecker.yyyyMMdd("MM"));
        String file = databaseHelper.returnFile("leave");
        ArrayList<String> n = new ArrayList<>();
        int Counter = 0;
        String id1 = Integer.toString(id);
        try {
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNext()) {
                String str = sc.nextLine();
                n.add(str);
            }
            sc.close();
            for (String p : n) {
                String u[] = p.split(" ");
                //System.err.println(u[0]);
                if (u[0].equals(id1)) {
                    System.err.println(u[1]);
                    String[] v = u[1].split("/");
                    int month2 = Integer.parseInt(v[0]);
                    int day2 = Integer.parseInt(v[1]);
                    if (month == month2) {
                        if (day < day2) {
                            Counter = Counter + 1;
                        }
                    } else if (month2 > month) {
                        if (day > day2) {
                            Counter = Counter + 1;
                        }
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(leaveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Counter;
    }
    
    
}
