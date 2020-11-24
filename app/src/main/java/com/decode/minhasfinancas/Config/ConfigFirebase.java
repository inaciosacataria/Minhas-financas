package com.decode.minhasfinancas.Config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static FirebaseAuth auth;
  public static DatabaseReference databaseReference;

    public  static FirebaseAuth getAuth(){
        if(auth==null){
        auth=FirebaseAuth.getInstance();}
        return auth;
    }


    public  static DatabaseReference getDatabaseRef(){
        if(databaseReference==null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }

    public  static DatabaseReference getDatabaseRefUsuario(){
        if(databaseReference==null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;
    }
}
