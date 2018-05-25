package pppdatadownloader.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Downloader {
    String remoteFile = "/pub/products/1993/igr19930.sp3.Z";
    //File file = new File("C:/TMP/2.sp3.Z");
    static Path path = Paths.get("C:/TMP/2.sp3.Z");
    static String FOLDER = "C:/TMP/";

    public void oldDownload(FTPClient connection, File file) throws IOException{
        OutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        InputStream input = connection.retrieveFileStream(remoteFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while((bytesRead = input.read(buffer)) != -1){
            output.write(buffer,0,bytesRead);
        }

        boolean success = connection.completePendingCommand();

        if (success){
            System.out.println("File was successfully downloaded!");
        }

        input.close();
        output.close();
    }

    public static void download(FTPClient connection, List<String> names) throws IOException{
        for(String name: names){
            File file = new File(FOLDER + name);
            OutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            connection.retrieveFile(name, output);
        }
    }

    public boolean checkFiles(FTPClient connection, List<String> names){
        return true;
    }
}
