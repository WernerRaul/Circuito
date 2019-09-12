package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.AuxiliarVo;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosAuxiliares extends RecyclerView.Adapter<AdapterDatosAuxiliares.ViewHolderDatosAuxiliares>
    implements View.OnClickListener{

    ArrayList<AuxiliarVo> listaAuxiliares;
    private View.OnClickListener listener;

    public AdapterDatosAuxiliares(ArrayList<AuxiliarVo> listaAuxiliares){
        this.listaAuxiliares = listaAuxiliares;
    }

    @NonNull
    @Override
    public ViewHolderDatosAuxiliares onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_auxiliares,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatosAuxiliares(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosAuxiliares viewHolderDatosAuxiliares, int i) {
        viewHolderDatosAuxiliares.etiNombre.setText(listaAuxiliares.get(i).getNombre());
        viewHolderDatosAuxiliares.etiAnoMes.setText(listaAuxiliares.get(i).getAñomes());
        viewHolderDatosAuxiliares.etiHoras.setText(listaAuxiliares.get(i).getHoras());
        viewHolderDatosAuxiliares.etiRevisitas.setText(listaAuxiliares.get(i).getRevisitas());
        viewHolderDatosAuxiliares.etiEstudios.setText(listaAuxiliares.get(i).getEstudios());
    }

    @Override
    public int getItemCount() {
        return listaAuxiliares.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolderDatosAuxiliares extends RecyclerView.ViewHolder {

        TextView etiNombre, etiAnoMes, etiHoras, etiRevisitas, etiEstudios;

        public ViewHolderDatosAuxiliares(@NonNull View itemView) {
            super(itemView);

            etiNombre = itemView.findViewById(R.id.nombreAuxiliar);
            etiAnoMes = itemView.findViewById(R.id.añomesAuxiliar);
            etiHoras = itemView.findViewById(R.id.horasAuxiliar);
            etiRevisitas = itemView.findViewById(R.id.reviAuxiliar);
            etiEstudios = itemView.findViewById(R.id.estAuxiliar);
        }
    }
}
