package pppdatadownloader.utils;

import pppdatadownloader.rinex.RinexHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pppdatadownloader.utils.Range.range;

public class DateConverter {
    private static long REFERENCE_TIME;

    /**
     * Default constructor used to initialize REFERENCE_TIME
     * @throws ParseException
     */

    public DateConverter() throws ParseException {
        REFERENCE_TIME = new SimpleDateFormat("yyyyMMdd")
                .parse("19800106")
                .getTime();
    }

    /**
     * Returns date from rinex string
     * @param element Date in specific format yyyyMMddhhmmss
     * @return Date of the parsed element
     * @throws ParseException
     */
    public static Date getDate(String element) throws ParseException{
        return new SimpleDateFormat("yyyyMMddhhmmss")
                .parse(validate(element));
    }

    /**
     * Get day number from the beginning of the year
     * @param date Date
     * @return Ordinal number of day from the beginning of the year
     * @throws ParseException
     */
    public static int getDayNumber(Date date) throws ParseException{
        String str = new SimpleDateFormat("D").format(date);
        return Integer.valueOf(str);
    }

    /**
     * Returns week number from 1980-01-06 and the day of the week
     * @param date Date
     * @return Week number from 1980-01-06 and the day of the week
     * @throws ParseException
     */
    public static int getWeekNumberAndDay(Date date) throws ParseException{
        long difference = date.getTime() - REFERENCE_TIME;
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        return (days / 7) + (days % 7 - 1);
    }

    /**
     * Returns week number from 1980-01-06
     * @param date Date
     * @return Week number from 1980-01-06
     * @throws ParseException
     */
    public static int getWeekNumber(Date date) throws ParseException{
        long difference = date.getTime() - REFERENCE_TIME;
        int days = (int)(difference/(24 * 60 * 60 * 1000));
        return (days / 7);
    }

    /**
     * Add zero if it absents
     * @param check String to check
     * @return double digit string as required for path searching purpose
     */
    private static String validate(String check){
        if (check.length() == 1) return 0 + check;
        else return check;
    }

    /**
     * Returns an array of observation days numbers
     * @param rinex Rinex header
     * @return Array of numbers as a range from first to last observation
     * @throws ParseException
     */
    public static int[] getDaysNumbers(RinexHeader rinex) throws ParseException{
        return range(getDayNumber(rinex.getLastObs()), getDayNumber(rinex.getFirstObs()));
    }

    /**
     * Returns an array of observation weeks numbers
     * @param rinex Rinex header
     * @return Ordinal number of days as a range from first to last observation
     * @throws ParseException
     */
    public static int[] getWeeksNumbers(RinexHeader rinex) throws ParseException{
        return range(getWeekNumberAndDay(rinex.getLastObs()), getWeekNumberAndDay(rinex.getFirstObs()));
    }

    /**
     *
     * @param rinex Rinex header
     * @return Count days from first to last observation
     * @throws ParseException
     */
    public static int getDaysCount(RinexHeader rinex) throws ParseException{
        return getDayNumber(rinex.getLastObs()) - getDayNumber(rinex.getFirstObs());
    }

    /**
     *
     * @param rinex Rinex header
     * @return Count weeks from first to last observation
     * @throws ParseException
     */
    public static int getWeeksCount(RinexHeader rinex) throws ParseException{
        return getWeekNumber(rinex.getLastObs()) - getWeekNumber(rinex.getFirstObs());
    }
}