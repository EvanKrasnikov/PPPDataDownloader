package pppdatadownloader.exception;

public class FailedDownloadingException extends RuntimeException{

    public FailedDownloadingException(String str) {
        super("Failed to download file " + str);
    }

}
