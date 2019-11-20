package com.example.applabssgonzalezjgonzalezjbultron.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applabssgonzalezjgonzalezjbultron.Comprador.CompradorActivity;
import com.example.applabssgonzalezjgonzalezjbultron.Helpers.datosHelper;
import com.example.applabssgonzalezjgonzalezjbultron.R;
import com.example.applabssgonzalezjgonzalezjbultron.Vendedor.VendedorActivity;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario;
    EditText txtPass;
    String nombreU;
    String contra;
    String nom;
    String con;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InicializarControles();

        //VERIFICAR SI LA SESION ESTA ACTIVA
        SharedPreferences preSesion = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String se = preSesion.getString("sesion","0");

        if(se.compareTo("0")== 0)
        {
            Toast.makeText(this, "Por favor inicie sesión!", Toast.LENGTH_SHORT).show();
        }
        else if(se.compareTo("1")== 0)//IR A LAYOUT VENDEDOR
        {
            Intent i = new Intent(this, VendedorActivity.class);
            startActivity(i);
        }
        else if(se.compareTo("2")== 0)//IR A LAYOUT COMPRADOR
        {
            Intent j =new Intent(this, CompradorActivity.class);
            startActivity(j);
        }
        else if(se.compareTo("3")== 0)//IR A LAYOUT SUPERVISOR
        {
        }

    }

    private void InicializarControles() {
        txtUsuario = (EditText)findViewById(R.id.editUsuario);
        txtPass = (EditText)findViewById(R.id.editPass);
    }

    public void registrarse(View view){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }

    public void irAlPerfil(View view) {

            //ACTIVAR BASE DE DATOS
            datosHelper userDB = new datosHelper(this, "Usuarios", null, 1);
            SQLiteDatabase db = userDB.getWritableDatabase();

            nombreU = txtUsuario.getText().toString();
            contra = txtPass.getText().toString();

            if (db != null) {

                //OBTENER LOS DATOS DE LA TABLA USUARIOS CORRESPONDIENTES AL USUARIO INGRESADO
                Cursor cursor = db.rawQuery("SELECT * FROM usuariosR WHERE nombre = '" + nombreU + "'", null);

                if (cursor.moveToFirst()) {
                    do {
                        nom = cursor.getString(0);
                        con = cursor.getString(1);
                        tipo = cursor.getString(2);

                        //VERIFICA SI LOS DATOS INGRESADOS CONCUERDAN CON LOS OBTENIDOS DE LA TABLA USUARIOS
                        if(nombreU.equals(nom) && contra.equals(con))
                        {
                            //ACTIVAR SESION
                            SharedPreferences preSesion = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preSesion.edit();
                            editor.putString("sesion", tipo);
                            editor.putString("perfil", nombreU);

                            if (tipo.equals("1")){//VENDEDOR
                                //GUARDAR PERFIL
                                editor.putString("tipoP", "Vendedor");
                                Intent i = new Intent(this, VendedorActivity.class);
                                startActivity(i);
                            }
                            else if (tipo.equals("2"))//COMPRADOR
                            {
                                //GUARDAR PERFIL
                                editor.putString("tipoP", "Comprador");
                                Intent i = new Intent(this, CompradorActivity.class);
                                startActivity(i);

                            }
                            else if (tipo.equals("3"))//SUPERVISOR
                            {

                            }

                            editor.commit();//ALMACENAR LOS DATOS DEL SHARE PREFERENCES

                        }
                        else {
                            Toast.makeText(this, "El nombre de usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }while (cursor.moveToNext());
                }

                db.close();
                cursor.close();

            }

    }

}

