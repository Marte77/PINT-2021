package com.example.crowdzero_v000;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

//https://stackoverflow.com/a/49500446/10676498
//agora, todas as atividades vao fazer "extend" desta classe e nao da AppCompatActivity

//esta activity serve de base para todas as activities desta app para ser possível manipular a toolbar
//e para ser possível usar o mesmo menu lateral em todas as activities
public class NavDrawerActivity extends AppCompatActivity {
    protected MaterialToolbar tb = null;
    protected DrawerLayout dl = null;
    protected NavigationView nv = null;
    protected AppBarLayout abl = null;
    protected String opcaoEscolhida ="home";
    protected int opcaoEscolhidaItemID = -1;
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
                if(opcaoEscolhida==null)
                    opcaoEscolhida="home";
            }
        }else{
            opcaoEscolhida = (String) savedInstanceState.getSerializable("opcaoEscolhida");
            opcaoEscolhidaItemID = (int) savedInstanceState.getSerializable("opcaoEscolhidaItemID");
            if(opcaoEscolhida==null)
                opcaoEscolhida="home";
        }
        tb = findViewById(R.id.topAppBar_navdrawerBase);
        dl = findViewById(R.id.drawerlayout_navdrawer);
        nv = findViewById(R.id.NavViewNavDrawerBase);
        abl = findViewById(R.id.AppBarrLayout_navdrawerBase);

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
            case "Classificacao":
                tb.setTitle(R.string.opcoes_navbar_classificacao);
        }
    }


    private void adicionarListenersDrawer(){
        this.dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull  View drawerView) {

                //region listeners de botoes
                //LinearLayout ll = findViewById(R.id.linearLayoutPontuacaoHeaderNav);
                //if(!ll.hasOnClickListeners()){ //aplicar o listener apenas se ainda nao tiver um
                //    //fiz desta maneira pq se tentasse fazer o findview() o layout ia sempre ficar nulo
                //    //entao isto so mete o listener quando se abre a drawer
                //    // , pois eu acho que aparecia nulo devido ao layout ainda nao ter sido instanciado
                //    ll.setOnClickListener(new View.OnClickListener() {
                //        @Override
                //        public void onClick(View v) {
                //            Intent i = new Intent(getApplicationContext(),ClassificacaoActivity.class);
                //            i.putExtra("opcaoEscolhida","Classificacao");
                //            comecarNovaActivity(i);
                //        }
                //    });
                //}
                TableRow tableRow = findViewById(R.id.tableRowExteriorHeaderNavBar);
                if(!tableRow.hasOnClickListeners()){//aplicar o listener apenas se ainda nao tiver um
                    //fiz desta maneira pq se tentasse fazer o findview() o layout ia sempre ficar nulo
                    //entao isto so mete o listener quando se abre a drawer
                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(),ClassificacaoActivity.class);
                            i.putExtra("opcaoEscolhida","Classificacao");
                            comecarNovaActivity(i);
                        }
                    });
                }
                //endregion

                //region atualizar pontuacao do header
                final FuncoesSharedPreferences funcoesSharedPreferences = new FuncoesSharedPreferences(getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE));
                try{
                    FuncoesApi.FuncoesPessoas.getInformacoesPessoa(getApplicationContext(), funcoesSharedPreferences.getIDPessoa(), new FuncoesApi.volleycallback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            final View LL = nv.getHeaderView(0);
                            TextView tvPontos = LL.findViewById(R.id.PontuacaoUtilizadorHeaderNavBar);
                            TextView tvNome = LL.findViewById(R.id.NomeUtilizadorHeaderNavBar);
                            String nome = jsonObject.getJSONObject("Pessoa").getString("PNome") + " " + jsonObject.getJSONObject("Pessoa").getString("UNome");
                            tvNome.setText(nome);
                            int pts;
                            if (funcoesSharedPreferences.getTipoPessoa().equals(FuncoesSharedPreferences.outrosUtil)) {
                                pts = jsonObject.getInt("Pontos_Outro_Util");
                                String pontos;
                                if(pts > 1)
                                    pontos = pts + " Pontos";
                                else pontos = pts + " Ponto";
                                tvPontos.setText(pontos);
                            } else {
                                pts = jsonObject.getInt("Pontos");
                                String pontos;
                                if(pts > 1)
                                    pontos = pts + " Pontos";
                                else pontos = pts + " Ponto";
                                tvPontos.setText(pontos);
                            }
                            if (!jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil").toString().equals("null")) {
                                {
                                    final int ptsfinal = pts;
                                    FuncoesApi.downloadImagem(getApplicationContext(), jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil"), new FuncoesApi.volleyimagecallback() {
                                        @Override
                                        public void onSuccess(Bitmap bitmap) {
                                            Drawable d = new BitmapDrawable(getResources(),bitmap);
                                            ((ImageView) (LL.findViewById(R.id.fotoPerfilUtilizadorHeaderNavBar))).setBackground(d);
                                            ClassificacaoActivity.colocarBordaPontuacao(getApplicationContext(),((ImageView) (LL.findViewById(R.id.fotoPerfilUtilizadorHeaderNavBar))),
                                                    ptsfinal);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onError(JSONObject jsonObjectErr) throws JSONException {
                            Log.i("pedido","erro navbar: "+jsonObjectErr);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                //endregion
            }

            //estes eventos ainda nao tem nada pq nao os usei, mas era preciso declará-los
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }

        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //colocar metodos onclick e isso
        //declaraçao dos elementos da activity base que contem o menu lateral
        this.tb = findViewById(R.id.topAppBar_navdrawerBase);
        this.dl = findViewById(R.id.drawerlayout_navdrawer);
        this.nv = findViewById(R.id.NavViewNavDrawerBase);

        //adicionar listener de abrir a drawer
        adicionarListenersDrawer();

        definirTituloEMenu();

        //colocar o menu lateral a ocupar 60% do ecrã
        //https://stackoverflow.com/a/65729358/10676498
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        int widthEcra = dpMetrics.widthPixels;
        ViewGroup.LayoutParams lp = nv.getLayoutParams();
        lp.width = (int) (widthEcra *0.73);
        nv.setLayoutParams(lp);


        //colocar a header com a mesma width da nav drawer
        View LL = nv.getHeaderView(0);
        ViewGroup.LayoutParams LLP = LL.getLayoutParams();
        LLP.width = (int) (widthEcra * 0.73);
        LL.setLayoutParams(LLP);



        //colocar os listeners dos botoes
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDrawer(view);
            }
        });
        /*tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                opcaoEscolhidaListarReports(item);
                return true;
            }
        });*/
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                abrirSeparadorItem(item);
                return true;
            }

        });

    }

    protected void abrirDrawer(View view){
        dl.openDrawer(view.getForegroundGravity());
    }

    /*protected void opcaoEscolhidaListarReports(MenuItem item){
        switch (item.getItemId()){
            case R.id.opcaoReportsIndoor:
                Log.i("testar","opcaoReportsIndoor");
                break;
            case R.id.opcaoReportsOutdoor:
                Log.i("testar","opcaoReportsOutdoor");
                break;
        }
    }*/

    //funcao para abrir a activity escolhida no menu
    @SuppressLint("NonConstantResourceId")
    void abrirSeparadorItem(MenuItem item){
        Intent i = null;
        switch (item.getItemId()){
            case R.id.opcoes_navbar_definicoes:
                i = new Intent(this,DefinicoesActivity.class);
                i.putExtra("opcaoEscolhida","definicoes");
                break;
            case R.id.opcoes_navbar_forum:
                i = new Intent(this,ForumActivity.class);
                i.putExtra("opcaoEscolhida","forum");
                break;
            case R.id.opcoes_navbar_home:
                i = new Intent(this,PaginaPrincipal.class);
                i.putExtra("opcaoEscolhida","home");
                break;
            case R.id.opcoes_navbar_instituicoes:
                i = new Intent(this,InstituicoesActivity.class);
                i.putExtra("opcaoEscolhida","insituicoes");
                break;
            case R.id.opcoes_navbar_lista_favs:
                i = new Intent(this,ListaFavoritosActivity.class);
                i.putExtra("opcaoEscolhida","favs");
                break;
            case R.id.opcoes_navbar_mapa:
                i = new Intent(this,MapaActivity.class);
                i.putExtra("opcaoEscolhida","mapa");
                break;
            case R.id.opcoes_navbar_notificacoes:
                i = new Intent(this,NotificacoesActivity.class);
                i.putExtra("opcaoEscolhida","notificacoes");
                break;
            case R.id.opcoes_navbar_procurar:
                //i = new Intent(this,ProcurarActivity.class);
                //i.putExtra("opcaoEscolhida","procurar");
                break;
            case R.id.linearLayoutPontuacaoHeaderNav:
                i = new Intent(this,ClassificacaoActivity.class);
                i.putExtra("opcaoEscolhida","Classificacao");
                break;
            default:
                    return;

        }
        if(i != null)
        {
            i.putExtra("opcaoEscolhidaItemID",item.getItemId());
            comecarNovaActivity(i);
        }
    }

    private void comecarNovaActivity(Intent i){
        this.dl.closeDrawers();
        startActivity(i);

        //isto faz com que a proxima activity entre da direita, e a activity atual vá para trás
        overridePendingTransition(R.anim.slide_in_right,R.anim.zoom_out);
    }

    protected void mudarNomeToolBar(String nome){
        tb.setTitle(nome);
    }
}
