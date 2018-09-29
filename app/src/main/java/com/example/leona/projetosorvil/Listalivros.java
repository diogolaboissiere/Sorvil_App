package com.example.leona.projetosorvil;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leona.projetosorvil.bancodedados.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listalivros extends AppCompatActivity {
    private List<String> livros;
    private List<Livro> dados;
    private RecyclerView listagem;
    private LivroAdapter livroAdapter;
    private ConstraintLayout layoutContentMain;

    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;          // Variaveis relacionadas autentificacao usuario
    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalivros);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InicializaComponetes();

        livros = new ArrayList<String>();
        dados =new ArrayList<Livro>();

        CarregarListaLivros();

        listagem = (RecyclerView)findViewById(R.id.listalivros2);
        listagem.setHasFixedSize(true);
        layoutContentMain = (ConstraintLayout)findViewById(R.id.layout2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listagem.setLayoutManager(linearLayoutManager);
        livroAdapter = new LivroAdapter(dados);
        listagem.setAdapter(livroAdapter);
    }

    public void InicializaComponetes(){
        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference();

        if(user == null) Toast.makeText(this, "Usuario nao autenticado", Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
    }

    public void CarregarListaLivros(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        databasereference.child("Conta").child(user.getUid().toString()).child("Livros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    String livroref = objSnapshot.getValue().toString();
                    livros.add(livroref);
                }
                CarregaListaLivros2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CarregaListaLivros2() {
        String idlivro;
        Iterator<String> itr = livros.iterator();
        while(itr.hasNext()){
            idlivro = itr.next();

            databasereference.child("Livros").child(idlivro).addListenerForSingleValueEvent( //Metodo que tenta acessar um campo no banco de dados
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {  //Se a tentiva de consulta for sucessida esta funcao é ativada
                            // I commented the code here to check whether its coming from this part but it continued to occur even after commenting.
                            String id =dataSnapshot.child("id").getValue().toString();
                            String autor =dataSnapshot.child("autor").getValue().toString();
                            String titulo =dataSnapshot.child("titulo").getValue().toString();
                            String ano = dataSnapshot.child("ano").getValue().toString();

                            Livro aux = new Livro(titulo,autor,ano,id);
                            dados.add(aux);

                            livroAdapter = new LivroAdapter(dados);
                            listagem.setAdapter(livroAdapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {  //Se a tentativa de consulta nao for possivel esta funcao e chamada
                            Toast.makeText(getApplicationContext(), "Requisão falhou!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }



}
