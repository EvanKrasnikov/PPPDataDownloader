package pppdatadownloader.rinex;

import java.util.Date;

public class RinexHeader {
    private Date firstObs;
    private Date lastObs;

    public Date getFirstObs() {
        return firstObs;
    }

    public void setFirstObs(Date firstObs) {
        this.firstObs = firstObs;
    }

    public Date getLastObs() {
        return lastObs;
    }

    public void setLastObs(Date lastObs) {
        this.lastObs = lastObs;
    }

    @Override
    public String toString() {
        return " " + firstObs + " " + lastObs ;
    }
}
