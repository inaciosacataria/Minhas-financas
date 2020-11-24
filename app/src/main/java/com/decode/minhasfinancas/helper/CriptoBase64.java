package com.decode.minhasfinancas.helper;

import android.util.Base64;

public class CriptoBase64 {

    public static String Encriptar(String email){
       return  Base64.encodeToString(email.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static String Desincriptar(String email){
        return  new String(Base64.decode(email.getBytes(),Base64.DEFAULT));
    }
}
