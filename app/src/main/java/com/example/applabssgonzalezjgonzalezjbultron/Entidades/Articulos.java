package com.example.applabssgonzalezjgonzalezjbultron.Entidades;

public class Articulos {

    private String Id;
    private String Nombre;
    private String Cantidad;
    private String Estado;
    private String Precio;
    private String Dire;
    //private byte[] Imagen;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDire() {
        return Dire;
    }

    public void setDire(String dire) {
        Dire = dire;
    }

    /*public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        Imagen = imagen;
    }*/
}
