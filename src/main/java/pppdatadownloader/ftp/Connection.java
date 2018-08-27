package pppdatadownloader.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import pppdatadownloader.exception.ConnectionException;
import pppdatadownloader.exception.MissingDataException;

import java.io.*;

public class Connection {
    private String server;
    private String login;
    private String pass;
    private int port;
    private FTPClient connection;

    public Connection connect() throws IOException{
        connection = new FTPClient();

        if (server == null || login == null || pass == null || port == 0)
            throw new MissingDataException("Missing data at " + Connection.class);

        connection.connect(server,port);

        if(!connection.isConnected())
            throw new ConnectionException(server);

        connection.login(login,pass);
        connection.enterLocalPassiveMode();
        connection.setFileType(FTP.FILE_STRUCTURE);
        return this;
    }

    public void perform(Performable performable){
        performable.perform();
    }

    public void disconnect() throws IOException{
        if (connection.isConnected()){
            connection.logout();
            connection.disconnect();
        }
    }

    public Connection setServer(String server) {
        this.server = server;
        return this;
    }

    public Connection setLogin(String login) {
        this.login = login;
        return this;
    }

    public Connection setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public Connection setPort(int port) {
        this.port = port;
        return this;
    }

    public FTPClient getConnection(){
        return connection;
    }

}
