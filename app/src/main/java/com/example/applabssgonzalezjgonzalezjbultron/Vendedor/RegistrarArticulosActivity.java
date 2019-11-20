package com.example.applabssgonzalezjgonzalezjbultron.Vendedor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Entidades.Articulos;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.ArticulosBDHelper;
import com.example.applabssgonzalezjgonzalezjbultron.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegistrarArticulosActivity extends AppCompatActivity {

    EditText txtNombre, txtCan, txtPre;
    //ImageView imgBtn;
    Spinner spnOpcion;
    String user;
    String dir;
    /*Bitmap bitImg;
    byte[] image;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_articulos);

        this.barraDeMenu();
        this.InicializarControles();
        this.CargarSpinner();
        this.obtenerLocalizacion();

        SharedPreferences prePerfil = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        user = prePerfil.getString("perfil","Invitado");

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

        txtNombre = (EditText)findViewById(R.id.txtNom);
        txtCan= (EditText)findViewById(R.id.txtCan);
        spnOpcion = (Spinner)findViewById(R.id.spnEs);
        txtPre = (EditText)findViewById(R.id.txtPre);
        //imgBtn = (ImageView)findViewById(R.id.imgInsertar);

    }

    public void registrar(View v)
    {
        ArticulosBDHelper arDB = new ArticulosBDHelper(this,"Articulos",null,1);

        SQLiteDatabase db = arDB.getWritableDatabase();

        int maxID = 0;
        String id;
        String nom = txtNombre.getText().toString();
        String can = txtCan.getText().toString();
        String est = spnOpcion.getSelectedItem().toString();
        String pre = txtPre.getText().toString();

        /*if(id.trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Por favor ingrese el id del artículo!", Toast.LENGTH_LONG).show();
        }*/
        if(nom.trim().equalsIgnoreCase("")) {
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
                    Cursor cursor = db.rawQuery("SELECT MAX(id) FROM articulos", null);

                    if (cursor.moveToFirst()){
                        do {
                            maxID = cursor.getInt(0) + 1;

                        }while (cursor.moveToNext());
                    }
                    cursor.close();

                    can = "Cantidad: "+can;
                    est = "Estado: "+est;
                    pre = "Precio: "+pre+"$";
                    dir = "Ubicación: "+dir;
                    db.execSQL("INSERT INTO articulos (id, nombre, cantidad, estado, precio, dire, usuario) " + "VALUES ('" + maxID + "', '" + nom + "', '" + can + "' , '" + est + "', '" + pre + "','" + dir + "', '" + user + "')");

                    Toast.makeText(this, "Articulo registrado con éxito!", Toast.LENGTH_LONG).show();
                    //Restablecer valores del registro
                    txtNombre.setText("");
                    txtCan.setText("");
                    txtPre.setText("");
                    this.CargarSpinner();

                } catch (Exception e) {
                    Toast.makeText(this, "El id ingresado ya existe!", Toast.LENGTH_LONG).show();


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
        actionBar.setTitle("Añade tus productos");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }


    //CARGAR IMAGEN DESDE GALERIA
    /*public void cargarImagen(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            imgBtn.setImageURI(path);
            try {
                bitImg = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image = getBytes(bitImg);
        }
    }


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }*/


    //OBTENER UBICACION DEL PRODUCTO EN VENTA
    private void obtenerLocalizacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                           dir = DirCalle.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
