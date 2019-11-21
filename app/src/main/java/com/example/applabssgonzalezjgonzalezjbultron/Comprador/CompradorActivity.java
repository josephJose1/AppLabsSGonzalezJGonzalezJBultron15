package com.example.applabssgonzalezjgonzalezjbultron.Comprador;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Adapters.ArticulosAdapters;
import com.example.applabssgonzalezjgonzalezjbultron.Adapters.CompradorAdapters;
import com.example.applabssgonzalezjgonzalezjbultron.Entidades.Articulos;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.ArticulosBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.CompradorBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.datosHelper;
import com.example.applabssgonzalezjgonzalezjbultron.Login.MainActivity;
import com.example.applabssgonzalezjgonzalezjbultron.R;
import com.example.applabssgonzalezjgonzalezjbultron.Vendedor.ActualizarArticulosActivity;
import com.example.applabssgonzalezjgonzalezjbultron.Vendedor.EliminarArticuloActivity;
import com.example.applabssgonzalezjgonzalezjbultron.Vendedor.RegistrarArticulosActivity;

import java.util.ArrayList;
import java.util.List;

public class CompradorActivity extends AppCompatActivity {

    ListView lstArticulos;
    String perfil, tipoP;
    Button btnBuscar;
    EditText EditexValor;
    Spinner spnOpcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprador);
        this.Inicializar_Controles();
        this.Cargar_Spinner();
        this.LoadListViewTemplate();
        this.barraDeMenu();
    }
    private void Inicializar_Controles(){
        spnOpcion = (Spinner)findViewById(R.id.spnEs);
        lstArticulos = (ListView)findViewById(R.id.lst_comprar_articulos);
        btnBuscar = (Button)findViewById(R.id.btnbuscar);
        EditexValor = (EditText)findViewById(R.id.editexValor);

    }

    private void Cargar_Spinner(){
        List<String> Tipo_filtrado = new ArrayList<>();
        Tipo_filtrado.add("Seleccione el tipo de filtrado");
        Tipo_filtrado.add("Código");
        Tipo_filtrado.add("Nombre");
        Tipo_filtrado.add("Cantidad");
        Tipo_filtrado.add("Estado");
        Tipo_filtrado.add("Precio");

        ArrayAdapter<String> adapterList = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Tipo_filtrado);
        spnOpcion.setAdapter(adapterList);
    }
    public void btn(View view){
        //Toast.makeText(getApplicationContext(),"Buscando",Toast.LENGTH_SHORT).show();
        this.LoadListViewTemplatefind();
    }
    private void LoadListViewTemplate()
    {

        List<Articulos> opciones = this.ObtenerDatos();

        CompradorAdapters adapter = new CompradorAdapters(this, opciones);

        lstArticulos.setAdapter(adapter);
    }
    private void LoadListViewTemplatefind()
    {

        List<Articulos> opciones = this.buscarArticulo();

        CompradorAdapters adapter = new CompradorAdapters(this, opciones);

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
                Cursor cursor = db.rawQuery("SELECT * FROM articulos", null);

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

    private List<Articulos> buscarArticulo() {

        String valor_busqueda = EditexValor.getText().toString();
        List<Articulos> lista = new ArrayList<Articulos>();
        String est = spnOpcion.getSelectedItem().toString();
        try {
            //Abre la base de datos
            ArticulosBDHelper arDB = new ArticulosBDHelper(this, "Articulos", null, 1);
            //En modo de lectura
            SQLiteDatabase db = arDB.getReadableDatabase();
            //equalsIgnorecase para que no distinga entre minuscula y mayuscula
            //trim() para retornar el valor sin espacios enfrente
            Toast.makeText(this, "Este es el valor del spinner!"+est, Toast.LENGTH_LONG).show();

            if(est.compareTo("Seleccione el tipo de filtrado") == 0)
            {
                Toast.makeText(this, "Por favor seleccione el Tipo de Filtrado del artículo!", Toast.LENGTH_LONG).show();
            }
            else if(est.equals("Código") && valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese el codigo del artículo!", Toast.LENGTH_SHORT).show();
            }
            else if(est.equals("Nombre") && valor_busqueda.trim().equalsIgnoreCase("")) {
                Toast.makeText(this, "Por  tu favor ingrese el nombre del artículo!", Toast.LENGTH_SHORT).show();
            }
            else if(est.equals("Cantidad") && valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese la cantidad de artículos!", Toast.LENGTH_SHORT).show();
            }
            else if(est.equals("Estado") && valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese el estado del artículo!", Toast.LENGTH_SHORT).show();
            }
            else if(est.equals("Precio") && valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese el costo del artículo!", Toast.LENGTH_SHORT).show();
            }

            else {

                if (db != null) {
                    Cursor cursor;
                    //Toast.makeText(this, "Antes del cursor!", Toast.LENGTH_LONG).show();

                    if(est.equals("Código")) {

                        Toast.makeText(this, "Asignando el query!", Toast.LENGTH_LONG).show();
                        // OBTIENEN LOS DATOS DE LA TABLA ARTICULOS DEL USUARIO CON LA SESION ACTIVA USANDO EL CODIGO
                        cursor = db.rawQuery("SELECT * FROM articulos WHERE Id='" + valor_busqueda + "'", null);
                        this.movcursor(cursor, lista);
                        db.close();
                        cursor.close();
                    }
                    else if(est.equals("Nombre")) {

                        //OBTIENEN LOS DATOS DE LA TABLA ARTICULOS DEL USUARIO CON LA SESION ACTIVA USANDO EL Nombre
                        cursor = db.rawQuery("SELECT * FROM articulos WHERE nombre='" + valor_busqueda + "'", null);
                        this.movcursor(cursor, lista);
                        db.close();
                        cursor.close();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error -> " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        EditexValor.setText("");
        this.Cargar_Spinner();
        return lista;
    }
    public void movcursor(Cursor cursor, List<Articulos> lista){
        if (cursor.moveToFirst()) {
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
            } while (cursor.moveToNext());
        }
    }
    public void Añadiralcarrito(View v){
        String nom="", codprod="", cant="", idcomp="";
        Integer MaxID=0, cantidad=1;



        //Abro la base de datos En modo de lectura
        CompradorBDHelper arDB = new CompradorBDHelper(this, "Compras", null, 1);

        SQLiteDatabase db = arDB.getReadableDatabase();
        try {
                if (db != null) {
                    Cursor cursor = db.rawQuery("SELECT (MaxID) FROM Comprador ''", null);
                    if (cursor.moveToFirst()) {
                        do {
                            MaxID = cursor.getInt(0) + 1;
                        } while (cursor.moveToNext());
                    }
                    db.close();
                    cursor.close();
                }

            //Buscando el id y nombre del comprador
            SharedPreferences prePerfil = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            perfil = prePerfil.getString("perfil","Invitado");
            datosHelper userDB = new datosHelper(this,"Usuarios",null,1);

            SQLiteDatabase db2 = userDB.getWritableDatabase();
            if (db2!= null)
            {

                //OBTINE LOS DATOS DEL USUARIO CON LA SESION ACTIVA
                Cursor cursor = db2.rawQuery("SELECT * FROM Usuarios WHERE usuario = '" + perfil + "'", null);

                if (cursor.moveToFirst()){
                    do {

                        idcomp= cursor.getString(0);
                        nom =cursor.getString(1);

                    }while (cursor.moveToNext());
                }
                db.close();
                cursor.close();
            }


            nom = "Nombre: "+nom;
            codprod = "Codigo: "+codprod;
            //estado = "Estado: "+estado;
            idcomp = "id compra: "+MaxID;
            cant = "Cantidad" +cantidad;
            db.execSQL("INSERT INTO Comprador (id, cant, cod) " + "VALUES ('" + MaxID + "', '" + cant + "', '" + codprod +"')");

            Toast.makeText(this, "Compra registrada con éxito!", Toast.LENGTH_LONG).show();
            //Restablecer valores del registro
            this.Cargar_Spinner();
        } catch (Exception e) {
            Toast.makeText(this, "Error -> " + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        EditexValor.setText("");
        this.Cargar_Spinner();

    }

    }

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
        getMenuInflater().inflate(R.menu.menubar3, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//ACCIONES DE LOS BOTONES DE LA BARRA DE MENU
        switch (item.getItemId()){
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
