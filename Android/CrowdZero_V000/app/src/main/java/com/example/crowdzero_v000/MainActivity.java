package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botaoEntrar = findViewById(R.id.botaoEntrar);
        Button botaoRegisto = findViewById(R.id.botaoRegistoMain);
        TextView hyperlink_sem_registo= findViewById(R.id.hiperlink_Sem_registo);


        MaterialToolbar tb = findViewById(R.id.topAppBar_mainActivity);
        String titulo = "      "+getResources().getString(R.string.app_name);
        tb.setTitle(titulo);
    }

    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.botaoRegistoMain:
                Intent RegistoIntent = new Intent(MainActivity.this,RegistoActivity.class);
                startActivity(RegistoIntent);
            break;
            case R.id.botaoEntrar://verifica se ja fez login anteriormente
                if(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE).getBoolean("SessaoIniciada",false)){
                    //sessao esta iniciada
                    try {
                        FuncoesApi.FuncoesPessoas.fazerLogin(getApplicationContext(),
                                getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE).getString("Email", "null"),
                                getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE).getString("Password", "null"),
                                new FuncoesApi.volleycallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) throws JSONException {
                                        Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onError(JSONObject jsonObjectErr) throws JSONException {
                                        Toast.makeText(getApplicationContext(),"Erro a fazer login automatico",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(i);
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Intent EntrarIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(EntrarIntent);
                }
                break;
            case R.id.hiperlink_Sem_registo:
                Toast.makeText(getApplicationContext(),"a tua mae",Toast.LENGTH_LONG).show();
                break;
        }
    }
}