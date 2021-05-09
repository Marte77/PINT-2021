package com.example.crowdzero_v000;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

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
    MaterialToolbar tb = null;
    DrawerLayout dl = null;
    NavigationView nv = null;
    protected String opcaoEscolhida;
    protected int opcaoEscolhidaItemID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navdrawer_activity);//vai passar a usar este layout como layout base

        //obter opcao escolhida anteriormente para colocar a opcao ativa no menu
        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if(bundle == null){
                opcaoEscolhida = "home";
                opcaoEscolhidaItemID = -1;
            }
            else{
                opcaoEscolhida= (String) bundle.get("opcaoEscolhida");
                opcaoEscolhidaItemID = (int) bundle.getInt("opcaoEscolhidaItemID");
            }
        }else{
            opcaoEscolhida = (String) savedInstanceState.getSerializable("opcaoEscolhida");
            opcaoEscolhidaItemID = (int) savedInstanceState.getSerializable("opcaoEscolhidaItemID");
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        ViewStub stub = findViewById(R.id.navdrawer_stubview);
        stub.setLayoutResource(layoutResID);
        stub.inflate();

        // super.setContentView(layoutResID);
    }

    protected void definirTituloEMenu(){
        if(tb == null || nv == null)
            return;
        if(opcaoEscolhidaItemID == -1)
            nv.setCheckedItem(R.id.opcoes_navbar_home);
        else nv.setCheckedItem(opcaoEscolhidaItemID);
        switch(opcaoEscolhida){
            case "definicoes":
                tb.setTitle(R.string.opcoes_navbar_definicoes);
                break;
            case "forum":
                tb.setTitle(R.string.opcoes_navbar_forum);
                break;
            case "home":
                tb.setTitle(R.string.opcoes_navbar_home);
                break;
            case "insituicoes":
                tb.setTitle(R.string.opcoes_navbar_instituicoes);
                break;
            case "favs":
                tb.setTitle(R.string.opcoes_navbar_lista_favs);
                break;
            case "mapa":
                tb.setTitle(R.string.opcoes_navbar_mapa);
                break;
            case "notificacoes":
                tb.setTitle(R.string.opcoes_navbar_notificacoes);
                break;
            case "procurar":
                tb.setTitle(R.string.opcoes_navbar_procurar);
                break;
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //colocar metodos onclick e isso
        //declaraçao dos elementos da activity base que contem o menu lateral
        this.tb = findViewById(R.id.topAppBar_navdrawerBase);
        this.dl = findViewById(R.id.drawerlayout_navdrawer);
        this.nv = findViewById(R.id.NavViewNavDrawerBase);



        definirTituloEMenu();

        //colocar o menu lateral a ocupar 60% do ecrã
        //https://stackoverflow.com/a/65729358/10676498
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        int widthEcra = dpMetrics.widthPixels;
        //ViewGroup.LayoutParams lp = nv.getLayoutParams();
        //lp.width = (int) (widthEcra *0.6);
        //nv.setLayoutParams(lp);
        //TODO:FAZER COM QUE A HEADER VIEW FIQUE COM ESTE PARAMETRO DE WIDTH E NAO O ORIGINAL

            //colocar a header com a mesma width da nav drawer
        View LL = nv.getHeaderView(0);
        ViewGroup.LayoutParams LLP = LL.getLayoutParams();
        LLP.width = (int) (widthEcra * 0.6);
        LL.setLayoutParams(LLP);

        TextView tvPontos = LL.findViewById(R.id.PontuacaoUtilizadorHeaderNavBar);
        TextView tvNome = LL.findViewById(R.id.NomeUtilizadorHeaderNavBar);
        tvPontos.setText("150 Pontos");
        tvNome.setText("Martinho Tavares Malhão");

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
                i.putExtra("opcaoEscolhida","definicoes");
                break;
            case R.id.opcoes_navbar_forum:
                //i = new Intent(this,ForumActivity.class);
                //i.putExtra("opcaoEscolhida","forum");
                break;
            case R.id.opcoes_navbar_home:
                i = new Intent(this,PaginaPrincipal.class);
                i.putExtra("opcaoEscolhida","home");
                break;
            case R.id.opcoes_navbar_instituicoes:
                //i = new Intent(this,InstituicoesActivity.class);
                //i.putExtra("opcaoEscolhida","insituicoes");
                break;
            case R.id.opcoes_navbar_lista_favs:
                //i = new Intent(this,ListaFavoritosActivity.class);
                //i.putExtra("opcaoEscolhida","favs");
                break;
            case R.id.opcoes_navbar_mapa:
                //i = new Intent(this,MapaActivity.class);
                //i.putExtra("opcaoEscolhida","mapa");
                break;
            case R.id.opcoes_navbar_notificacoes:
                //i = new Intent(this,NotificacoesActivity.class);
                //i.putExtra("opcaoEscolhida","notificacoes");
                break;
            case R.id.opcoes_navbar_procurar:
                //i = new Intent(this,ProcurarActivity.class);
                //i.putExtra("opcaoEscolhida","procurar");
                break;
            default:
                    return;

        }
        if(i != null)
        {
            DrawerLayout dl = findViewById(R.id.drawerlayout_navdrawer);
            dl.closeDrawers();
            i.putExtra("opcaoEscolhidaItemID",item.getItemId());

            // Check if we're running on Android 5.0 or higher
            // porque APIs de transição de atividade estão disponíveis no Android 5.0 (API 21) e versões posteriores.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

                // set an exit transition
                Slide s = new Slide();
                s.setSlideEdge(Gravity.LEFT); //colocar a deslizar da esquerda
                getWindow().setExitTransition(s);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

            } else {
                // Swap without transition
                startActivity(i);
            }
        }
    }
}
