package pppdatadownloader.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String server) {
        super("Failed to connect to the server" + server);
    }

}
