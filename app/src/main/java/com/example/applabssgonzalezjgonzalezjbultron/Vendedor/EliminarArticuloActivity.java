package com.example.applabssgonzalezjgonzalezjbultron.Vendedor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Helpers.ArticulosBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.R;

public class EliminarArticuloActivity extends AppCompatActivity {

    EditText txtIdE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_articulo);

        this.barraDeMenu();
        this.InicializarControles();
    }


    private void InicializarControles(){
        txtIdE = (EditText)findViewById(R.id.txtIdE);
    }

    public void eliminar(View v)
    {
        ArticulosBDHelper arDB = new ArticulosBDHelper(this,"Articulos",null,1);

        SQLiteDatabase db = arDB.getWritableDatabase();

        String id = txtIdE.getText().toString();

        String[] args = new String[]{"Id: "+id};

        if(id.trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Por favor ingrese el id del artículo!", Toast.LENGTH_LONG).show();
        }
        else {
            if (db != null) {

                try {

                    db.execSQL("DELETE FROM articulos WHERE id =?", args);
                    Toast.makeText(this, "Artículo eliminado con éxito!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "El id ingresado no es válido!", Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
            }
        }
    }


    //Metodos para mostrar y ocultar nuestro menú
    private void barraDeMenu(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Elimina tus productos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }
}
