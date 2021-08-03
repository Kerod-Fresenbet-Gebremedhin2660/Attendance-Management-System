/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filehandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import databasehandling.databaseHelper;
/**
 *
 * @author Henok Gelaneh
 */
public class fileeditor {
    
    public static void main(String[] args) {
        
    }
    
    public static boolean postNews(String title, String s){
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curdate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Scanner in = new Scanner(System.in);
        
        String a = databaseHelper.returnFile("news");
        

        try {
            FileWriter fw = new FileWriter(a,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(title+"xxx"+curdate+"xxx"+s);
            bw.newLine();
            bw.close();
            System.out.println("PUBLISHED. ");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(fileeditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
