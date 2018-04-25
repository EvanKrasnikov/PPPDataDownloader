package pppdatadownloader;

import pppdatadownloader.ui.Window;
import pppdatadownloader.utils.RinexHeaderParser;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        //new FTPConnection().getConnection();

        //RinexHeaderParser parser = new RinexHeaderParser(new File("C:/TMP/2017_06_16_VOLX_g101b10149_f001_.17o").toPath());
        //parser.print();

        Window app = new Window();
        app.setVisible(true);
        app.setResizable(false);
    }
}
