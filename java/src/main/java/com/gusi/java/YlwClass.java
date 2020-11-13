package com.gusi.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class YlwClass {

    public static void main(String[] args) {
//        String s = "4 2 5 3 7";

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                if (input == null){
                    continue;
                }
                List<String> list = Arrays.asList(input.split(" "));
                Collections.sort(list);
                List<String> arrayList = new ArrayList<String>();
                int k = 0;
                for (int i = 0; i < list.size() / 2; i++) {
                    arrayList.add(list.get(k));
                    k += 2;
                }
                if ((list.size()) % 2 != 0) {
                    arrayList.add(list.get(list.size() - 1));
                }
                StringBuilder sb = new StringBuilder();
                for (int u = 0; u < arrayList.size(); u++) {
                    sb.append(arrayList.get(u));
                    if (u < arrayList.size() - 1) {
                        sb.append(" ");
                    }
                }
                System.out.println(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
