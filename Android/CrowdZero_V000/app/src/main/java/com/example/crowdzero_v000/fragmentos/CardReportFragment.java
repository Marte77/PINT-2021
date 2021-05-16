package com.example.crowdzero_v000.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.crowdzero_v000.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardReportFragment extends Fragment {
    boolean botaoDislike = false;
    boolean botaoLike = false;
    boolean botaoCoracao = false;


    public static CardReportFragment newInstance(String nome, String descricao) {

        CardReportFragment f = new CardReportFragment();

        Bundle b = new Bundle();
        b.putString("nome", nome);
        b.putString("descricao", descricao);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_card_report, container, false);

        final ImageButton IBDislike = v.findViewById(R.id.botaoDislikeReport);
        IBDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botaoDislike){
                    IBDislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                }else{
                    IBDislike.setImageResource(R.drawable.ic_thumb_down_black_filled_24dp);
                }
                botaoDislike =!botaoDislike;
            }
        });
        final ImageButton IBLike = v.findViewById(R.id.botaoLikeReport);
        IBLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botaoLike){
                    IBLike.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                }else{
                    IBLike.setImageResource(R.drawable.ic_thumb_up_black_filled_24dp);
                }
                botaoLike =!botaoLike;
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



        ((TextView) v.findViewById(R.id.NomeDataHoraTxtReport)).setText(getArguments().getString("nome"));
        ((TextView) v.findViewById(R.id.TipoDescricaoTxtReport)).setText(getArguments().getString("descricao"));
        return v;
    }
}