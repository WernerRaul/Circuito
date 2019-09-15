package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.ActualizarAsistenciaVo;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosActualizacionAsistencia extends RecyclerView.Adapter<AdapterDatosActualizacionAsistencia.ViewHolderDatos>
        implements View.OnClickListener {

    ArrayList<ActualizarAsistenciaVo> listaAsistencia;
    private View.OnClickListener listener;

    public AdapterDatosActualizacionAsistencia(ArrayList<ActualizarAsistenciaVo> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_actualizar_asistencia, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatosActualizacionAsistencia.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.idAsistencia.setText(listaAsistencia.get(i).getID_Mes());
        viewHolderDatos.mes.setText(listaAsistencia.get(i).getMes());
        viewHolderDatos.REntreSemana.setText(listaAsistencia.get(i).getReuEntreSemana());
        viewHolderDatos.RFinSemana.setText(listaAsistencia.get(i).getReuFinSemana());
    }

    @Override
    public int getItemCount() {
        return listaAsistencia.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView idAsistencia, mes, REntreSemana, RFinSemana;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            idAsistencia = itemView.findViewById(R.id.idAsistencia);
            mes = itemView.findViewById(R.id.mes);
            REntreSemana = itemView.findViewById(R.id.REntreSemana);
            RFinSemana = itemView.findViewById(R.id.RFinSemana);
        }
    }
}
