package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.IrregularVo;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosIrregulares extends RecyclerView.Adapter<AdapterDatosIrregulares.ViewHolderDatosIrregulares>
    implements View.OnClickListener{

    ArrayList<IrregularVo> listaIrregulares;
    private View.OnClickListener listener;

    public AdapterDatosIrregulares(ArrayList<IrregularVo> listaIrregulares){
        this.listaIrregulares = listaIrregulares;
    }

    @NonNull
    @Override
    public ViewHolderDatosIrregulares onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_irregulares,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatosIrregulares(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosIrregulares viewHolderDatosIrregulares, int i) {
        viewHolderDatosIrregulares.etiNombre.setText(listaIrregulares.get(i).getNombre());
        viewHolderDatosIrregulares.etiAnoMes.setText(listaIrregulares.get(i).getAnomes());
    }

    @Override
    public int getItemCount() {
        return listaIrregulares.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderDatosIrregulares extends RecyclerView.ViewHolder {

        TextView etiNombre, etiAnoMes;

        public ViewHolderDatosIrregulares(@NonNull View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.nombreIrregular);
            etiAnoMes = itemView.findViewById(R.id.anoMesIrregular);
        }
    }
}
