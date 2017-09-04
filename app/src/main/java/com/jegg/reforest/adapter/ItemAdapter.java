package com.jegg.reforest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jegg.reforest.R;

import java.util.List;

/**
 * Created by oscarvc on 2/05/17.
 * Adapter para lista lotes.
 */

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<ItemLote> listaItemLote;


    public ItemAdapter(Context context, List<ItemLote> listaItemLote) {
        this.context = context;
        this.listaItemLote = listaItemLote;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_lista_lote, parent, false);
        }

        TextView tvNombre = (TextView) row.findViewById(R.id.nombre_lote_item);
        TextView tvFecha = (TextView) row.findViewById(R.id.fecha_lote_item);

        TextView tvArboles = (TextView) row.findViewById(R.id.arboles_lote_item);
        TextView tvArea = (TextView) row.findViewById(R.id.area_lote_item);

        tvFecha.setText(this.listaItemLote.get(position).getFecha()); // mostrar fecha
        tvNombre.setText(this.listaItemLote.get(position).getNombre()); // mostrar nombre de lote

        tvArea.setText("Area: "+ String.format("%.2f", listaItemLote.get(position).getArea()) + " Hectareas");
        tvArboles.setText("Arboles: "+ String.valueOf(this.listaItemLote.get(position).getNumArboles()));

        return row;
    }


    @Override
    public int getCount() {
        return this.listaItemLote.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaItemLote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
