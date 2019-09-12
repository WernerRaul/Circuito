package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.R;
import com.example.raul.circuito.Clases.RegularVo;

import java.util.ArrayList;

public class AdapterDatosRegulares extends RecyclerView.Adapter<AdapterDatosRegulares.ViewHolderDatosRegulares>
    implements View.OnClickListener{

    ArrayList<RegularVo> listaRegulares;
    private View.OnClickListener listener;

    public AdapterDatosRegulares(ArrayList<RegularVo> listaRegulares){
        this.listaRegulares = listaRegulares;
    }

    @NonNull
    @Override
    public ViewHolderDatosRegulares onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_precursores,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatosRegulares(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosRegulares viewHolderDatosRegulares, int i) {
        viewHolderDatosRegulares.etiNombre.setText(listaRegulares.get(i).getNombre());
        viewHolderDatosRegulares.etiHoras.setText(listaRegulares.get(i).getHoras());
        viewHolderDatosRegulares.etiRevisitas.setText(listaRegulares.get(i).getRevisitas());
        viewHolderDatosRegulares.etiEstudios.setText(listaRegulares.get(i).getEstudios());
    }

    @Override
    public int getItemCount() {
        return listaRegulares.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) { this.listener = listener; }

    public class ViewHolderDatosRegulares extends RecyclerView.ViewHolder {

        TextView etiNombre, etiHoras, etiRevisitas, etiEstudios;

        public ViewHolderDatosRegulares(@NonNull View itemView) {
            super(itemView);

            etiNombre = itemView.findViewById(R.id.nombreRegular);
            etiHoras = itemView.findViewById(R.id.horasRegular);
            etiRevisitas = itemView.findViewById(R.id.reviRegular);
            etiEstudios = itemView.findViewById(R.id.estRegular);
        }
    }
}