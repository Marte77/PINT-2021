package com.example.crowdzero_v000.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crowdzero_v000.classesDeAjuda.FuncoesApi;
import com.example.crowdzero_v000.InstituicaoInformacoesActivity;
import com.example.crowdzero_v000.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardInstituicoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardInstituicoesFragment extends Fragment {

                                             /* nome da instituicao e descricao da mesma*/
    public static CardInstituicoesFragment newInstance(String nome, String descricao, int idlocal, String urlimagem) {

        CardInstituicoesFragment f = new CardInstituicoesFragment();

        Bundle b = new Bundle();
        b.putString("nome", nome);
        b.putString("urlimagem", urlimagem);
        b.putString("descricao", descricao);
        b.putInt("idlocal",idlocal);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflatedView =  inflater.inflate(R.layout.fragment_card_instituicoes, container, false);

        ((TextView) inflatedView.findViewById(R.id.NomeTxtViewFrag)).setText(getArguments().getString("nome"));
        ((TextView) inflatedView.findViewById(R.id.DescTxtViewFrag)).setText(getArguments().getString("descricao"));
        inflatedView.findViewById(R.id.DetalhesFragmentoBotao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(getActivity().getApplicationContext(), InstituicaoInformacoesActivity.class);
                    i.putExtra("opcaoEscolhida", "Home");
                    i.putExtra("opcaoEscolhidaItemID", -1);
                    i.putExtra("nome", getArguments().getString("nome"));
                    i.putExtra("descricao", getArguments().getString("descricao"));
                    i.putExtra("urlimagem", getArguments().getString("urlimagem"));
                    i.putExtra("idlocal",getArguments().getInt("idlocal"));
                    startActivity(i);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Log.e("erroCatch","NullPointerException em CardInstituicoesFragment botao onClick");
                    Log.e("erroCatch",e.toString());
                }
            }
        });
        String urlimagem = getArguments().getString("urlimagem");
        //Bitmap imagem = FuncoesApi.getBitmapFromURL(getArguments().getString("urlimagem"));


        ImageView img = inflatedView.findViewById(R.id.idImagemFragmentCardInstituicao);
        try{
            FuncoesApi.downloadImagem(getActivity().getApplicationContext(), urlimagem, img);
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.e("erroCatch","NullPointerException em CardInstituicoesFragment downloadImagem");
            Log.e("erroCatch",e.toString());
        }

        //NetworkImageView imageView = inflatedView.findViewById(R.id.idImagemFragmentCardInstituicao);
        //imageView.setDefaultImageResId(R.drawable.ic_launcher_background);
        //imageView.setImageUrl(urlimagem,Img);


        return inflatedView;
    }



}