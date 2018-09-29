package com.example.leona.projetosorvil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity { //Classe que representa a janela de Login

    private EditText Login, Senha; //Variveis relaciodas as caixas de textos presente na tela

    private FirebaseAuth mAuth; //Variaves do servico de autentificação (--LOGIN CONTA DE USUARIO)
    FirebaseUser user;          //Variavel realiconado ao usuario logado



    @Override
    protected void onCreate(Bundle savedInstanceState) { //Metodos que sao ativados quando a janela é criada!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Declarando a barra de titulo do app Sorvil (Teste)
        setSupportActionBar(toolbar);


        Login = (EditText) findViewById(R.id.editText); //Referenciando as varivaeis da caixa de texto login e senha
        Senha = (EditText) findViewById(R.id.editText2);

        FirebaseAuth.getInstance().signOut(); //Desconecta usuario

        mAuth = FirebaseAuth.getInstance(); //Inicializando as variaves de autentificação
        user = mAuth.getCurrentUser(); //Recebendo o ID do usuario logado


    }

    public void ValidarDados(View view) { //Valida os dados presentes nas caixas de texto da janela mainactivity!
        String login = Login.getText().toString(); //Recebe os valores digitados nas caixas de texto no formato string
        String senha = Senha.getText().toString();

        if (TextUtils.isEmpty(login)) //Verifica se a string login é vazia
            Toast.makeText(getApplicationContext(), "Digite o login ou e-mail!", Toast.LENGTH_SHORT).show();

        else {
            if (TextUtils.isEmpty(senha)) //Verifica se string senha e vazia
                Toast.makeText(getApplicationContext(), "Digite a senha!", Toast.LENGTH_SHORT).show();
            else Login(login,senha);
        }
    }

    public void Cadastrar(View view) { // Pula para a proxima janela
        Intent it = new Intent(MainActivity.this, Main2Activity.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada

    }

    public void Logar(){
        Intent it = new Intent(MainActivity.this, BoasVindas.class); //Referencia uma mudança de tela
        startActivity(it); //Pula para a proxima janela referenciada
    }

    public void Login(String login, String senha){ //Metodo que loga
        mAuth.signInWithEmailAndPassword(login, senha) //Metodo que loga na firebase a partir do login e senha
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  //Se a conexão for um sucesso o metodo é executado
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Authentication is sucess.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser(); //A variavel user recebe o "usuario" atualmente logado
                            Logar();
                        } else {  //Se a conexão for for possivel o metodo é executado
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() { //Metodos executados sempre que a janela é aberta
        super.onStart();
        if (user == null)
            Toast.makeText(this, "Usuario nao autenticado", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Usuario autenticado", Toast.LENGTH_SHORT).show();

    }


}
