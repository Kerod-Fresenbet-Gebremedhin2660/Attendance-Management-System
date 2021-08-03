/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Henok Gelaneh
 */
public class regex {

public static boolean regexemail(String a){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(a);
        return (m.matches());
    }
    public static boolean regexid(String a){
        String regex = "[0-9][0-9]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(a);
        return (m.matches());
    }
    public static boolean regexnum(String a){
        String regex = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(a);
        return (m.matches());
    }
    public static boolean regexstr(String a){
        String regex = "^[a-zA-Z]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(a);
        return (m.matches());
    }

}
