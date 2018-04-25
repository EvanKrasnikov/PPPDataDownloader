package pppdatadownloader.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class Connection {
    private static final String SERVER = "garner.ucsd.edu";
    private static final String LOGIN = "anonymous";
    private static final String PASS = "anon@gmail.com";
    private static final int PORT = 21;
    private FTPClient connection;

    public FTPClient getConnection(){
        return connection;
    }

    public void connect() {
         connection = new FTPClient();

        try {
            connection.connect(SERVER,PORT);
            if(connection.isConnected()){
                System.out.println("Client was connected!");
            } else {
                System.out.println("!!!!!");
            }

            connection.login(LOGIN,PASS);
            connection.enterLocalPassiveMode();
            connection.setFileType(FTP.FILE_STRUCTURE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        if (connection.isConnected()){
            try {
                connection.logout();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
