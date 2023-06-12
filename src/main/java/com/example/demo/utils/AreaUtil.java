package com.example.demo.utils;

public class AreaUtil {
    public static String addWildcards(String id){
        if(id.equals("0000")){
            return "%%";
        } else if (id.substring(2,4).equals("00")) {
            return id.substring(0,2)+"%";
        } else{
            return id;
        }
    }
}
