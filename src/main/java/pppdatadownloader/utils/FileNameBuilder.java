package pppdatadownloader.utils;

import pppdatadownloader.rinex.RinexHeader;

import java.util.ArrayList;
import java.util.List;

import static pppdatadownloader.utils.DateConverter.getDaysCount;
import static pppdatadownloader.utils.DateConverter.getWeekNumber;
import static pppdatadownloader.utils.DateConverter.getWeekNumberAndDay;

public class FileNameBuilder {
    private List<String> names = new ArrayList<>();

    //returns list of filenames for each selected rinex
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
