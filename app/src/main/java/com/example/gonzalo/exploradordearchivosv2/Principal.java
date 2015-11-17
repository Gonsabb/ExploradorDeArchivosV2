package com.example.gonzalo.exploradordearchivosv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Principal extends AppCompatActivity {

    private ListView lv;
    private TextView tvRuta;
    private String directorioRaiz;
    private Adaptador adap;
    private ArrayList<String> listaNombresArchivos;
    private ArrayList<String> listaRutasArchivos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.lv = (ListView) findViewById(R.id.listView);
        this.tvRuta = (TextView) findViewById(R.id.tvRuta);
        iniciar();
    }

    public void iniciar(){
        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        verArchivosDirectorio(directorioRaiz);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File archivo = new File(listaRutasArchivos.get(position));

                if (archivo.isFile()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setDataAndType(Uri.fromFile(archivo),"application/*");
                    startActivity(i);
                } else {
                    verArchivosDirectorio(listaRutasArchivos.get(position));
                }
            }
        });
    }

    private void verArchivosDirectorio(String rutaDirectorio) {
        tvRuta.setText("Estás en: " + rutaDirectorio);

        listaNombresArchivos = new ArrayList<>();
        listaRutasArchivos = new ArrayList<>();

        File directorioActual = new File(rutaDirectorio);

        File[] listaArchivos = directorioActual.listFiles();

        int x = 0;

        if (!rutaDirectorio.equals(directorioRaiz)) {
            listaNombresArchivos.add("../");

            listaRutasArchivos.add(directorioActual.getParent());
            x = 1;
        }

        for (File archivo : listaArchivos) {
            listaRutasArchivos.add(archivo.getPath());
        }

        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);

        if (listaArchivos.length > 0) {

            for (int i = x; i < listaRutasArchivos.size(); i++) {
                File archivo = new File(listaRutasArchivos.get(i));
                if (archivo.isFile()) {
                    listaNombresArchivos.add(archivo.getName());
                } else {
                    listaNombresArchivos.add(" /" + archivo.getName());
                }
            }

        } else {
            listaNombresArchivos.add("Carpeta vacía");
            listaRutasArchivos.add(rutaDirectorio);
        }

        adap = new Adaptador(this, R.layout.item, listaNombresArchivos);
        lv.setAdapter(adap);
    }

}
