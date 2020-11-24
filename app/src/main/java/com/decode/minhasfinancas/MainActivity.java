package com.decode.minhasfinancas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {
   private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_main);


 setButtonBackVisible(false);
 setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder()
                    .fragment(R.layout.intro1)
                    .background(android.R.color.white)
                    .build());

    addSlide(new FragmentSlide.Builder()
                    .fragment(R.layout.intro2)
                    .background(android.R.color.white)
                    .build());

    addSlide(new FragmentSlide.Builder()
                    .fragment(R.layout.intro3)
                            .background(android.R.color.white)
                            .build());

        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro2)
                .background(android.R.color.white)
                .build());

        addSlide(new FragmentSlide.Builder()
                .fragment(R.layout.intro_casdastro)
                .background(android.R.color.white)
                .canGoForward(false)
                .build());

    }

    public void Cadastro(View view){
        Intent intent = new Intent(getApplicationContext(),CadastroActivity.class);
        startActivity(intent);
    }
    public void Login(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
      VerificarUsuario();

    }

    public void VerificarUsuario(){
        auth= ConfigFirebase.getAuth();
        if(auth.getCurrentUser()!=null){
            Intent intent=new Intent(this,PrincipalActivity.class);
            startActivity(intent); 
            finish();
        }
    }
}