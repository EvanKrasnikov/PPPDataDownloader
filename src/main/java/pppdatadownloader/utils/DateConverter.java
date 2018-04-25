package pppdatadownloader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static final String REFERENCE_DATE  = "19800106";

    private static Date getDate(String data) throws ParseException{
        return new SimpleDateFormat("yyyyMMdd").parse(data);
    }

    public static Date getDate(String[] elements){
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();

        for (String element: elements){
            element = validate(element);
            stringBuilder.append(element);
        }

        try {
            date = new SimpleDateFormat("yyyyMMddhhmmss").parse(stringBuilder.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //get day number from the beginning of the year
    public static int getDayNumber(Date date) throws ParseException{
        String str = new SimpleDateFormat("D").format(date);
        return Integer.valueOf(str);
    }

    //returns week number from 1980-01-01 and the day of the week
    public static int getWeekNumberAndDay(Date date) throws ParseException{
        long difference = date.getTime() - getDate(REFERENCE_DATE).getTime();
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        return (days / 7) + (days % 7 - 1);
    }

    private static String validate(String check){
        if (check.length() == 1) return 0 + check;
        else return check;
    }
}