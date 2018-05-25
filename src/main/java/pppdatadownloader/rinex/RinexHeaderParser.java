package pppdatadownloader.rinex;

import pppdatadownloader.rinex.RinexHeader;

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
import static pppdatadownloader.rinex.ParseMap.getParseInstruction;

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

    private String parseObs(String match, String mapKey){
        String regexp = "\\s+([#/.\\w]+)";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(match);
        StringBuilder stringBuilder = new StringBuilder();

        if (matcher.find()){
            int[] expression = getParseInstruction(mapKey);
            for (int i = 0; i < expression.length ; i++) {
                if (i == expression[i]) stringBuilder.append(matcher.group(1));
            }
        } else {
            System.out.println("Find nothing!");
        }
        return stringBuilder.toString();
    }

    private void read() throws ParseException{
        String regexp = "[/#\\w]+(\\s{1}[/#\\w]+)+$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher;
        for (String line : headerLines) {
            matcher = pattern.matcher(line);

            if (matcher.find()) {
                String data = parseObs(line, matcher.group(0));
                switch (matcher.group(0)){
                    case "TIME OF FIRST OBS" : rinex.setFirstObs(getDate(data));
                    case "TIME OF LAST OBS" : rinex.setLastObs(getDate(data));
                }
            }
        }
    }

    public void print() throws ParseException{
        System.out.println(rinex.toString());
        System.out.println("DayNum " + getDayNumber(rinex.getFirstObs()));
        System.out.println("WeekNum " + getWeekNumberAndDay(rinex.getFirstObs()));
    }
}
