package com.demo.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MMM {

    public static void main(String[] args) {
        String a = "/blog/del/{id}/books";
        String b = "/blog/del/aaa1/books";
        a = a.replaceAll("\\{([\\w\\W]+)\\}", "([a-zA-Z0-9]+)");
        //System.out.println(a);
        Pattern p = Pattern.compile(a);
        Matcher m = p.matcher(b);
        if(m.find()){
            System.out.println(m.group(0));
        }
    }

}
