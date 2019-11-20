package com.example.applabssgonzalezjgonzalezjbultron.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.applabssgonzalezjgonzalezjbultron.Entidades.Articulos;
import com.example.applabssgonzalezjgonzalezjbultron.R;

import java.util.ArrayList;
import java.util.List;

public class CompradorAdapters extends ArrayAdapter<Articulos> {

    //public static byte[] img;
    private List<Articulos> opciones = new ArrayList<>();

    public CompradorAdapters(Context context, List<Articulos> datos){
        super(context, R.layout.lst_comprar_articulos_, datos);

        opciones= datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.lst_comprar_articulos_, null);

        TextView lblNombre = (TextView)item.findViewById(R.id.lblNom);
        lblNombre.setText(opciones.get(position).getNombre());

        TextView lblId = (TextView)item.findViewById(R.id.lblId);
        lblId.setText("Id: "+opciones.get(position).getId());

        TextView lblCanti = (TextView)item.findViewById(R.id.lblCanti);
        lblCanti.setText(opciones.get(position).getCantidad());

        TextView lblEstado = (TextView)item.findViewById(R.id.lblEstado);
        lblEstado.setText(opciones.get(position).getEstado());

        TextView lblPrecio = (TextView)item.findViewById(R.id.lblPrecio);
        lblPrecio.setText(opciones.get(position).getPrecio());

        TextView lblDir = (TextView)item.findViewById(R.id.lblUbi);
        lblDir.setText(opciones.get(position).getDire());

       /* ImageView imgP = (ImageView)item.findViewById(R.id.imgProducto);
        img = opciones.get(position).getImagen();
        Bitmap imagen = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgP.setImageBitmap(Bitmap.createScaledBitmap(imagen, imgP.getWidth(), imgP.getHeight(), false));*/

        return(item);
    }

}
