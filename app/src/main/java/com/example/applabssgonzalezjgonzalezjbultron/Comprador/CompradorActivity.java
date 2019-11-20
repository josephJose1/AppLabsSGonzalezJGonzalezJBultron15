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
        Toast.makeText(getApplicationContext(),"Buscando",Toast.LENGTH_SHORT).show();
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
            if(est.compareTo("Seleccione el tipo de filtrado") == 0)
            {
                Toast.makeText(this, "Por favor seleccione el Tipo de Filtrado del artículo!", Toast.LENGTH_LONG).show();
            }
            else if(est.compareTo("Código") == 1 && valor_busqueda.trim().equalsIgnoreCase("") ) {
                Toast.makeText(this, "Por favor ingrese el nombre del artículo!", Toast.LENGTH_LONG).show();
            }
            else if(est.compareTo("Nombre") == 2 && valor_busqueda.trim().equalsIgnoreCase("") ) {
                Toast.makeText(this, "Por favor ingrese el nombre del artículo!", Toast.LENGTH_LONG).show();
            }
            else if(est.compareTo("Cantidad") == 3 &&valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese el número de artículos!", Toast.LENGTH_LONG).show();
            }
            else if(est.compareTo("Estado") == 4 && valor_busqueda.trim().equalsIgnoreCase("0"))
            {
                Toast.makeText(this, "Por favor ingrese el estado del artículo!", Toast.LENGTH_LONG).show();
            }
            else if(est.compareTo("Precio") == 5 && valor_busqueda.trim().equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Por favor ingrese el costo del artículo!", Toast.LENGTH_LONG).show();
            }

            else {
                if (db != null) {
                    Cursor cursor = db.rawQuery("SELECT * FROM articulos WHERE Id='" + valor_busqueda + "'", null);

                    if(est.compareTo("Código") == 1) {

                        // OBTIENEN LOS DATOS DE LA TABLA ARTICULOS DEL USUARIO CON LA SESION ACTIVA USANDO EL CODIGO
                        cursor = db.rawQuery("SELECT * FROM articulos WHERE Id='" + valor_busqueda + "'", null);
                    }
                    else if(est.compareTo("Nombre") == 2) {

                        //OBTIENEN LOS DATOS DE LA TABLA ARTICULOS DEL USUARIO CON LA SESION ACTIVA USANDO EL Nombre
                        cursor = db.rawQuery("SELECT * FROM articulos WHERE Nombre='" + valor_busqueda + "'", null);
                    }

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
                    db.close();
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error -> " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        EditexValor.setText("");
        this.Cargar_Spinner();
        return lista;
    }
    public void Añadiralcarrito(View v){
        String nom="", codprod="", estado="", fecha="", idcomp="", cant="";
        Integer MaxID=0, cantidad=0;

        //Abro la base de datos
        ArticulosBDHelper arDB = new ArticulosBDHelper(this, "Comprador", null, 1);
        //En modo de lectura
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
            //Pasarle el nombre
            nom = "Nombre: "+nom;
            codprod = "Codigo: "+codprod;
            estado = "Estado: "+estado;
            idcomp = "id compra: "+MaxID;
            cant = "Cantidad" +cantidad;
            db.execSQL("INSERT INTO Comprador (id, nombre, estado, cod) " + "VALUES ('" + MaxID + "', '" + nom + "', '" + cant + "' , '" + estado + "')");

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
