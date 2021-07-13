package com.example.crowdzero_v000.fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.classesDeAjuda.FuncoesSharedPreferences;
import com.example.crowdzero_v000.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardReportFragment extends Fragment {
    boolean botaoDislike = false;
    boolean botaoLike = false;
    boolean botaoCoracao = false;
    public int idReport= -1;
    int idpessoa = 0;
    int nLikes = 0;
    int nDislikes = 0;
    TextView txtLikesDislikes;
    int pontosPessoaQueFezReport = 0;
    /**
     * O NOME TEM DE TER SEMPRE UM '\n' DO GENERO
     *  "nome pessoa\ndata e hora
     * */
    public static CardReportFragment newInstance(String nomePessoa,String dataReport, String descricaoReport,
                                                 int idReport, String populacao, int idpessoa_,
                                                 int nLikes_, int nDislikes_) {

        CardReportFragment f = new CardReportFragment();

        Bundle b = new Bundle();
        b.putString("nome", nomePessoa);
        b.putString("data",dataReport);
        b.putString("descricao", descricaoReport);
        b.putInt("idReport", idReport);
        b.putString("populacao", populacao);
        b.putInt("idpessoa",idpessoa_);
        b.putInt("nLikes",nLikes_);
        b.putInt("nDislikes",nDislikes_);

        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v =  inflater.inflate(R.layout.fragment_card_report, container, false);

        assert getArguments() != null;
        this.idReport = getArguments().getInt("idReport");
        this.idpessoa = getArguments().getInt("idpessoa");
        this.nLikes = getArguments().getInt("nLikes");
        this.nDislikes = getArguments().getInt("nDislikes");

        txtLikesDislikes = v.findViewById(R.id.textViewLikesReport);
        atualizarTextViewLikes();

        //region listeners
        final ImageButton IBDislike = v.findViewById(R.id.botaoDislikeReport);
        IBDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!botaoLike) {
                    if (botaoDislike) {
                        removerInteracao(new FuncoesApi.volleycallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                IBDislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                                Log.i("pedido","Sucesso remover interacao dislike: "+jsonObject);
                                nDislikes = nDislikes -1;
                                atualizarTextViewLikes();
                            }

                            @Override
                            public void onError(JSONObject jsonObjectErr) throws JSONException {
                                Log.i("pedido","Erro removerinteracao dislike: "+jsonObjectErr);
                            }
                        });
                    } else {
                        darDislike(new FuncoesApi.volleycallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                IBDislike.setImageResource(R.drawable.ic_thumb_down_black_filled_24dp);
                                Log.i("pedido","Sucesso Dislike: "+jsonObject);
                                nDislikes = nDislikes +1;
                                atualizarTextViewLikes();

                            }

                            @Override
                            public void onError(JSONObject jsonObjectErr) throws JSONException {
                                Log.i("pedido","Erro remover dar dislike: "+jsonObjectErr);
                            }
                        });
                    }
                    botaoDislike = !botaoDislike;
                }

            }
        });
        final ImageButton IBLike = v.findViewById(R.id.botaoLikeReport);
        IBLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!botaoDislike) {
                    if (botaoLike) {

                        removerInteracao(new FuncoesApi.volleycallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                IBLike.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                                Log.i("pedido","Sucesso remover interacao like: "+jsonObject);
                                nLikes = nLikes -1;
                                atualizarTextViewLikes();
                            }

                            @Override
                            public void onError(JSONObject jsonObjectErr) throws JSONException {
                                Log.i("pedido","Erro remover interacao like: "+jsonObjectErr);
                            }
                        });
                    } else {
                        darLike(new FuncoesApi.volleycallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                IBLike.setImageResource(R.drawable.ic_thumb_up_black_filled_24dp);
                                Log.i("pedido","Sucesso Like: "+jsonObject);
                                nLikes = nLikes +1;
                                atualizarTextViewLikes();

                            }
                            @Override
                            public void onError(JSONObject jsonObjectErr) throws JSONException {

                                Log.i("pedido","Erro dar like: "+jsonObjectErr);
                            }
                        });
                    }
                    botaoLike = !botaoLike;
                }
            }
        });
        final ImageButton IBCoracao = v.findViewById(R.id.botaoCoracaoReport);
        IBCoracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botaoCoracao){
                    IBCoracao.setImageResource(R.drawable.ic_favorite_black_24dp);
                }else{
                    IBCoracao.setImageResource(R.drawable.ic_favorite_filled_black_24dp);
                }
                botaoCoracao =!botaoCoracao;
            }
        });
        //endregion

        //region colocar texto
        //cortar as strings para depois se meter a negrito certas partes
        String populacao, descricao;

        populacao = getArguments().getString("populacao");
        descricao = getArguments().getString("descricao");
        populacao = "<b>"+populacao+"</b><br>"+descricao;
        ((TextView) v.findViewById(R.id.TipoDescricaoTxtReport)).setText(Html.fromHtml(populacao));
        String nome, data;
        nome = getArguments().getString("nome");
        data = getArguments().getString("data");
        nome =nome+'\n';
        SpannableStringBuilder ssbNome = new SpannableStringBuilder(nome);
        ssbNome.setSpan(new StyleSpan(Typeface.BOLD),0,nome.length()-1,0);
        SpannableString ssbData = new SpannableString(data);
        ssbData.setSpan(new RelativeSizeSpan(0.7f),0,data.length(),0);
        ssbNome.append(ssbData);
        ssbNome.setSpan(new RelativeSizeSpan(1.1f),0,ssbNome.length(),0);
        ((TextView) v.findViewById(R.id.NomeDataHoraTxtReport)).setText(ssbNome);
        //endregion

        try {
            verificarSeJaInteragiu(new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    boolean existeInteracao = jsonObject.getBoolean("existe");
                    if(existeInteracao){
                        boolean isLike = jsonObject.getBoolean("isLike");
                        if(isLike){
                            IBLike.setImageResource(R.drawable.ic_thumb_up_black_filled_24dp);
                            botaoLike = true;
                        }else{
                            IBDislike.setImageResource(R.drawable.ic_thumb_down_black_filled_24dp);
                            botaoDislike = true;
                        }
                    }
                }

                @Override
                public void onError(JSONObject jsonObjectErr) throws JSONException {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            FuncoesApi.FuncoesPessoas.getInformacoesPessoa(getActivity().getApplicationContext(), idpessoa, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    String urlPessoa = jsonObject.getJSONObject("Pessoa").getString("Foto_De_Perfil");
                    if(!urlPessoa.equals("null"))
                        obterImagem(urlPessoa,v);
                }
                @Override
                public void onError(JSONObject jsonObjectErr) throws JSONException {
                    Log.i("pedido","Erro obter info pessoa: " +jsonObjectErr.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //obter pontuacao da pessoa que fez o report para meter o overlay da imagem
        try{
            FuncoesApi.FuncoesPessoas.getPessoaFromReport(getActivity(), idReport, new FuncoesApi.volleycallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    Log.i("pedido",jsonObject.toString());
                    JSONObject pessoa = jsonObject.getJSONObject("PessoaReport");
                    if(pessoa.has("ID_Outro_Util")){
                        pontosPessoaQueFezReport = pessoa.getInt("Pontos_Outro_Util");
                    }else pontosPessoaQueFezReport = pessoa.getInt("Pontos");
                    colocarBordaCorrespondenteAosPontos(v);
                }

                @Override
                public void onError(JSONObject jsonObjectErr) throws JSONException {
                    Log.i("pedido",jsonObjectErr.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.i("pedido","Catch erro pedido get [pessoa from report: " + e);
        }

        return v;
    }

    void colocarBordaCorrespondenteAosPontos(View v){
        if(pontosPessoaQueFezReport>=200){
            ((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(),R.drawable.borda200pontos)
            );
        }else if(pontosPessoaQueFezReport>=50){
            ((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(),R.drawable.borda50pontos)
            );
        }else if(pontosPessoaQueFezReport>=10){
            ((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(),R.drawable.borda10pontos)
            );
        }else{
            //isto é feito para nao ficar uma imagem verde em cima visto que a foto de perfil é colocada no background da imagem
            ((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(),R.drawable.imagemtransparent)
            );
        }
    }

    private void atualizarTextViewLikes() {
        txtLikesDislikes.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;<b>Likes:</b>" + nLikes + "<br>&nbsp;&nbsp;&nbsp;<b>Dislikes:</b>" + nDislikes));
    }

    void obterImagem(String urlImagem, final View v){
        FuncoesApi.downloadImagem(getActivity(), urlImagem, new FuncoesApi.volleyimagecallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                //((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setImageBitmap(bitmap);
                Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                ((ImageView)(v.findViewById(R.id.fotoPerfilReportCard))).setBackground(drawable);
            }
        });
    }

    void darLike(FuncoesApi.volleycallback VCB){
        try {
            FuncoesApi.FuncoesPessoas.avaliarReport(getActivity().getApplicationContext(), 1, 0, idReport,
                    (new FuncoesSharedPreferences(getActivity().getSharedPreferences("InfoPessoa", Context.MODE_PRIVATE))).getIDPessoa(),
                    VCB);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void darDislike(FuncoesApi.volleycallback VCB){
        try {
            FuncoesApi.FuncoesPessoas.avaliarReport(getActivity().getApplicationContext(), 0, 1, idReport
                    , idpessoa,VCB);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void removerInteracao(FuncoesApi.volleycallback VCB){
        try {
            FuncoesApi.FuncoesPessoas.removerInteracaoReport(getActivity().getApplicationContext(), idReport, idpessoa,
                    VCB);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    void verificarSeJaInteragiu(FuncoesApi.volleycallback VCB) throws JSONException {
        FuncoesApi.FuncoesPessoas.verificarSeExisteInteracao(getActivity().getApplicationContext(),idReport,idpessoa,VCB);
    }
}