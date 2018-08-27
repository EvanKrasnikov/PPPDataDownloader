package pppdatadownloader.rinex;

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

    /**
     * Method used for reading headers from RINEX files
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public void readHeader(File file) throws IOException, ParseException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        Charset charset = Charset.forName("UTF-8");
        channel.read(buffer);
        buffer.flip();
        CharBuffer charBuffer = charset.decode(buffer);
        headerLines = charBuffer.toString().split("\\n");
        read();
    }

    /**
     * Method used for defining what regexp to use
     * @throws ParseException
     */
    private void read() throws ParseException{
        String regexp = "[/#\\w]+(\\s{1}[/#\\w]+)+$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher;
        for (String line : headerLines) {
            matcher = pattern.matcher(line);

            if (matcher.find()) {
                String data;

                switch (matcher.group(0)){
                    case "TIME OF FIRST OBS" : {
                        data = parse(line,"TIME OF FIRST OBS");
                        rinex.setFirstObs(getDate(data));
                    }

                    case "TIME OF LAST OBS" : {
                        data = parse(line,"TIME OF LAST OBS");
                        rinex.setLastObs(getDate(data));
                    }
                }
            }
        }
    }

    /**
     * Method returns a String appended from each group of pattern that match regexp
     * @param line
     * @param regex
     * @return String
     */
    private String parse(String line, String regex){
        Pattern pattern = Pattern.compile(ParseMap.getRegExp(regex));
        Matcher matcher = pattern.matcher(line);
        int count = 0;
        StringBuilder sb = new StringBuilder();

        while (matcher.find()){
            sb.append(matcher.group(++count));
        }

        return sb.toString();
    }

    /**
     * Method prints information about corresponded instance of RINEX - header class
     * @throws ParseException
     */
    public void print() throws ParseException{
        System.out.println(rinex.toString());
        System.out.println("DayNum " + getDayNumber(rinex.getFirstObs()));
        System.out.println("WeekNum " + getWeekNumberAndDay(rinex.getFirstObs()));
    }
}
