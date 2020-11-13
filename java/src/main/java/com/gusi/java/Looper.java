package com.gusi.java;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Ylw
 * @since 2020/9/21 21:48
 */
public class Looper {
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    public void collect() throws IOException {
        int limit = 1000;
        File[] files = new File("C:\\Users\\batue\\Desktop\\log").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".txt");
            }
        });
        File out = new File("C:\\Users\\batue\\Desktop\\log\\out");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "utf-8"));
        String line = null;
        for (File file : files) {
            System.out.println("====:" + file.getName());
            InputStreamReader inStream = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader reader = new BufferedReader(inStream);
            String dispatchLine = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(">>>>> Dispatching to")) {
                    System.out.println("11111:" + line);
                    dispatchLine = line;
                } else if (line.contains("<<<<< Finished to")) {
                    System.out.println("22222:" + (dispatchLine == null) + ":--:" + line);
                    if (dispatchLine == null) {
                        continue;
                    }
                    long diff = getDiff(dispatchLine, line);
                    if (diff >= limit) {
                        writeFile(dispatchLine, line, writer, diff);
                    }
                    dispatchLine = null;
                } else {
                    System.out.println("3333:" + line);
                }
            }
            reader.close();
        }
        writer.close();
    }

    private void writeFile(String dispatchLine, String finishLine, BufferedWriter writer, long diff) throws IOException {
        writer.newLine();
        writer.write("----------:" + diff);
        writer.newLine();
        writer.write(dispatchLine);
        writer.newLine();
        writer.write(finishLine);
        writer.newLine();
    }

    private long getDiff(String dispatchLine, String finishLine) {
        String startTime = dispatchLine.substring(0, dispatchLine.indexOf("  "));
        String endTime = finishLine.substring(0, finishLine.indexOf("  "));
        try {
            return (mDateFormat.parse(endTime).getTime() - mDateFormat.parse(startTime).getTime());
        } catch (ParseException e) {
            System.out.println("" + e.toString());
        }
        return 0L;
    }

    private boolean isEmpty(String line) {
        return line == null || line.isEmpty();
    }
}
