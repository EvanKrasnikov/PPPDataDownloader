package pppdatadownloader.ftp;

import org.apache.commons.net.ftp.FTPClient;
import pppdatadownloader.exception.FailedDownloadingException;

import java.io.*;
import java.util.List;

public class Downloader implements Performable {
    private List<String> remoteFiles;
    private FTPClient client;
    private String initialFolder;

    public void perform(){
        for(String remote: remoteFiles){
            File local = new File(initialFolder + "/" + cropName(remote));

            try {
                OutputStream output = new BufferedOutputStream(new FileOutputStream(local));
                client.retrieveFile(remote, output);

                if (!client.completePendingCommand())
                    throw new FailedDownloadingException(remote);

                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String cropName(String str){
        return str.substring(str.lastIndexOf("/"));
    }

    public Downloader setRemoteFiles(List<String> remoteFiles) {
        this.remoteFiles = remoteFiles;
        return this;
    }

    public Downloader setClient(FTPClient client) {
        this.client = client;
        return this;
    }

    public Downloader setInitialFolder(String initialFolder) {
        this.initialFolder = initialFolder;
        return this;
    }
}
