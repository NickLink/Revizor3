package tv.novy.revizor2.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by NickNb on 15.09.2016.
 */
public class HelperClasses {

    public static String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            Date netDate = (new Date(timeStamp * 1000));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    public static boolean notNull_orEmpty(String in){
        if(in!=null && !in.trim().isEmpty() && !in.equals("null"))
            return true;
        else
            return false;
    }
}
