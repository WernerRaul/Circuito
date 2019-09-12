package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.R;

import java.util.ArrayList;

public class AdapterDatosNoRevEst extends RecyclerView.Adapter<AdapterDatosNoRevEst.ViewHolderDatosNoRevEst>
    implements View.OnClickListener{

    ArrayList<String> listDatos;
    private View.OnClickListener listener;

    public AdapterDatosNoRevEst(ArrayList<String> listDatos){
        this.listDatos = listDatos;
    }
    @NonNull
    @Override
    public ViewHolderDatosNoRevEst onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_no_rev_est,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatosNoRevEst(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosNoRevEst viewHolderDatosNoRevEst, int i) {
        viewHolderDatosNoRevEst.dato.setText(listDatos.get(i));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
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

    public class ViewHolderDatosNoRevEst extends RecyclerView.ViewHolder {

        TextView dato;
        public ViewHolderDatosNoRevEst(@NonNull View itemView) {
            super(itemView);
            dato = itemView.findViewById(R.id.nombreNoRevisitas);
        }
    }
}
