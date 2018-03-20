package pppdatadownloader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FTPConnection {
    private static final String SERVER = "garner.ucsd.edu";
    private static final String LOGIN = "anonymous";
    private static final String PASS = "anon@gmail.com";
    private static final int PORT = 21;

    public void getConnection() {
        FTPClient client = new FTPClient();

        try {
            client.connect(SERVER,PORT);
            if(client.isConnected()){
                System.out.println("Client was connected!");
            } else {
                System.out.println("!!!!!");
            }

            client.login(LOGIN,PASS);
            client.enterLocalPassiveMode();
            client.setFileType(FTP.FILE_STRUCTURE);

            String remoteFile = "/pub/products/1993/igr19930.sp3.Z";
            Path localFile = Paths.get("C:/Downloads/1.sp3.Z");
            File file = new File("C:/TMP/2.sp3.Z");

            OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            InputStream input = client.retrieveFileStream(remoteFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while((bytesRead = input.read(buffer)) != -1){
                stream.write(buffer,0,bytesRead);
            }

            boolean succsess = client.completePendingCommand();

            if (succsess){
                System.out.println("File was successfully downloaded!");
            }

            stream.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client.isConnected()){
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
