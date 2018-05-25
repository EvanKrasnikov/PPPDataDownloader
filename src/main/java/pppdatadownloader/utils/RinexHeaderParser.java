package pppdatadownloader.utils;

import pppdatadownloader.RinexHeader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pppdatadownloader.utils.DateConverter.getDate;
import static pppdatadownloader.utils.DateConverter.getDayNumber;
import static pppdatadownloader.utils.DateConverter.getWeekNumberAndDay;

public class RinexHeaderParser {
    private static final int BUFFER_SIZE = 4096;
    private RinexHeader rinex = new RinexHeader();
    private String[] headerLines;

    public void parse(File file) throws IOException, ParseException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        Charset charset = Charset.forName("UTF-8");
        channel.read(buffer);
        buffer.flip();
        CharBuffer charBuffer = charset.decode(buffer);
        headerLines = charBuffer.toString().split("\\n");;
        read();
    }

    private void readTimeOfFirstObs(String match) throws ParseException{
        String regexp = "\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+).\\d+\\s+\\w+\\s+TIME OF FIRST OBS";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                stringBuilder.append(matcher.group(i+1));
            }
            rinex.setFirstObs(getDate(stringBuilder.toString()));
        } else {
            System.out.println("Find nothing!");
        }
    }

    private void readTimeOfLastObs(String match) throws ParseException{
        String regexp = "\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+).\\d+\\s+\\w+\\s+TIME OF LAST OBS";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                stringBuilder.append(matcher.group(i+1));
            }
            rinex.setLastObs(getDate(stringBuilder.toString()));
        } else {
            System.out.println("Find nothing!");
        }
    }

    private void read() throws ParseException{
        String regexp = "(\\s{1}[/#\\w]+)+$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher;
        for (String line : headerLines) {
            matcher = pattern.matcher(line);

            if (matcher.find()) {
                String s = matcher.group(0);
                if (s.equals(" TIME OF FIRST OBS")) readTimeOfFirstObs(line);
                if (s.equals(" TIME OF LAST OBS")) readTimeOfLastObs(line);
            }
        }
    }

    public void print() throws ParseException{
        System.out.println(rinex.toString());
        System.out.println("DayNum " + getDayNumber(rinex.getFirstObs()));
        System.out.println("WeekNum " + getWeekNumberAndDay(rinex.getFirstObs()));
    }
}
