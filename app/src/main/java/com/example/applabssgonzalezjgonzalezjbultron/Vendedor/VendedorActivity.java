package com.example.applabssgonzalezjgonzalezjbultron.Vendedor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Adapters.ArticulosAdapters;
import com.example.applabssgonzalezjgonzalezjbultron.Entidades.Articulos;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.ArticulosBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.Login.MainActivity;
import com.example.applabssgonzalezjgonzalezjbultron.R;

import java.util.ArrayList;
import java.util.List;

public class VendedorActivity extends AppCompatActivity {

    ListView lstArticulos;
    String perfil;
    String tipoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);

        lstArticulos = (ListView)findViewById(R.id.lstArticulos);

        this.barraDeMenu();
        this.LoadListViewTemplate();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        this.LoadListViewTemplate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.LoadListViewTemplate();
    }

    private void LoadListViewTemplate()
    {
        List<Articulos> opciones = this.ObtenerDatos();

        ArticulosAdapters adapter = new ArticulosAdapters(this, opciones);

        lstArticulos.setAdapter(adapter);
    }

    private List<Articulos> ObtenerDatos(){

        List<Articulos> lista = new ArrayList<Articulos>();

        try{
            ArticulosBDHelper arDB = new ArticulosBDHelper(this,"Articulos",null,1);

            SQLiteDatabase db = arDB.getReadableDatabase();

            if (db!= null)
            {

                //OBTINE LOS DATOS DE LA TABLA ARTICULOS DEL USUARIO CON LA SESION ACTIVA
                Cursor cursor = db.rawQuery("SELECT * FROM articulos WHERE usuario = '" + perfil + "'", null);

                if (cursor.moveToFirst()){
                    do {
                        Articulos sm = new Articulos(); //INSERTA LOS DATOS EN LA LISTA

                        sm.setId(cursor.getString(0));
                        sm.setNombre(cursor.getString(1));
                        sm.setCantidad(cursor.getString(2));
                        sm.setEstado(cursor.getString(3));
                        sm.setPrecio(cursor.getString(4));
                        sm.setDire(cursor.getString(5));
                        /*byte[] image = cursor.getBlob(4);
                        sm.setImagen(image);*/

                        lista.add(sm);

                    }while (cursor.moveToNext());
                }
                db.close();
                cursor.close();
            }
        }
        catch (Exception e){
            Toast.makeText(this,"Error -> " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

        return lista;
    }


    //Metodos para mostrar y ocultar nuestro menú
    private void barraDeMenu() {
        //Obtener perfil con sesion iniciada
        SharedPreferences prePerfil = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        perfil = prePerfil.getString("perfil","Invitado");
        tipoP = prePerfil.getString("tipoP","Invitado");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(perfil);
        actionBar.setSubtitle(tipoP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//ACCIONES DE LOS BOTONES DE LA BARRA DE MENU
        switch (item.getItemId()){
            case R.id.agrArti:
                Log.i("ActionBar","Agregar");
                Intent i = new Intent(this, RegistrarArticulosActivity.class);
                startActivity(i);
                break;
            case R.id.updArti:
                Log.i("ActionBar","Actualizar");
                Intent j = new Intent(this, ActualizarArticulosActivity.class);
                startActivity(j);
                break;
            case R.id.eliArti:
                Log.i("ActionBar","Eliminar");
                Intent k = new Intent(this, EliminarArticuloActivity.class);
                startActivity(k);
                break;
            case R.id.cerSesion:
                Log.i("ActionBar","Cerrar");
                SharedPreferences preSesion = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preSesion.edit();
                editor.putString("sesion", "0");
                editor.commit();
                Intent z = new Intent(this, MainActivity.class);
                startActivity(z);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
