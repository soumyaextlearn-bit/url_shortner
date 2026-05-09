package com.soumya.urlshortner.utill;

public class Base62Encoder {
    private static final String BASE62CHAR =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long value){
        StringBuilder sb = new StringBuilder();

        while(value>0){
            int remainder = (int) (value%62);
            sb.append(BASE62CHAR.charAt(remainder));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
