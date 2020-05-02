package com.gusi.java;

import java.util.Base64;

public class YlwClass {
    public static void main(String[] args) {
        String base64 = Base64.getEncoder().encodeToString("aaa".getBytes());
        System.out.println(base64);
        byte[] bytes = Base64.getDecoder().decode(base64);
        System.out.println(new String(bytes));

    }

    private void test() {

    }
}
