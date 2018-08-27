package pppdatadownloader.utils;

import pppdatadownloader.rinex.RinexHeader;

import java.util.LinkedList;
import java.util.List;

import static pppdatadownloader.utils.DateConverter.*;

public class FileNameBuilder {
    private static List<String> names = new LinkedList<>();

    public static List<String> getNames() {
        return names;
    }

    /**
     * @param rinexes List of RINEX Headers, whose contain dates of first and last observation
     * @return list of paths for downloading precise ephemeresis
     * @throws Exception
     */
    public FileNameBuilder addPreciseEphemeresis(List<RinexHeader> rinexes) throws Exception{
        for(RinexHeader rinex: rinexes){
            int weeks = getWeekNumber(rinex.getFirstObs());
            int days = getWeekNumberAndDay(rinex.getFirstObs());

            for (int i = 0; i < getDaysCount(rinex) ; i++) {
                if (days > 7){
                    days = days - 7;
                    weeks++;
                }
                names.add("/pub/products/" + weeks + "/igr" + days + ".sp3.Z");
            }
        }
        return this;
    }
}
