package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.Congregaciones;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosEstablecerFechaVisita extends RecyclerView.Adapter<AdapterDatosEstablecerFechaVisita.ViewHolderDatos>
        implements View.OnClickListener {

    ArrayList<Congregaciones> listaCongregacion;
    private View.OnClickListener listener;

    public AdapterDatosEstablecerFechaVisita(ArrayList<Congregaciones> listaCongregacion){
        this.listaCongregacion = listaCongregacion;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_datos_cong,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.idCongregacion.setText(listaCongregacion.get(i).getId_congregacion());
        viewHolderDatos.nombre.setText(listaCongregacion.get(i).getnombre());
        viewHolderDatos.seccion.setText(listaCongregacion.get(i).getseccion());
        viewHolderDatos.observaciones.setText(listaCongregacion.get(i).getobservaciones());
    }

    @Override
    public int getItemCount() {
        return listaCongregacion.size();
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

        TextView idCongregacion, nombre, seccion, observaciones;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            idCongregacion = itemView.findViewById(R.id.idCongregacion);
            nombre = itemView.findViewById(R.id.nombre);
            seccion = itemView.findViewById(R.id.seccion);
            observaciones = itemView.findViewById(R.id.observaciones);
        }
    }
}
