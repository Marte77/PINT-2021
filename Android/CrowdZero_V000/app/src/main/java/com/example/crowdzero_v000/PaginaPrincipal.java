package com.example.crowdzero_v000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.crowdzero_v000.fragmentos.CardReportFragment;
import com.google.android.material.appbar.MaterialToolbar;

//<!--Note: The layout_width and layout_height attributes should be set to wrap_content, match_parent, or a custom dimension depending on the navigation drawer type and parent ViewGroup.-->

public class PaginaPrincipal extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        CardReportFragment cardInicial = CardReportFragment.newInstance("Martinho\nàs 09:47 de 20/05/21","Muito populado - Muitas pessoas na fila da cantina assustei-me");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linearLayout,cardInicial).commit();
    }

}