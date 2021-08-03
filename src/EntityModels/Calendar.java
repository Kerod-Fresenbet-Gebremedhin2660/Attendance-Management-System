/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityModels;

import DateValidator.dateChecker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Henok Gelaneh
 */
public class Calendar {
    
    public static void main(String[] args) {
        
    }

    public static String pickMonth() {
        String file = "";
        dateChecker n = new dateChecker();
        String d = n.yyyyMMdd("MM");
        if (null != d) {
            switch (d) {
                case "01":
                    file = "january";
                    break;
                case "02":
                    file = "february";
                    break;
                case "03":
                    file = "march";
                    break;
                case "04":
                    file = "april";
                    break;
                case "05":
                    file = "may";
                    break;
                case "06":
                    file = "june";
                    break;
                case "07":
                    file = "july";
                    break;
                case "08":
                    file = "august";
                    break;
                case "09":
                    file = "september";
                    break;
                case "10":
                    file = "october";
                    break;
                case "11":
                    file = "november";
                    break;
                case "12":
                    file = "december";
                    break;
                default:
                    break;
            }
        }

        return file;
    }

    public static String currentDay() {
        Date date = new Date();
        java.util.Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curdate = new SimpleDateFormat("yyyy-MM-dd").format(java.util.Calendar.getInstance().getTime());
        return curdate;
    }
    
    public static String currentTime() {
        Date date = new Date();
        java.util.Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String curtime = new SimpleDateFormat("HH:mm:ss").format(java.util.Calendar.getInstance().getTime());
        return curtime;
    }
}
