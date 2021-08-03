/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling.validator;

import EntityModels.Calendar;
import static databasehandling.AttendanceDB.getConnection;
import static databasehandling.AttendanceDB.punchOut;
import databasehandling.databaseHelper;
import static databasehandling.databaseHelper.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import DateValidator.dateChecker;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import DateValidator.dateChecker;
import EntityModels.Employee;
import static databasehandling.EmployeeDB.getConnection;
import static databasehandling.EmployeeDB.overDays;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import databasehandling.AttendanceDB;

/**
 *
 * @author Henok Gelaneh
 */
public class validatorDB {

    public static void main(String[] args) throws Exception {
        
//        if (canPin(12)) {
//            System.err.println("Is Done");
//        } else {
//            System.out.println("Not Yet");
//        }
        //System.err.println(canPout(13));
//        Object[] a = overDays(2);
//        for (int i=0; i<10;i++){
//            System.err.println(a[i]);
//        }

//       
//        ArrayList<String> a = isAbsent("february");
//        ArrayList<String> b = new ArrayList<>();
//        ArrayList<ArrayList> c = new ArrayList<>();
//        
//        for (int i=0; i<a.size();i++){
//            for (int j =0; i<4;i++){
//                b.add(a.get(i));
//                b.add(a.get(i+1));
//                b.add(a.get(i+2));
//                b.add(a.get(i+3));
//                c.add(b);
//                b.clear();
//            }
//            System.err.println(b);
//        }
        // System.err.println(isAbsent("february"));
        System.out.println(isDone(29));

    }

    public static boolean PinTime() {
        int hour = Integer.parseInt(dateChecker.yyyyMMdd("HH"));
        if (hour >= 8 && hour <= 9) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean PoutTime() {
        int hour = Integer.parseInt(dateChecker.yyyyMMdd("HH"));
        if (hour >= 17 && hour <= 17) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean canPin(int id) throws Exception {
        String month = Calendar.pickMonth();
        System.err.println(month);
        String date = Calendar.currentDay();
        int hour = Integer.parseInt(dateChecker.yyyyMMdd("HH"));

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT punchin FROM " + month + " WHERE id = ? AND date =?");
            sel.setInt(1, id);
            sel.setString(2, date);
            ArrayList<String> b = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                b.add(res.getString("punchin"));
            }
            System.err.println(b);
            System.err.println("Select Success.");
            if (b.isEmpty() || b.get(0) == null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public static boolean canPout(int id) throws Exception {
        String month = Calendar.pickMonth();
        String date = Calendar.currentDay();

        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT punchin FROM " + month + " WHERE id = ? AND date =?");
            sel.setInt(1, id);
            sel.setString(2, date);
            ArrayList<String> b = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                b.add(res.getString("punchin"));
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

    public static void addAttendance(int id) throws Exception {
        String month = Calendar.pickMonth();
        String a[] = {"january", "february", "march", "april", "may", "june"};
        Connection con = getConnection();
//        for (int j = 0; j < a.length; j++) {
        try {
            Object[] s = overDays(dateChecker.convert(month));
            PreparedStatement d = con.prepareStatement("UPDATE daysattended SET present =?, absent=?, late=? WHERE id=? and month=?");
            d.setString(5, month);
            d.setInt(4, id);
            d.setInt(1, AttendanceDB.PresentCheck(id, month));
            d.setInt(2, AttendanceDB.AbsentCheck(id, month));
            d.setInt(3, AttendanceDB.LateCheck(id, month));

            d.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Insertion Completed");
        }

    }

    public static boolean isPresent(String month) {
        String present;
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM " + month);
            ArrayList<String[]> b = new ArrayList<>();
            String[] a = new String[4];
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                String id = res.getString("id");
                String date = res.getString("date");
                if (res.getString("punchout") == null) {
                    present = "NO";
                } else {
                    present = "YES";
                }
                a[0] = name;
                a[1] = id;
                a[2] = date;
                a[3] = present;
                b.add(a);

            }

            System.err.println("Select Success.");
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public static ArrayList isAbsent(String month) {
        String present;
        int mon = dateChecker.convert(month);
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM " + month);
            Object[] obj = overDays(mon);
            ArrayList<String> b = new ArrayList<>();
            ArrayList<String> a = new ArrayList<>();
            ArrayList<String> c = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                String name = res.getString("name");
                String id = res.getString("id");
                String date = res.getString("date");
                if (res.getString("punchout") == null) {
                    present = "NO";
                } else {
                    present = "YES";
                }
                a.add(name);
                a.add(id);
                a.add(date);
                a.add(present);
                b.addAll(a);
                for (int i = 0; i < obj.length; i++) {
                    if (!obj[i].toString().equals(date)) {
                        c.add(name);
                        c.add(id);
                        c.add(obj[i].toString());
                        c.add("NO");
                        b.addAll(c);
                        c.clear();
                    }
                }
                a.clear();
            }
            return b;
            //System.err.println(b);
            //System.err.println("Select Success.");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static Object[] overDays(int month) throws NullPointerException {
        ArrayList<String> a = null;
        LocalDate start = LocalDate.of(2019, month, 1);
        LocalDate end = LocalDate.of(2019, month, 28);
        Stream<LocalDate> stream = LongStream.range(start.toEpochDay(), end.toEpochDay() + 1).mapToObj(LocalDate::ofEpochDay);
        Object[] toArray = stream.toArray();
        return toArray;
    }

    public static boolean isDone(int id) throws Exception {
        String month = Calendar.pickMonth();
        String date = Calendar.currentDay();
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
