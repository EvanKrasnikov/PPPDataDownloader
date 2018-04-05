package pppdatadownloader.utils;

import pppdatadownloader.RinexHeader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RinexHeaderParser {
    private static final int BUFFER_SIZE = 4096;
    private Path path;
    private RinexHeader rinex = new RinexHeader();
    private String[] headerLines;

    public RinexHeaderParser(Path path) {
        this.path = path;
        try {
            RandomAccessFile file = new RandomAccessFile(path.toFile(),"r");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            Charset charset = Charset.forName("UTF-8");
            channel.read(buffer);
            buffer.flip();
            CharBuffer charBuffer = charset.decode(buffer);
            headerLines = charBuffer.toString().split("\\n");;
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTimeOfFirstObs(String match){
        String regexp = "\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+).\\d+\\s+\\w+\\s+TIME OF FIRST OBS";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()){
            rinex.setFyy(Integer.valueOf(matcher.group(1)));
            rinex.setFmm(Integer.valueOf(matcher.group(2)));
            rinex.setFdd(Integer.valueOf(matcher.group(3)));
            System.out.println(matcher.groupCount() + " gc");
        } else {
            System.out.println("Find nothing!");
        }
    }

    private void readTimeOfLastObs(String match){
        String regexp = "\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+).\\d+\\s+\\w+\\s+TIME OF LAST OBS";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()){
            rinex.setLyy(Integer.valueOf(matcher.group(1)));
            rinex.setLmm(Integer.valueOf(matcher.group(2)));
            rinex.setLdd(Integer.valueOf(matcher.group(3)));
            System.out.println(matcher.groupCount() + " gc");
        } else {
            System.out.println("Find nothing!");
        }
    }

    private void read(){
        String regexp = "(\\s{1}[/#\\w]+)+$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher;
        for (String line: headerLines){
            matcher = pattern.matcher(line);
            try {
                if (matcher.find()){

                    String s = matcher.group(0);
                    //System.out.println(s);

                    if (s.equals(" TIME OF FIRST OBS")) {
                        //System.out.println(s);
                        readTimeOfFirstObs(line);
                    }

                    if (s.equals(" TIME OF LAST OBS")) {
                        //System.out.println(s);
                        readTimeOfLastObs(line);
                    }
                }
            } catch (Exception e){
                System.err.println("No match!");
            }
        }
    }

    public void print(){
        System.out.println(rinex.toString());
    }
}
