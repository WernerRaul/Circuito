package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.Clases.BajaactividadVo;
import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosBajaactividad extends RecyclerView.Adapter<AdapterDatosBajaactividad.ViewHolderDatosBajaactividad>
    implements View.OnClickListener{

    ArrayList<BajaactividadVo> listaBajaactividad;
    private View.OnClickListener listener;

    public AdapterDatosBajaactividad(ArrayList<BajaactividadVo> listaBajaactividad){
        this.listaBajaactividad = listaBajaactividad;
    }

    @NonNull
    @Override
    public ViewHolderDatosBajaactividad onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_baja_actividad, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDatosBajaactividad(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosBajaactividad viewHolderDatosBajaactividad, int i) {
        viewHolderDatosBajaactividad.etiNombre.setText(listaBajaactividad.get(i).getNombre());
        viewHolderDatosBajaactividad.etiHoras.setText(listaBajaactividad.get(i).getHoras());
    }

    @Override
    public int getItemCount() {
        return listaBajaactividad.size();
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

    public class ViewHolderDatosBajaactividad extends RecyclerView.ViewHolder {

        TextView etiNombre, etiHoras;

        public ViewHolderDatosBajaactividad(@NonNull View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.nombreBajaActividad);
            etiHoras = itemView.findViewById(R.id.horasBajaActividad);
        }
    }
}
