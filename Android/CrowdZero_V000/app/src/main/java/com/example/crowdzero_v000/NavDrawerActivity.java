package com.example.crowdzero_v000;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

//https://stackoverflow.com/a/49500446/10676498
//agora, todas as atividades vao fazer "extend" desta classe e nao da AppCompatActivity
public class NavDrawerActivity extends AppCompatActivity {
    MaterialToolbar tb;
    DrawerLayout dl;
    NavigationView nv;
    int opcaoEscolhida;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navdrawer_activity);//vai passar a usar este layout como layout base

        //obter opcao escolhida anteriormente para colocar a opcao ativa no menu
        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if(bundle == null){
                opcaoEscolhida = -1;
            }
            else{
                opcaoEscolhida = bundle.getInt("opcaoEscolhida");
            }
        }else{
            opcaoEscolhida = (int) savedInstanceState.getSerializable("opcaoEscolhida");

        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        ViewStub stub = findViewById(R.id.navdrawer_stubview);
        stub.setLayoutResource(layoutResID);
        stub.inflate();

        // super.setContentView(layoutResID);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //colocar metodos onclick e isso
        //declaraçao dos elementos da activity base que contem o menu lateral
        tb = findViewById(R.id.topAppBar_navdrawerBase);
        dl = findViewById(R.id.drawerlayout_navdrawer);
        nv = findViewById(R.id.NavViewNavDrawerBase);

        //if(opcaoEscolhida != -1)
        //{
        //    MenuItem itemescolhidoAnteriormente = nv.getMenu().getItem(opcaoEscolhida);
        //    itemescolhidoAnteriormente.setChecked(true);
        //}else{ //se nao for escolhido nada defaulta para o home
        //    MenuItem itemescolhidoAnteriormente = nv.getMenu().getItem(R.id.opcoes_navbar_home);
        //    itemescolhidoAnteriormente.setChecked(true);
        //}

        //colocar o menu lateral a ocupar 60% do ecrã
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        int widthEcra = dpMetrics.widthPixels;
        ViewGroup.LayoutParams lp = nv.getLayoutParams();
        lp.width = (int) (widthEcra * 0.6);
        nv.setLayoutParams(lp);

        //colocar os listeners dos botoes
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(view.getForegroundGravity());
            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                abrirSeparadorItem(item);
                return true;
            }

        });

    }

    //funcao para abrir a activity escolhida no menu
    void abrirSeparadorItem(MenuItem item){
        Intent i = null;
        switch (item.getItemId()){
            case R.id.opcoes_navbar_definicoes:
                i = new Intent(this,DefinicoesActivity.class);
                break;
            case R.id.opcoes_navbar_forum:
                //i = new Intent(this,ForumActivity.class);
                break;
            case R.id.opcoes_navbar_home:
                i = new Intent(this,PaginaPrincipal.class);
                break;
            case R.id.opcoes_navbar_instituicoes:
                //i = new Intent(this,InstituicoesActivity.class);
                break;
            case R.id.opcoes_navbar_lista_favs:
                //i = new Intent(this,ListaFavoritosActivity.class);
                break;
            case R.id.opcoes_navbar_mapa:
                //i = new Intent(this,MapaActivity.class);
                break;
            case R.id.opcoes_navbar_notificacoes:
                //i = new Intent(this,NotificacoesActivity.class);
                break;
            case R.id.opcoes_navbar_procurar:
                //i = new Intent(this,ProcurarActivity.class);
                break;
            default:
                    return;

        }
        if(i != null)
        {
            i.putExtra("opcaoEscolhida",item.getItemId());
            startActivity(i);
        }
    }
}
