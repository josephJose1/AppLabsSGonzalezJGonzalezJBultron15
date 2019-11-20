package com.example.applabssgonzalezjgonzalezjbultron.Vendedor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Helpers.ArticulosBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.R;

import java.util.ArrayList;
import java.util.List;

public class ActualizarArticulosActivity extends AppCompatActivity {

    EditText txtId, txtNombre, txtCan, txtPre;
    Spinner spnOpcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_articulos);
        this.barraDeMenu();
        this.InicializarControles();
        this.CargarSpinner();
    }

    private void CargarSpinner(){
        List<String> operaciones = new ArrayList<>();
        operaciones.add("Seleccione el estado del artículo");
        operaciones.add("Nuevo");
        operaciones.add("Usado");

        ArrayAdapter<String> adapterList = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, operaciones);
        spnOpcion.setAdapter(adapterList);
    }

    private void InicializarControles(){

        txtId = (EditText)findViewById(R.id.txtId);
        txtNombre = (EditText)findViewById(R.id.txtNom);
        txtCan= (EditText)findViewById(R.id.txtCan);
        spnOpcion = (Spinner)findViewById(R.id.spnEs);
        txtPre = (EditText)findViewById(R.id.txtPre);
    }


    public void actualizar(View v)
    {
        ArticulosBDHelper arDB = new ArticulosBDHelper(this,"Articulos",null,1);

        SQLiteDatabase db = arDB.getWritableDatabase();

        String id = txtId.getText().toString();
        String nom = txtNombre.getText().toString();
        String can = txtCan.getText().toString();
        String est = spnOpcion.getSelectedItem().toString();
        String pre = txtPre.getText().toString();

        String[] args = new String[]{"Id: " + id};

        if(id.trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Por favor ingrese el id del artículo!", Toast.LENGTH_LONG).show();
        }
        else if(nom.trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Por favor ingrese el nombre del artículo!", Toast.LENGTH_LONG).show();
        }
        else if(can.trim().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Por favor ingrese el número de artículos!", Toast.LENGTH_LONG).show();
        }
        else if(est.compareTo("Seleccione el estado del artículo") == 0)
        {
            Toast.makeText(this, "Por favor ingrese el estado del artículo!", Toast.LENGTH_LONG).show();
        }
        else if(pre.trim().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Por favor ingrese el costo del artículo!", Toast.LENGTH_LONG).show();
        }
        else {
            if (db != null) {
                try {

                    ContentValues content = new ContentValues();
                    content.put("nombre", nom);
                    content.put("cantidad", "Cantidad: " + can);
                    content.put("estado", "Estado: " + est);
                    content.put("precio", "Precio: " + pre + "$");

                    db.update("articulos", content, "id =?", args);
                    Toast.makeText(this, "Articulo registrado con éxito!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Error -> " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
            }
        }
    }

    //Metodos para mostrar y ocultar nuestro menú
    private void barraDeMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Actualiza tus productos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

}
