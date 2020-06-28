package com.gusi.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class YlwClass {

    public static void main(String[] args) {
        int totalDays = 0;
        long totalMinutes = 0;
        try {
            File file = new File(new File("").getCanonicalPath(), "Input");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            // 168929 2019/6/29	8:05	17:48
            while ((line = reader.readLine()) != null) {
                System.out.println(":" +line);
                String[] strings = line.split(",");
                if (strings.length != 6) {
                    continue;
                }
                if (isSaturday(strings[3])) {
                    continue;
                }
                totalDays++;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date begin = sdf.parse(strings[4]);
                Date end = sdf.parse(strings[5]);
                Date afternoon = sdf.parse("17:30");
                Date afternoon1 = sdf.parse("18:00");
                long total =
                        afternoon.getTime() - begin.getTime() - 90 * 60 * 1000 + Math.max((end.getTime() - afternoon1.getTime()), 0);
                totalMinutes += total / (1000 * 60);
            }
            System.out.println(":" + totalDays);
            double v = (totalMinutes - totalDays * 480) + 1.0D;
            double average =
                    new BigDecimal(v / (60 * totalDays)).setScale(2,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("TotalDays: = " + totalDays + " ,totalMinutes: = " + v + " , average: = " + average);
            reader.close();
        } catch (ParseException | IOException e) {
            System.err.println("Error: " + e.toString());
        }
    }


    private static boolean isSaturday(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }


    private static class Entry {
        private String mDate;
        private String mBegin;
        private String mEnd;

        public Entry(String[] strings) {
            mDate = strings[3];
            mBegin = strings[4];
            mEnd = strings[5];
        }

        public String getDate() {
            return mDate;
        }

        public String getBegin() {
            return mBegin;
        }

        public String getEnd() {
            return mEnd;
        }
    }
}
