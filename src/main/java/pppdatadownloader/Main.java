package pppdatadownloader;

import pppdatadownloader.utils.RinexHeaderParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        //new FTPConnection().getConnection();

        RinexHeaderParser parser = new RinexHeaderParser(new File("C:/TMP/2017_06_16_VOLX_g101b10149_f001_.17o").toPath());
       // parser.readTimeOfFirstObs();
        //parser.readTimeOfLastObs();

    }
}
