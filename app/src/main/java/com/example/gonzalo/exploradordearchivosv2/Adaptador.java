package com.example.gonzalo.exploradordearchivosv2;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Gonzalo on 14/11/2015.
 */
public class Adaptador extends ArrayAdapter<String> {

    private ArrayList<String> listaNombres;
    private String ruta;
    private Context contexto;
    private int recurso;
    private LayoutInflater i;
    public TextView tvArch;
    public ImageView ivCarp, ivArch;


    public Adaptador(Context context, int resource, ArrayList<String> listaNombres) {
        super(context, resource, listaNombres);
        this.contexto = context;
        this.recurso = resource;
        this.listaNombres=listaNombres;
        i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        public TextView tvArchivo;
        public ImageView ivCarpeta, ivArchivo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder gv = new ViewHolder();

        if (convertView == null) {
            convertView = i.inflate(recurso, null);

            tvArch = (TextView) convertView.findViewById(R.id.archivo);
            ivCarp = (ImageView) convertView.findViewById(R.id.ivCarpeta);
            ivArch = (ImageView) convertView.findViewById(R.id.ivArchivo);

            ivCarp.setVisibility(View.INVISIBLE);
            ivArch.setVisibility(View.INVISIBLE);

            gv.tvArchivo = tvArch;
            gv.ivCarpeta = ivCarp;
            gv.ivArchivo = ivArch;

            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }

        if(listaNombres.get(position).contains("/")) {
            ivCarp.setVisibility(View.VISIBLE);
            ivArch.setVisibility(View.INVISIBLE);
            gv.tvArchivo.setText(listaNombres.get(position));

        }else if(listaNombres.get(position).contains("Carpeta vacía")){
            ivCarp.setVisibility(View.INVISIBLE);
            ivArch.setVisibility(View.INVISIBLE);
            gv.tvArchivo.setText("Carpeta vacía");
        }else{
            ivArch.setVisibility(View.VISIBLE);
            ivCarp.setVisibility(View.INVISIBLE);
            gv.tvArchivo.setText(listaNombres.get(position));
        }

        return convertView;
    }

}
