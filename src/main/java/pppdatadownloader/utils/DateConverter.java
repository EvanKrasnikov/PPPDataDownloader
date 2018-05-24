package pppdatadownloader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateConverter {
    private static long REFERENCE_TIME;

    public DateConverter() throws ParseException {
        REFERENCE_TIME = new SimpleDateFormat("yyyyMMdd")
                .parse("19800106")
                .getTime();
    }

    public static List<Date> getDates(List<String> elements) throws ParseException{
        List<Date> result = new ArrayList<>();
        for (String element: elements){
            element = validate(element);
            result.add(new SimpleDateFormat("yyyyMMddhhmmss")
                    .parse(element));
        }
        return result;
    }

    //get day number from the beginning of the year
    public static int getDayNumber(Date date) throws ParseException{
        String str = new SimpleDateFormat("D").format(date);
        return Integer.valueOf(str);
    }

    //returns week number from 1980-01-06 and the day of the week
    public static int getWeekNumberAndDay(Date date) throws ParseException{
        long difference = date.getTime() - REFERENCE_TIME;
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        return (days / 7) + (days % 7 - 1);
    }

    //returns week number from 1980-01-06
    public static int getWeekNumber(Date date) throws ParseException{
        long difference = date.getTime() - REFERENCE_TIME;
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        return (days / 7);
    }

    private static String validate(String check){
        if (check.length() == 1) return 0 + check;
        else return check;
    }
}