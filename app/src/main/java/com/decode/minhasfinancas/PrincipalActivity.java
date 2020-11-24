package com.decode.minhasfinancas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.decode.minhasfinancas.Adapter.AdapterLista;
import com.decode.minhasfinancas.Config.ConfigFirebase;
import com.decode.minhasfinancas.Model.Movimentacao;
import com.decode.minhasfinancas.Model.Usuario;
import com.decode.minhasfinancas.helper.CriptoBase64;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    Usuario usuario;
    TextView editValorReceitaPrincipal,txtOlaUser, txtDescricao;
    FirebaseAuth firebaseAuth=ConfigFirebase.getAuth();
    DatabaseReference databaseReference=ConfigFirebase.getDatabaseRef();
   DatabaseReference user;
   MaterialCalendarView calendarView;
   ValueEventListener valueEventListenerUsuario;
   ValueEventListener valueEventListenerMovimentacoes;
    DatabaseReference usuarioRef;
   RecyclerView recyclerView;
   List<Movimentacao>m= new ArrayList<Movimentacao>();
   DatabaseReference movimentacoesRef;
   CalendarDay data;
   String datalista;
    AdapterLista adapter;
    Movimentacao movimentacaoApagar;
   double receita=0,despesa=0,resumoTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Minhas Financas");
        toolbar.setLogo(R.drawable.ic_monetization);

        setSupportActionBar(toolbar);
        editValorReceitaPrincipal=findViewById(R.id.txtValorReceitaPrincipal);
        calendarView=findViewById(R.id.calendarView);
       txtOlaUser=findViewById(R.id.txtOlaUser);
       txtDescricao=findViewById(R.id.txtDescricaoPrincioal);
       recyclerView=findViewById(R.id.recycleView);

        if(saldo<0){

        }else{

        }

       Swipe();

        data= calendarView.getCurrentDate();
        datalista=(data.getMonth()+1)+""+data.getYear();
        calendarView.setOnMonthChangedListener(
                new OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                        datalista=(date.getMonth()+1)+""+date.getYear();
                 //       Log.i("aaa", "onMonthChanged: "+datalista);
                        movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
                        recuperarMovimentacoes();
                    }
                }
        );



        adapter= new AdapterLista(m,getApplicationContext());

        //configurar o recycle
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }



    public void recuperarMovimentacoes(){

        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        movimentacoesRef=databaseReference.child("Movimentacao").child(idUsuario).child(datalista);

        valueEventListenerMovimentacoes= movimentacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                m.clear();
                for (DataSnapshot dados: snapshot.getChildren() ){

                    Movimentacao movimentacao = dados.getValue( Movimentacao.class );
                    movimentacao.setKey(dados.getKey());
                    Log.i("amor", "onDataChange: "+ movimentacao.getValor());
                    m.add( movimentacao );

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void AdcionarDespesa(View view){

        Intent intent= new Intent(this, AddDespesaActivity.class);
        startActivity(intent);

    }
    public  void AdcionarReceita(View view){

        Intent intent= new Intent(this, AddReceitaActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    Context c=this;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.btnSair:
                                       if(firebaseAuth.getCurrentUser()!=null){
                                           firebaseAuth.signOut();
                                           Intent intent = new Intent(c, MainActivity.class);
                                           startActivity(intent);
                                           finish();
                                       }

                    break;
                }

        return super.onOptionsItemSelected(item);
    }
    double saldo;


    public void recuperarDados(){


        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        usuarioRef = databaseReference.child("usuario").child( idUsuario );

         valueEventListenerUsuario= usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue( Usuario.class );
                receita=usuario.getReceita();
                despesa=usuario.getDespesa();
                resumoTotal=receita-despesa;
                editValorReceitaPrincipal.setText(resumoTotal+" Mt");
                txtOlaUser.setText("Ola, "+usuario.getNome());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        data= calendarView.getCurrentDate();
        datalista=(data.getMonth()+1)+""+data.getYear();
        recuperarDados();
        recuperarMovimentacoes();
    }

    public void Swipe(){
        ItemTouchHelper.Callback itemTouch= new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFrlags = ItemTouchHelper.START | ItemTouchHelper.END;
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                return makeMovementFlags(dragFlags,swipeFrlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ExcluirMovimentaco(viewHolder, viewHolder.getAdapterPosition());
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void ExcluirMovimentaco(RecyclerView.ViewHolder a, final int posision){

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);

        alertDialog.setTitle("Apagar");
        alertDialog.setMessage("Tem certeza que deseja apagar esses dados?");
        alertDialog.setIcon(R.drawable.ic_delete);
        alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
                String idUsuario = CriptoBase64.Encriptar( emailUsuario );
                movimentacoesRef=databaseReference.child("Movimentacao").child(idUsuario).child(datalista);

                movimentacaoApagar= m.get(posision);
                movimentacoesRef.child(movimentacaoApagar.getKey()).removeValue();
                atualisarSaldo();
                adapter.notifyItemRemoved(posision);

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               adapter.notifyDataSetChanged();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.create();
        alertDialog.show();





    }
    public void atualisarSaldo(){
        String emailUsuario =firebaseAuth.getCurrentUser().getEmail();
        String idUsuario = CriptoBase64.Encriptar( emailUsuario );
        usuarioRef = databaseReference.child("usuario").child( idUsuario );

        if(movimentacaoApagar.getTipoDeMov().equals("Receita")){
            double novaReceitaTotal= usuario.getReceita()-movimentacaoApagar.getValor();
            usuarioRef.child("receita").setValue(novaReceitaTotal);
        } else if(movimentacaoApagar.getTipoDeMov().equals("Despesa")){
            double novaReceitaTotal= usuario.getDespesa()-movimentacaoApagar.getValor();
            usuarioRef.child("despesa").setValue(novaReceitaTotal);
        }
    }
}
