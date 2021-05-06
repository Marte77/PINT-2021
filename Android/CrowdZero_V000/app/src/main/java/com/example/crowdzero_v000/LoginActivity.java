package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MaterialToolbar tb = findViewById(R.id.appbar_login);
        String titulo = "      "+getResources().getString(R.string.titulo_Login);
        tb.setTitle(titulo);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.botaoLogin2:
                    Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
                    startActivity(i);
                break;
        }
    }
}