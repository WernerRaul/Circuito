package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.ActividadVo;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosActualizacionActividad extends RecyclerView.Adapter<AdapterDatosActualizacionActividad.ViewHolderDatos>
        implements View.OnClickListener{

    ArrayList<ActividadVo> listaActividad;
    private View.OnClickListener listener;

    public AdapterDatosActualizacionActividad(ArrayList<ActividadVo> listaActividad){
        this.listaActividad = listaActividad;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_actualizacion_actividad, null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.idActividad.setText(listaActividad.get(i).getID());
        viewHolderDatos.anomes.setText(listaActividad.get(i).getAnoMes());
        viewHolderDatos.horas.setText(listaActividad.get(i).getHoras());
        viewHolderDatos.revisitas.setText(listaActividad.get(i).getRevisitas());
        viewHolderDatos.estudios.setText(listaActividad.get(i).getEstudios());
        viewHolderDatos.auxiliar.setText(listaActividad.get(i).getAuxiliar());
    }

    @Override
    public int getItemCount() {
        return listaActividad.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView idActividad, anomes, horas, revisitas, estudios, auxiliar;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            idActividad = itemView.findViewById(R.id.idActividad);
            anomes = itemView.findViewById(R.id.anomes);
            horas = itemView.findViewById(R.id.horas);
            revisitas = itemView.findViewById(R.id.revisitas);
            estudios = itemView.findViewById(R.id.estudios);
            auxiliar = itemView.findViewById(R.id.auxiliar);
        }
    }
}
