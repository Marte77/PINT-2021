package com.example.crowdzero_v000;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class RegistoActivity extends AppCompatActivity implements View.OnClickListener {

    Button cidadeBotao; //talvez alterar estes botoes para textviews iguais as outras para ficar tudo igual
    Button dataNascimentoBotao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        cidadeBotao = findViewById(R.id.botaoCidadeRegisto);
        dataNascimentoBotao = findViewById(R.id.botaoDataNascimentoRegisto);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botaoCidadeRegisto:
                    abrirCidadeDialog();
                break;
            case R.id.botaoDataNascimentoRegisto:
                    abrirCalendario();
                break;
            case R.id.botaoRegistarRegisto:
                    Intent i = new Intent(getApplicationContext(),PaginaPrincipal.class);
                    startActivity(i);
                break;
        }
    }

    public void abrirCalendario(){
       MaterialDatePicker.Builder calendario = MaterialDatePicker.Builder.datePicker();
       calendario.setTitleText(R.string.data_nascimento_registo);

       final MaterialDatePicker datePicker = calendario.build();
       datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
           @Override
           public void onPositiveButtonClick(Object selection) {
               dataNascimentoBotao.setText(datePicker.getHeaderText());

           }
       });

       datePicker.show(getSupportFragmentManager(), String.valueOf(R.string.data_nascimento_registo));
    }

    public void abrirCidadeDialog(){
        //TODO: buscar cidades
        final String[] cidades = {"Aveiro","Beja","Braga","Bragança","Castelo Branco","Coimbra","Évora","Faro","Guarda","Leiria","Lisboa","Portalegre","Porto","Santarém","Setúbal","Viana do Castelo","Vila Real","Viseu"};

        final int opcao = 0;
        MaterialAlertDialogBuilder dialogo = new MaterialAlertDialogBuilder(RegistoActivity.this);
        dialogo.setTitle(R.string.cidade);
        dialogo.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialogo.setPositiveButton(R.string.selecionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.setSingleChoiceItems(cidades, opcao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cidadeBotao.setText(cidades[i]);
            }
        });
        dialogo.show();

    }

}