package com.decode.minhasfinancas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.decode.minhasfinancas.Model.Movimentacao;
import com.decode.minhasfinancas.R;

import java.util.List;

import static com.decode.minhasfinancas.R.color.colorPrimaryDispesas;
import static com.decode.minhasfinancas.R.color.colorPrimaryReceita;

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.MyViewHolder> {

    private List<Movimentacao> m;
    private Context c;
    public AdapterLista(List<Movimentacao>movimentacaos, Context context) {
        m=movimentacaos;
        c=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movimentacao mv=m.get(position);
        holder.txtCategoria.setText(mv.getCategoria());
        holder.txtDescricao.setText(mv.getDescricao());


        if(mv.getTipoDeMov().equals("Despesa")){
            holder.txtValor.setTextColor(c.getResources().getColor(colorPrimaryDispesas)); holder.txtValor.setText("-"+mv.getValor()+" Mt");
        }
        else if(mv.getTipoDeMov().equals("Receita")){
            holder.txtValor.setTextColor(c.getResources().getColor(colorPrimaryReceita));  holder.txtValor.setText("+"+mv.getValor()+" Mt");
        }
    }

    @Override
    public int getItemCount() {
        return m.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txtDescricao,txtCategoria,txtValor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescricao=itemView.findViewById(R.id.txtDescricaoLista);
            txtCategoria=itemView.findViewById(R.id.txtCategoriaLista);
            txtValor=itemView.findViewById(R.id.txtValorLista);
        }
    }
}
