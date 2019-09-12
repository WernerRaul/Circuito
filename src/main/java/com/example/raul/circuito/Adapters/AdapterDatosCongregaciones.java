package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosCongregaciones
        extends RecyclerView.Adapter<AdapterDatosCongregaciones.ViewHolderDatosCongregaciones>
        implements View.OnClickListener {

    ArrayList<String> listDatos;
    private View.OnClickListener listener;

    public AdapterDatosCongregaciones (ArrayList<String> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatosCongregaciones onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_congregaciones,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatosCongregaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosCongregaciones viewHolderDatosCongregaciones, int i) {
        viewHolderDatosCongregaciones.asignarDatos(listDatos.get(i));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatosCongregaciones extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolderDatosCongregaciones(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.idNombreCongregacion);
        }

        public void asignarDatos(String s) {
            dato.setText(s);
        }
    }
}
