package pppdatadownloader.utils;

import pppdatadownloader.rinex.RinexHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pppdatadownloader.utils.Range.range;

public class DateConverter {
    private static long REFERENCE_TIME;

    public DateConverter() throws ParseException {
        REFERENCE_TIME = new SimpleDateFormat("yyyyMMdd")
                .parse("19800106")
                .getTime();
    }

    //returns date from rinex string
    public static Date getDate(String element) throws ParseException{
        return new SimpleDateFormat("yyyyMMddhhmmss")
                .parse(validate(element));
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

    //add zero if it absents
    private static String validate(String check){
        if (check.length() == 1) return 0 + check;
        else return check;
    }

    //returns an array of observation days numbers
    public static int[] getDaysNumbers(RinexHeader rinex) throws ParseException{
        return range(getDayNumber(rinex.getLastObs()), getDayNumber(rinex.getFirstObs()));
    }

    //returns an array of observation weeks numbers
    public static int[] getWeeksNumbers(RinexHeader rinex) throws ParseException{
        return range(getWeekNumberAndDay(rinex.getLastObs()), getWeekNumberAndDay(rinex.getFirstObs()));
    }

    //returns how much days count from first obs
    public static int getDaysCount(RinexHeader rinex) throws ParseException{
        return getDayNumber(rinex.getLastObs()) - getDayNumber(rinex.getFirstObs());
    }

    //returns how much weeks count from first obs
    public static int getWeeksCount(RinexHeader rinex) throws ParseException{
        return getWeekNumber(rinex.getLastObs()) - getWeekNumber(rinex.getFirstObs());
    }
}