package pppdatadownloader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static final String REFERENCE_DATE  = "19800101";

    private static Date getDate(String data) throws ParseException{
        return new SimpleDateFormat("yyyyMMdd").parse(data);
    }

    //get day number from the beginning of the year
    public static String getDayNumber(String data) throws ParseException{
        return new SimpleDateFormat("D").format(getDate(data));
    }

    //returns week number from 1980-01-01 and the day of the week
    public static String getWeekNumberAndDay(String data) throws ParseException{
        long difference = getDate(data).getTime() - getDate(REFERENCE_DATE).getTime();
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        StringBuilder sb = new StringBuilder();
        sb.append(days / 7);
        sb.append(days % 7);
        return sb.toString();
    }
}