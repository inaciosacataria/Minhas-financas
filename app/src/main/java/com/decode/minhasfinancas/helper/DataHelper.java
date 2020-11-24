package com.decode.minhasfinancas.helper;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class DataHelper {
    public  static String getData(){
        long data= System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("d/MM/yyyy");
        String getData= simpleDateFormat.format(data);
        return getData;
    }
    public static String dataEmStringParaNos(String texto){

        String[] data= texto.split("/");
        return  data[1]+data[2];
    }
}
