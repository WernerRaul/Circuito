package com.example.raul.circuito.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raul.circuito.R;
import com.example.raul.circuito.Clases.VaronVo;

import java.util.ArrayList;

public class AdapterDatosVarones extends RecyclerView.Adapter<AdapterDatosVarones.ViewHolderDatosVarones>
    implements View.OnClickListener {

    ArrayList<VaronVo> listaVarones;
    private View.OnClickListener listener;

    public AdapterDatosVarones(ArrayList<VaronVo> listaVarones) {
        this.listaVarones = listaVarones;
    }

    @NonNull
    @Override
    public ViewHolderDatosVarones onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_varones, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDatosVarones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosVarones viewHolderDatosVarones, int i) {
        viewHolderDatosVarones.etiNombre.setText(listaVarones.get(i).getNombre());
        viewHolderDatosVarones.etiEdad.setText(listaVarones.get(i).getEdad());
        viewHolderDatosVarones.etiBautizado.setText(listaVarones.get(i).getBautizado());
        viewHolderDatosVarones.etiAnciano.setText(listaVarones.get(i).getAnciano());
        viewHolderDatosVarones.etiSM.setText(listaVarones.get(i).getSm());
        viewHolderDatosVarones.etiPrecRegular.setText(listaVarones.get(i).getPrecregular());
        viewHolderDatosVarones.etiHoras.setText(listaVarones.get(i).getHoras());
        viewHolderDatosVarones.etiRevisitas.setText(listaVarones.get(i).getRevisitas());
        viewHolderDatosVarones.etiEstudios.setText(listaVarones.get(i).getEstudios());
    }

    @Override
    public int getItemCount() {
        return listaVarones.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    public class ViewHolderDatosVarones extends RecyclerView.ViewHolder {

        TextView etiNombre, etiEdad, etiBautizado, etiAnciano, etiSM,
                etiPrecRegular, etiHoras, etiRevisitas, etiEstudios;

        public ViewHolderDatosVarones(@NonNull View itemView) {
            super(itemView);

            etiNombre = itemView.findViewById(R.id.nombreVaron);
            etiEdad = itemView.findViewById(R.id.edadVaron);
            etiBautizado = itemView.findViewById(R.id.bautizadoVaron);
            etiAnciano = itemView.findViewById(R.id.ancianoVaron);
            etiSM = itemView.findViewById(R.id.smVaron);
            etiPrecRegular = itemView.findViewById(R.id.precregVaron);
            etiHoras = itemView.findViewById(R.id.horasVaron);
            etiRevisitas = itemView.findViewById(R.id.reviVaron);
            etiEstudios = itemView.findViewById(R.id.estVaron);
        }
    }

}