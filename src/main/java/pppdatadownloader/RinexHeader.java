package pppdatadownloader;

public class RinexHeader {
    private int fyy;
    private int fmm;
    private int fdd;
    private int lyy;
    private int lmm;
    private int ldd;

    public void setFyy(int fyy) {
        this.fyy = fyy;
    }

    public void setFmm(int fmm) {
        this.fmm = fmm;
    }

    public void setFdd(int fdd) {
        this.fdd = fdd;
    }

    public void setLyy(int lyy) {
        this.lyy = lyy;
    }

    public void setLmm(int lmm) {
        this.lmm = lmm;
    }

    public void setLdd(int ldd) {
        this.ldd = ldd;
    }

    @Override
    public String toString() {
        return " " + fyy + " " + fmm + " " + fdd + " " + lyy + " " + lmm + " " + ldd ;
    }
}
