package com.decode.minhasfinancas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.Model.Movimentacao;
import com.decode.minhasfinancas.Model.Usuario;
import com.decode.minhasfinancas.helper.CriptoBase64;
import com.decode.minhasfinancas.helper.DataHelper;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddReceitaActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth=ConfigFirebase.getAuth();
    DatabaseReference databaseReference=ConfigFirebase.getDatabaseRef();
    TextInputEditText editTextDataReceita,editCategoriaReceita,editDescricaoReceita;
    Usuario usuario;
    EditText  editValorReceita; FloatingActionButton btnConcluirReceita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);
        getSupportActionBar().setTitle("Receita");
        getSupportActionBar().setElevation(0);





        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        DatabaseReference usuarioRef = databaseReference.child("usuario").child( idUsuario );
        DatabaseReference valorRef=databaseReference;
        recuperarUser();
        editValorReceita=findViewById(R.id.editValorReceita);
        editTextDataReceita=findViewById(R.id.editDataReceita);
        editCategoriaReceita=findViewById(R.id.editCategoriaReceita);
        editDescricaoReceita=findViewById(R.id.editDescricaoReceita);;
        editTextDataReceita.setText(DataHelper.getData());
    }

    public void add(View view){

        String  valor= editValorReceita.getText().toString();
        String data= editTextDataReceita.getText().toString();
        String categoria= editCategoriaReceita.getText().toString();
        String descricao= editDescricaoReceita.getText().toString();


        if(!valor.isEmpty()){
            double valorReceita=Double.parseDouble(valor);
            if(!data.isEmpty()){
                if(!categoria.isEmpty()){
                    if(!descricao.isEmpty()){
                       Movimentacao movimentacao= new Movimentacao(data,categoria,descricao,"Receita",valorReceita);
                       movimentacao.salvar();
                        recuperarUser();
                        double receita= movimentacao.getValor()+usuario.getReceita();

                      //
                        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
                        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
                        DatabaseReference usuarioRef = databaseReference.child("usuario").child( idUsuario ).child("receita");
                        usuarioRef.setValue(receita);


                       finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Preencha a descricao",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha a categoria",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Preencha a data",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Preencha o valor de Receita",Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarUser(){





        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        DatabaseReference usuarioRef = databaseReference.child("usuario").child( idUsuario );
        DatabaseReference valorRef=databaseReference;
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               usuario = dataSnapshot.getValue( Usuario.class );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
