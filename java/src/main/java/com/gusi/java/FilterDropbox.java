package com.gusi.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;

/**
 * @author Ylw
 * @since 2020/8/2 12:46
 */
public class FilterDropbox {
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public void filterDropbox(String fileName, String process) {
        File dropboxFile = new File(new File("").getAbsolutePath(), fileName);
        if (!dropboxFile.exists()) {
            System.out.println("Not exist: " + dropboxFile.getAbsolutePath());
            return;
        }
        File[] files = dropboxFile.listFiles((file1, s) -> {
            System.out.println(s + " : " + file1.toString());
            return s.endsWith(".txt");
        });
        if (files != null) {
            for (File file : files) {
                renameFile(file, process);
            }
        }
    }

    private void renameFile(File file, String process) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean contains = reader.readLine().contains(process);
            reader.close();
            if (contains) {
                String name = file.getName();
                String longTime = name.substring(name.indexOf("@") + 1, name.indexOf(".txt"));
                String formatTime = mFormat.format(Long.parseLong(longTime));
                file.renameTo(new File(file.getParent(), formatTime + ".txt"));
            }
        } catch (Exception e) {
            System.err.println(":" + e.toString());
        }
    }
}
