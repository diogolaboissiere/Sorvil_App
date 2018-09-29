package com.example.leona.projetosorvil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsultaLivro extends AppCompatActivity {
    private String idlivro,nomelivro,autorlivro,anolivro;
    private TextView titulo,autor,ano,descricao;
    private Button addrem;
    private boolean possuirlivro;

    FirebaseDatabase firebasedatabase; // Variaveis relacionadas ao banco de dados
    DatabaseReference databasereference;
    private FirebaseAuth mAuth; //Variaves relacionadas a autentificação
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_livro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Declarando a barra de titulo do app Sorvil (Teste)
        setSupportActionBar(toolbar);


        titulo = (TextView)findViewById(R.id.textView16);
        autor = (TextView)findViewById(R.id.textView17);
        ano = (TextView)findViewById(R.id.textView19);
        descricao = (TextView)findViewById(R.id.textView22);
        addrem = (Button)findViewById(R.id.button8);


        VerificaParametro();
        CarregarLivro();
        VerificaBotao();

    }

    public void AddRem(View view){
        firebasedatabase = FirebaseDatabase.getInstance();  //Ligando o banco de dados
        databasereference = firebasedatabase.getReference();

        if(possuirlivro){

            databasereference.child("Conta").child(user.getUid().toString()).child("Livros").child(idlivro).removeValue();
        }
        else{
            databasereference.child("Conta").child(user.getUid().toString()).child("Livros").child(idlivro).setValue(idlivro);
        }
        possuirlivro = !possuirlivro;
        VerificaBotao();
    }

    public void VerificaParametro(){
        Bundle bundle = getIntent().getExtras();

        if((bundle != null) && (bundle.containsKey("Livro"))){
            idlivro = (String) bundle.getSerializable("Livro");
        }
    }

    public void VerificaBotao(){
        mAuth = FirebaseAuth.getInstance(); //Verifica qual usuario está logado
        user = mAuth.getCurrentUser();
        if(user == null) Toast.makeText(this, "Usuario nao autenticado", Toast.LENGTH_SHORT).show();

        firebasedatabase = FirebaseDatabase.getInstance();  //Ligando o banco de dados
        databasereference = firebasedatabase.getReference();

        databasereference.child("Conta").child(user.getUid()).child("Livros").addListenerForSingleValueEvent( //Metodo que tenta acessar um campo no banco de dados
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {  //Se a tentiva de consulta for sucessida esta funcao é ativada
                        // I commented the code here to check whether its coming from this part but it continued to occur even after commenting.
                        if(dataSnapshot.hasChild(idlivro)){
                            addrem.setText("Remover Livro");
                            possuirlivro = true;
                        }
                        else {
                            addrem.setText("Adicionar Livro");
                            possuirlivro = false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {  //Se a tentativa de consulta nao for possivel esta funcao e chamada
                        Toast.makeText(getApplicationContext(), "Requisão falhou!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void CarregarLivro(){
        firebasedatabase = FirebaseDatabase.getInstance();  //Ligando o banco de dados
        databasereference = firebasedatabase.getReference();
        databasereference.child("Livros").child(idlivro).addListenerForSingleValueEvent( //Metodo que tenta acessar um campo no banco de dados
                new ValueEventListener() {
                    String n1;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {  //Se a tentiva de consulta for sucessida esta funcao é ativada
                        // I commented the code here to check whether its coming from this part but it continued to occur even after commenting.


                        nomelivro = (String)dataSnapshot.child("titulo").getValue();
                        autorlivro = (String)dataSnapshot.child("autor").getValue();
                        anolivro = (String)dataSnapshot.child("ano").getValue();

                        titulo.setText(nomelivro);
                        autor.setText(autorlivro);
                        ano.setText(anolivro);


                        Toast.makeText(getApplicationContext(), "A requisição foi um sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {  //Se a tentativa de consulta nao for possivel esta funcao e chamada
                        Toast.makeText(getApplicationContext(), "Requisão falhou!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
