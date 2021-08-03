/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DateValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author The Java Artists
 */
public class dateChecker {

    public static boolean dayCheck(String day) {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curday = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        if (!curday.equals(day)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean monthCheck(String month) {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curmonth = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
        if (curmonth.equals(month)) {
            return true;
        } else {
            return false;
        }
    }

    public static String extractor(Integer timein, String ID) {
        ArrayList<String> n = new ArrayList<>();
        Date date1 = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date1);
        String curday = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        
        
        try {
            Scanner sc = new Scanner(new File("punchindate.txt"));
            while (sc.hasNext()) {
                String str = sc.nextLine();
                n.add(str);
            }
            sc.close();
            for (String p : n) {
                String u[] = p.split(" ");
                if (u[0].equals(ID) && u[2].equals(curday)) {
                    String date = u[2];
                    String datesplit[] = date.split("/");
                    return datesplit[timein];
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        return "-1";
    }

    public static String yyyyMMdd(String choice) {
        Date date1 = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date1);
        String date = "";
        if (choice.equals("yyyy")) {
            date = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        } else if (choice.equals("MM")) {
            date = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        } else if (choice.equals("dd")) {
            date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        } else if (choice.equals("all")) {
            date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        } else if (choice.equals("HH")) {
            date = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        }
        
        return date;
    }
    public static Integer convert(String month){
        switch(month){
            case "january":
                return 01;
            case "february":
                return 02;
            case "march":
                return 03;
            case "april":
                return 04;
            case "may":
                return 05;
            case "june":
                return 06;
        }
        return 00;
    }
}
