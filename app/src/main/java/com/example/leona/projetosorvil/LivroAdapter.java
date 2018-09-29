package com.example.leona.projetosorvil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leona.projetosorvil.bancodedados.Livro;

import org.w3c.dom.Text;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.ViewHolderLivros> {
    private List<Livro> dados;

    public LivroAdapter(List<Livro> dados){
        this.dados = dados;

    }

    @Override
    public LivroAdapter.ViewHolderLivros onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linhalivro,parent,false);
        ViewHolderLivros holderLivros = new ViewHolderLivros(view,parent.getContext());

        return holderLivros;
    }

    @Override
    public void onBindViewHolder(LivroAdapter.ViewHolderLivros holder, int position) {
        if(dados != null && dados.size() > 0){
            Livro livro = dados.get(position);

            holder.titulo.setText(livro.getTitulo());
            holder.autor.setText(livro.getAutor());
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderLivros extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView autor;

        public ViewHolderLivros(View itemView, final Context context) {
            super(itemView);

            titulo = (TextView)itemView.findViewById(R.id.textView11);
            autor = (TextView)itemView.findViewById(R.id.textView12);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (dados.size() > 0) {
                        String idlivro;
                        idlivro = dados.get(getLayoutPosition()).getId();


                        Intent it = new Intent(context, ConsultaLivro.class);
                        it.putExtra("Livro",idlivro);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }

}
