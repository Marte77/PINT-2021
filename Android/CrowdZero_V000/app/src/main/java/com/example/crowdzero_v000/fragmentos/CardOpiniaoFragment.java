package com.example.crowdzero_v000.fragmentos;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crowdzero_v000.ClassificacaoActivity;
import com.example.crowdzero_v000.R;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;

import org.json.JSONException;
import org.json.JSONObject;


public class CardOpiniaoFragment extends Fragment {

    int idOpiniao=0,idPessoaOpiniao=0,classificacao=0;
    String descricao, data, nomePessoa;

    public static CardOpiniaoFragment newInstance(String nomePessoa,int idOpiniao, int idPessoaOpiniao, int classificao, String data,String descricao){
        CardOpiniaoFragment f = new CardOpiniaoFragment();
        Bundle b = new Bundle();
        b.putInt("idopiniao",idOpiniao);
        b.putInt("idpessoaopiniao",idPessoaOpiniao);
        b.putInt("classificacao",classificao);
        b.putString("data",data);
        b.putString("descricao",descricao);
        b.putString("nomepessoa",nomePessoa);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_card_opiniao,container,false);

        this.classificacao = getArguments().getInt("classificacao");
        this.idOpiniao = getArguments().getInt("idopiniao");
        this.idPessoaOpiniao = getArguments().getInt("idpessoaopiniao");
        this.descricao = getArguments().getString("descricao");
        this.data = getArguments().getString("data");
        this.nomePessoa = getArguments().getString("nomepessoa");
        TextView descricao = inflatedView.findViewById(R.id.textViewDescricaoOpiniao),
                nomeedata = inflatedView.findViewById(R.id.txtNomeDataOpiniao);
        atualizarTextViews(descricao,nomeedata);
        ImageView[] estrelas = {
                inflatedView.findViewById(R.id.estrela1),
                inflatedView.findViewById(R.id.estrela2),
                inflatedView.findViewById(R.id.estrela3),
                inflatedView.findViewById(R.id.estrela4),
                inflatedView.findViewById(R.id.estrela5),
        };
        atualizarEstrelas(estrelas);
        ImageView fotodeperfil = inflatedView.findViewById(R.id.fotoPerfilOpiniaoCard);
        obterFotoDePerfil(fotodeperfil);
        return inflatedView;
    }

    void atualizarTextViews(TextView descricao, TextView nomedata){
        descricao.setText(this.descricao);
        String nomeda = "<b>" + this.nomePessoa + "</b><br>" + data;
        nomedata.setText(Html.fromHtml(nomeda));
    }
    void atualizarEstrelas(ImageView[] estrelas){
        estrelas[0].setImageResource(R.drawable.ic_star_black_24dp);
        switch (classificacao){
            case 1:
                break;
            case 2:
                estrelas[1].setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 3:
                estrelas[1].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[2].setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 4:
                estrelas[1].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[2].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[3].setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 5:
                estrelas[1].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[2].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[3].setImageResource(R.drawable.ic_star_black_24dp);
                estrelas[4].setImageResource(R.drawable.ic_star_black_24dp);
                break;
        }

    }
    void obterFotoDePerfil(final ImageView fotodeperfil){
        final FuncoesApi.volleyimagecallback volleyimagecallbackImagem = new FuncoesApi.volleyimagecallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                Drawable d = new BitmapDrawable(getResources(),bitmap);
                fotodeperfil.setBackground(d);
            }
        };
        FuncoesApi.volleycallback volleycallbackObterPontosPessoa = new FuncoesApi.volleycallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                int pontos = 0;
                if(jsonObject.has("Pontos")){
                    pontos = jsonObject.getInt("Pontos");
                }else
                    pontos = jsonObject.getInt("Pontos_Outro_Util");
                ClassificacaoActivity.colocarBordaPontuacao(getContext(),fotodeperfil,pontos);
                String urlimagem = jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil");
                if(!urlimagem.equals("null"))
                    FuncoesApi.downloadImagem(getContext(),jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil"),volleyimagecallbackImagem);
            }

            @Override
            public void onError(JSONObject jsonObjectErr) throws JSONException {
                Log.i("pedido",jsonObjectErr.toString());
                ClassificacaoActivity.colocarBordaPontuacao(getContext(),fotodeperfil,0);
            }
        };
        FuncoesApi.FuncoesPessoas.getInformacoesPessoa(getContext(),idPessoaOpiniao,volleycallbackObterPontosPessoa);
    }
}