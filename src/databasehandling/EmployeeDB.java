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
import EntityModels.Employee;
import java.time.LocalDate;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import DateValidator.dateChecker;
import databasehandling.SecurityDB;

/**
 *
 * @author Henok Gelaneh
 */
public class EmployeeDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //createTable();
        //insert("Amanuel","Teferra");
        //select();
        Employee e = new Employee(29, "Harry", "0987562700", "Harry@gmail.com", "Male", 30, "C:\\Users\\Henok Gelaneh\\Documents\\NetBeansProjects\\DatabaseHandling\\src\\databasehandling");
        addEmployee(e);
//updateEmployee(10,"James", "0985555555", "hh@gmail.com", "Female");
        //deleteEmployee(10);
        // updateLeaveStatus(11);
        //selectUser(11);

    }

    public static void deleteEmployee(int id) {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("DELETE FROM employee WHERE id=?");
            c.setInt(1, id);
            c.executeUpdate();
            JOptionPane.showMessageDialog(null, "Delete Success!");
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    static ArrayList<Integer> idid = new ArrayList<>();
    
    
    public static void saveid(int id){
        idid.add(id);
        System.err.println(idid);
    }
    public static void deleteid(){
        idid.clear();
    }
    public static int loadid(){
        return idid.get(0);
    }

    public static void updateEmployee(Employee e) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("UPDATE employee SET id =?,name= ? ,mobile= ? ,email=?,sex=?, profilepic=?  WHERE id=?");
            c.setString(3, e.getMobile());
            c.setString(2, e.getName());
            c.setInt(1, e.getId());
            c.setString(4, e.getEmail());
            c.setString(5, e.getSex());
            c.setInt(7, e.getId());
            c.setString(6, e.getProfilepic());
            c.executeUpdate();
            JOptionPane.showMessageDialog(null, "Update Sucess");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void updateLeaveStatus(int id) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("UPDATE employee SET onleave=?  WHERE id=?");
            c.setString(1, "YES");
            c.setInt(2, id);
            c.executeUpdate();
            JOptionPane.showMessageDialog(null, "Update Sucess");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static ArrayList<String> selectUser(int id) throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement sel = con.prepareStatement("SELECT * FROM employee WHERE id =?");
            sel.setInt(1, id);
            ArrayList<String> a = new ArrayList<>();
            ResultSet res = sel.executeQuery();
            while (res.next()) {
                a.add(res.getString("id"));
                a.add(res.getString("name"));
                a.add(res.getString("mobile"));
                a.add(res.getString("email"));
                a.add(res.getString("profilepic"));
            }
            System.err.println("Select Sucess.");
            return a;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void addEmployee(Employee e) throws Exception {
        String a[] = {"january", "february", "march", "april", "may", "june"};
        try {
            Connection con = getConnection();
            PreparedStatement c = con.prepareStatement("INSERT INTO employee(id,name,mobile,email,sex,leavedays,profilepic) VALUES (?,?,?,?,?,?,?)");
            c.setString(3, e.getMobile());
            c.setString(2, e.getName());
            c.setInt(1, e.getId());
            c.setString(4, e.getEmail());
            c.setString(5, e.getSex());
            c.setString(7, e.getProfilepic());
            c.setInt(6, e.getLeavedays());
            c.executeUpdate();
            addEmployeeData(e);
            SecurityDB.addPassword(e.getId(), e.getName(), "user");

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Insertion Completed");
        }
    }

    public static Object[] overDays(int month) throws NullPointerException {
        ArrayList<String> a = null;
        LocalDate start = LocalDate.of(2019, month, 1);
        LocalDate end = LocalDate.of(2019, month, 28);
        Stream<LocalDate> stream = LongStream.range(start.toEpochDay(), end.toEpochDay() + 1).mapToObj(LocalDate::ofEpochDay);
        Object[] toArray = stream.toArray();
        return toArray;
    }

    public static void addEmployeeData(Employee e) throws Exception {
        String a[] = {"january", "february", "march", "april", "may", "june"};
        Connection con = getConnection();
        for (int j = 0; j < a.length; j++) {
            try {
                Object[] s = overDays(dateChecker.convert(a[j]));
                for (int i = 0; i < s.length; i++) {
                    PreparedStatement c = con.prepareStatement("INSERT INTO "+ a[j] +"(id,name,date) VALUES (?,?,?)");
                    c.setString(2, e.getName());
                    c.setInt(1, e.getId());
                    c.setString(3, s[i].toString());
                    c.executeUpdate();
                }
                PreparedStatement d = con.prepareStatement("INSERT INTO daysattended(id,month,present,absent,late) VALUES (?,?,?,?,?)");
                d.setInt(3, 0);
                d.setString(2, a[j]);
                d.setInt(1, e.getId());
                d.setInt(4, 0);
                d.setInt(5, 0);
                d.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                System.out.println("Insertion Completed");
            }
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
