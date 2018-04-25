package pppdatadownloader.utils;

public class Range {
    public static int[] range(int begin, int end){
        int[] values = new int[end - begin];

        for (int i = 0; i < begin - end; i++) {
            values[i] = begin++;
        }
        return values;
    }
}
