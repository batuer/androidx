package com.gusi.java;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author Ylw
 * @since 2020/8/13 22:36
 */
public class Browser {
    //"C:\\Users\\batue\\Desktop\\url.txt"
    public static void open(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println(file.getAbsolutePath() + " not exist.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    browse2(line.trim());
                    Thread.sleep(2000);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(":" + e.toString());
        }
    }

    public static void browse1(String url) {
        try {
            String osName = System.getProperty("os.name", "");
            if (osName.startsWith("Windows")) {// windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (osName.startsWith("Mac OS")) {// Mac
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", String.class);
                openURL.invoke(null, url);
            } else {// Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }

                if (browser == null) {
                    throw new RuntimeException("Not found browser.");
                } else {
                    Runtime.getRuntime().exec(new String[]{browser, url});
                }
            }
        } catch (Exception e) {
            System.out.println(":" + e.toString());
        }
    }


    public static void browse2(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(url);
                desktop.browse(uri);
            }
        } catch (Exception e) {
            System.out.println(":" + e.toString());
        }
    }

    public static void browse3(String url) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (Exception e) {
            System.out.println(":" + e.toString());
        }
//        Runtime.getRuntime().exec("cmd /c start iexplore " + url);
    }
}
