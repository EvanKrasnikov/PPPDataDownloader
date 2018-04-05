package pppdatadownloader.utils;

import pppdatadownloader.RinexHeader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pppdatadownloader.utils.DateConverter.getDate;

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
            String[] dateElements = new String[6];
            for (int i = 0; i < 6; i++) {
                dateElements[i] = matcher.group(i+1);
            }
            rinex.setFirstObs(getDate(dateElements));
        } else {
            System.out.println("Find nothing!");
        }
    }

    private void readTimeOfLastObs(String match){
        String regexp = "\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+).\\d+\\s+\\w+\\s+TIME OF LAST OBS";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()){
            String[] dateElements = new String[6];
            for (int i = 0; i < 6; i++) {
                dateElements[i] = matcher.group(i+1);
            }
            rinex.setLastObs(getDate(dateElements));
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
                    if (s.equals(" TIME OF FIRST OBS")) readTimeOfFirstObs(line);
                    if (s.equals(" TIME OF LAST OBS")) readTimeOfLastObs(line);
                }
            } catch (Exception e){
                System.err.println("No match!");
            }
        }
    }

    public void print(){
        System.out.println(rinex.toString());

        try {
            System.out.println("DayNum " + DateConverter.getDayNumber(rinex.getFirstObs()));
            System.out.println("WeekNum " + DateConverter.getWeekNumberAndDay(rinex.getFirstObs()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
