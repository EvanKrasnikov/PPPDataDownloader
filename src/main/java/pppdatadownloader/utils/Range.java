package pppdatadownloader.utils;

public class Range {

    /**
     *
     * @param begin First value
     * @param end Second value
     * @return Array of values between two arguments
     */

    public static int[] range(int begin, int end){

        if (begin > end){
            int temp = end;
            end = begin;
            begin = temp;
        }

        int[] values = new int[end - begin];

        for (int i = 0; i < end - begin; i++) {
            values[i] = begin++;
        }
        return values;
    }
}
