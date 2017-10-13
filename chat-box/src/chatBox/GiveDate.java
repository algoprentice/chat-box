/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Swapnil Jain - Refactored.
 */
public class GiveDate {
    static DateFormat dateFormat;
        
    static {
        dateFormat = new SimpleDateFormat("HH:mm:ss");
    }
    
    //Return Current Time.
    public static String now() {
        Date date = new Date();
        return dateFormat.format(date);
    }   
}
